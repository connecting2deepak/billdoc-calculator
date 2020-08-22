/**
 * 
 */
package com.billdoc.calculator.serive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.openmbean.OpenDataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billdoc.calculator.Constants.TpSalesConstants;
import com.billdoc.calculator.model.BillDoc;
import com.billdoc.calculator.model.BillDocInfo;
import com.billdoc.calculator.mongodb.model.Bill;
import com.billdoc.calculator.mongodb.model.Charge;
import com.billdoc.calculator.mongodb.model.InvoiceAndAr;
import com.billdoc.calculator.mongodb.model.Item;
import com.billdoc.calculator.mongodb.model.PricingCharge;
import com.billdoc.calculator.mongodb.model.TpSales;
import com.billdoc.calculator.repository.TpSalesRepository;
import com.billdoc.calculator.utils.DateUtils;

/**
 * @author Deepak M S
 *
 */
@Service
public class BillService {

    private static final String NO_ITEMS_FOR_BILLING = "No items for billing within specified date range";

    private static final String ITEMS_DETAILS_ARE_UNAVIALBLE = "Items Details are unavialble in DB";

    private static final String ORDER_NOT_ACTIVE_MESSAGE = "Order is NOT ACTIVE. Bill docs cannot be posted.";

    private static final String VB_ORDER_UNAVAILABLE = "vbOrder data is unavailable in DB";

    private static final String ORDER_NOT_FOUND = "Order not found";

    private static final String ORDER_DETAILS = "Order[docNbr: %s & companyNbr: %s]";

    private static final Logger LOGGER = LoggerFactory.getLogger(BillService.class);

    @Autowired
    private TpSalesRepository tpSalesRepository;

    public BillDocInfo createBillDoc(final Integer docNbr, final String companyNbr, final String fromDate, final String toDate)
	    throws OpenDataException {

	final BillDocInfo billDocInfo = new BillDocInfo();
	billDocInfo.setMessage(NO_ITEMS_FOR_BILLING);
	
	LOGGER.info("Fetching " + String.format(ORDER_DETAILS, docNbr, companyNbr));
	// Fetch TpSales data from MongoDB
	TpSales sales = tpSalesRepository.findOneByDocNbrAndCompanyNbr(docNbr, companyNbr);
	
	if (Optional.ofNullable(sales).isPresent()) {
	    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr) + " got successfully fethced from DB.",
		    docNbr, companyNbr);

	    if (Optional.ofNullable(sales.getVbOrder()).isPresent()) {
		if (sales.getVbOrder().getStatus().equalsIgnoreCase(TpSalesConstants.VB_ORDER_ACTIVE)) {

		    if (Optional.ofNullable(sales.getItems()).isPresent() && !sales.getItems().isEmpty()) {
			List<Item> items = sales.getItems();
			
			// Iterate over each items to get BillCycles and do pricing calculations
			for (Item item : items) {

			    final Integer itemNumber = item.getItemNbr();
			    final Integer subItemNumber = item.getSubItemNbr();
			    final String soldAs = item.getSoldAs();
			    final boolean usageRelavant = item.getUsageRelevant();
			    final String pricingModel = item.getPricingModel();
			    final String subscriptionTerm = item.getSubscriptionTerm();
			    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ "'s Item: {} is taken for processing. "
			    	+ "Item Details-> subItemNumber: {}, soldAs: {}, usageRelavant: {}, pricingModel: {}, subscriptionTerm: {}",
			    	itemNumber, subItemNumber, soldAs, usageRelavant,pricingModel, subscriptionTerm);
			    if (Optional.ofNullable(item.getSubItemNbr()).isPresent()
				    && Optional.ofNullable(item.getStatus()).isPresent() && item.getSubItemNbr() < 2
				    && item.getStatus().equalsIgnoreCase(TpSalesConstants.ITEMS_ITEM_STATUS)) {
	// CASE 1
				if (soldAs.equalsIgnoreCase(TpSalesConstants.SOLD_AS_SUBSCRIPTION) && usageRelavant 
					&& pricingModel.equalsIgnoreCase(TpSalesConstants.PRICING_MODEL_TIERED_PRICING)
					&& subscriptionTerm.equalsIgnoreCase(TpSalesConstants.SUBSCRITION_TERM_FIXED)) {

				    Integer tier2Qty = null;
				    Optional<PricingCharge> pricingChargeOpt = Optional.empty();
				    final List<PricingCharge> pricingCharges = item.getPricingCharges();

				    if (TpSalesConstants.USAGE_QUANTITY > 1000) {
					tier2Qty = TpSalesConstants.USAGE_QUANTITY - 1000;
					pricingChargeOpt = pricingCharges.stream().filter(priceCharge -> priceCharge.getTier().compareTo(Integer.valueOf(2)) == 0)
						.findAny();
				    } else {
					pricingChargeOpt = pricingCharges.stream().filter(priceCharge -> priceCharge.getTier().compareTo(Integer.valueOf(1)) == 0)
						.findAny();
				    }

				    List<Bill> bills = item.getBill();
				    if (Optional.ofNullable(bills).isPresent()) {
					for (Bill bill : bills) {
					    // validate input date rage with billcycle's billDueDate
					    if ((bill.getBillStatus()
						    .equalsIgnoreCase(TpSalesConstants.BILL_STATUS_TBD)
						    || bill.getBillStatus().equalsIgnoreCase(TpSalesConstants.BILL_STATUS_READY_TO_BILL))
						    && (DateUtils.isDateWithInRange(fromDate, toDate, bill.getBillDueDt()))) {
						
						if (pricingChargeOpt.isPresent()) {
						    final List<Charge> bCharges = new ArrayList<>();
						     
						    Double billAmount = 0d;
						    for (final Charge pCharge : pricingChargeOpt.get().getCharges()) {
							final Charge bCharge = new Charge();
							if (pCharge.getPriceType()
								.equalsIgnoreCase(TpSalesConstants.PRICE_TYPE_USAGE_BASED_CHARGES)) {
							    
							    bCharge.setAmount((tier2Qty != null ? tier2Qty * pCharge.getAmount() : 0d)
								    + TpSalesConstants.USAGE_QUANTITY * TpSalesConstants.TIER1_PER_MILE_CHARGE);
							} else {
							    bCharge.setAmount(pCharge.getAmount());
							}
							bCharge.setChargeType(pCharge.getPriceType());
							bCharges.add(bCharge);
							billAmount += bCharge.getAmount();
						    }
						    // update BillCycle
						    bill.setBilledOn(DateUtils.getCurrentTimestamp());
						    bill.setBillDocNbr(bill.getCycle().toString());
						    bill.setBillAmount(billAmount);
						    bill.setBillStatus(TpSalesConstants.BILL_STATUS_BILLING_COMPLETE);
						    bill.setCharges(bCharges);
						    tpSalesRepository.save(sales);
						    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+"'s Billing Completed for cycle:{} on item:{}",  bill.getCycle(), item.getItemNbr());
						} else {
						    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Pricing Charges details for item: {}",itemNumber);
						}
					    } else {
						LOGGER.debug(String.format(ORDER_DETAILS, docNbr, companyNbr) +"'s item {} with bill cycle {} is skipped from billing.", item.getItemNbr(), bill.getCycle());
					    }
					}
				    } else {
					LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Billing Details.");
					billDocInfo.setMessage(NO_ITEMS_FOR_BILLING);
				    }
				}
	// CASE 2
				else if (soldAs.equalsIgnoreCase(TpSalesConstants.SOLD_AS_SUBSCRIPTION) && usageRelavant
					&& pricingModel.equalsIgnoreCase(TpSalesConstants.PRICING_MODEL_FIXED_MONTHLY_PRICING)
					&& subscriptionTerm.equalsIgnoreCase(TpSalesConstants.SUBSCRITION_TERM_FIXED)) {
	 
				    final List<PricingCharge> pricingCharges = item.getPricingCharges();
				    final Optional<PricingCharge> pricingChargeOpt = pricingCharges.stream().filter(priceCharge -> priceCharge.getTier().compareTo(Integer.valueOf(1)) == 0)
					    .findAny();
					
				    List<Bill> bills = item.getBill();
				    if (Optional.ofNullable(bills).isPresent()) {
					for (Bill bill : bills) {
					    // validate input date rage with billcycle's billDueDate
					    if ((bill.getBillStatus().equalsIgnoreCase(TpSalesConstants.BILL_STATUS_TBD)
						    || bill.getBillStatus().equalsIgnoreCase(TpSalesConstants.BILL_STATUS_READY_TO_BILL)) 
						    && (DateUtils.isDateWithInRange(fromDate, toDate, bill.getBillDueDt()))) {

						if (pricingChargeOpt.isPresent()) {

						    final Optional<Charge> pChargeOpt = pricingChargeOpt.get()
							    .getCharges().stream().filter(charge -> charge.getPriceType().equalsIgnoreCase(TpSalesConstants.CHARGE_TYPE_LIST_PRICE))
							    .findAny();
						    if (pChargeOpt.isPresent()) {
							// set and update values
							bill.setBilledOn(DateUtils.getCurrentTimestamp());
							bill.setBillDocNbr(bill.getCycle().toString());
							bill.setBillAmount(
								Double.valueOf(pChargeOpt.get().getAmount()));
							bill.setBillStatus(TpSalesConstants.BILL_STATUS_BILLING_COMPLETE);
							// add charges to bill
							final List<Charge> bCharges = new ArrayList<>();
							final Charge BCharge = new Charge();
							BCharge.setAmount(pChargeOpt.get().getAmount());
							BCharge.setChargeType(pChargeOpt.get().getPriceType());
							bCharges.add(BCharge);
							bill.setCharges(bCharges);
							tpSalesRepository.save(sales);
							LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+"'s Billing Completed for cycle:{} on item:{}",  bill.getCycle(), item.getItemNbr());
						    } else {
							LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Charge details for item: {}",itemNumber);
						    }
						} else {
						    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Pricing Charges details for item: {}",itemNumber);
						}
					    } else {
						LOGGER.debug(String.format(ORDER_DETAILS, docNbr, companyNbr) +"'s item {} with bill cycle {} is skipped from billing.", item.getItemNbr(), bill.getCycle());
					    }
					}
				    } else {
					LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Billing Details.");
					billDocInfo.setMessage(NO_ITEMS_FOR_BILLING);
				    }
				}
	// CASE 3
				else if (soldAs.equalsIgnoreCase(TpSalesConstants.SOLD_AS_SUBSCRIPTION) && usageRelavant
					&& pricingModel.equalsIgnoreCase(TpSalesConstants.PRICING_MODEL_FIXED_MONTHLY_PRICING)
					&& subscriptionTerm.equalsIgnoreCase(TpSalesConstants.SUBSCRIPTION_OPEN_TERM)) {

				    final List<PricingCharge> pricingCharges = item.getPricingCharges();
				    final Optional<PricingCharge> pricingChargeOpt = pricingCharges.stream().filter(priceCharge -> priceCharge.getTier().compareTo(Integer.valueOf(1)) == 0)
					    .findAny();
				    
				    List<Bill> bills = item.getBill();
				    if (Optional.ofNullable(bills).isPresent()) {
					// CASE 3 PART 1
					for (Bill bill : bills) {
					    if ((bill.getBillStatus().equalsIgnoreCase(TpSalesConstants.BILL_STATUS_TBD)
						    || bill.getBillStatus().equalsIgnoreCase(TpSalesConstants.BILL_STATUS_READY_TO_BILL)) 
						    && (DateUtils.isDateWithInRange(fromDate, toDate, bill.getBillDueDt()))) {

						if (pricingChargeOpt.isPresent()) {

						    final Optional<Charge> pChargeOpt = pricingChargeOpt.get().getCharges().stream().filter(charge -> charge
								    .getPriceType().equalsIgnoreCase(TpSalesConstants.CHARGE_TYPE_LIST_PRICE)).findAny();
						    if (pChargeOpt.isPresent()) {
							// set and update values
							bill.setBilledOn(DateUtils.getCurrentTimestamp());
							bill.setBillDocNbr(bill.getCycle().toString());
							bill.setBillAmount(Double.valueOf(pChargeOpt.get().getAmount()));
							bill.setBillStatus(TpSalesConstants.BILL_STATUS_BILLING_COMPLETE);
							// add charges to bill
							final List<Charge> bCharges = new ArrayList<>();
							final Charge BCharge = new Charge();
							BCharge.setAmount(pChargeOpt.get().getAmount());
							BCharge.setChargeType(pChargeOpt.get().getPriceType());
							bCharges.add(BCharge);
							bill.setCharges(bCharges);
							tpSalesRepository.save(sales);
							LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+"'s Billing Completed for cycle:{} on item:{}",  bill.getCycle(), item.getItemNbr());
						    } else {
							LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Charge details for item: {}",itemNumber);
						    }
						} else {
						    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Pricing Charges details for item: {}",itemNumber);
						}
					    } else {
						LOGGER.debug(String.format(ORDER_DETAILS, docNbr, companyNbr) +"'s item {} with bill cycle {} is skipped from billing.", item.getItemNbr(), bill.getCycle());
					    }
					}
					// CASE 3 PART 2
					if (item.getTermCycle().equalsIgnoreCase(TpSalesConstants.TERM_CYCLE_MONTH)) {
					    if (generateBillForTermCyleMonth(bills, pricingChargeOpt, itemNumber,
						    subItemNumber, docNbr, companyNbr)) {
						tpSalesRepository.save(sales);
					    }
					}
				    } else {
					LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Billing Details.");
					billDocInfo.setMessage(NO_ITEMS_FOR_BILLING);
				    }
				}
	// CASE 4
				else if (soldAs.equalsIgnoreCase(TpSalesConstants.SOLD_AS_ONE_TIME)) {

				    List<Bill> bills = item.getBill();
				    if (Optional.ofNullable(bills).isPresent()) {

					final List<PricingCharge> pricingCharges = item.getPricingCharges();
					final Optional<PricingCharge> pricingChargeOpt = pricingCharges.stream().filter(priceCharge -> priceCharge.getTier().compareTo(Integer.valueOf(1)) == 0)
						.findAny();
					// Only one BillCycle will be available    
					Bill bill = bills.stream().findFirst().get();
					if ((bill.getBillStatus().equalsIgnoreCase(TpSalesConstants.BILL_STATUS_TBD)
						|| bill.getBillStatus().equalsIgnoreCase(TpSalesConstants.BILL_STATUS_READY_TO_BILL))
						&& (DateUtils.isDateWithInRange(fromDate, toDate, Optional.ofNullable(bill.getBillDueDt()).isPresent() ? 
							    bill.getBillDueDt() : bill.getFrom()))) {

					    if (pricingChargeOpt.isPresent()) {

						    final Optional<Charge> pChargeOpt = pricingChargeOpt.get().getCharges().stream().filter(charge -> charge
								    .getPriceType().equalsIgnoreCase(TpSalesConstants.CHARGE_TYPE_LIST_PRICE)).findAny();
						    if (pChargeOpt.isPresent()) {
							// set and update values
							bill.setBilledOn(DateUtils.getCurrentTimestamp());
							bill.setBillDocNbr(bill.getCycle().toString());
							bill.setBillAmount(Double.valueOf(pChargeOpt.get().getAmount()));
							bill.setBillStatus(TpSalesConstants.BILL_STATUS_BILLING_COMPLETE);
							// add charges to bill
							final List<Charge> bCharges = new ArrayList<>();
							final Charge BCharge = new Charge();
							BCharge.setAmount(pChargeOpt.get().getAmount());
							BCharge.setChargeType(pChargeOpt.get().getPriceType());
							bCharges.add(BCharge);
							bill.setCharges(bCharges);
							tpSalesRepository.save(sales);
							LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+"'s Billing Completed for cycle:{} on item:{}",  bill.getCycle(), item.getItemNbr());
						    } else {
							LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Charge details for item: {}",itemNumber);
						    }
						} else {
						    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Pricing Charges details for item: {}",itemNumber);
						}
					} else {
					    LOGGER.debug(String.format(ORDER_DETAILS, docNbr, companyNbr) +"'s item {} with bill cycle {} is skipped from billing.", item.getItemNbr(), bill.getCycle());
					    }
				    } else {
					LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Billing Details.");
					billDocInfo.setMessage(NO_ITEMS_FOR_BILLING);
				    }
				}
	// CASE END			
			    } else {
				LOGGER.info(String.format(NO_ITEMS_FOR_BILLING + " for " + ORDER_DETAILS, docNbr, companyNbr));
				billDocInfo.setMessage(NO_ITEMS_FOR_BILLING);
			    }
			}
		    } else {
			LOGGER.info(
				String.format(ORDER_DETAILS, docNbr, companyNbr) + " has empty Items detatils in DB.",
				docNbr, companyNbr);
			throw new OpenDataException(ITEMS_DETAILS_ARE_UNAVIALBLE);
		    }
		} else {
		    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr) + " is currently not active.", docNbr,
			    companyNbr);
		    billDocInfo.setMessage(ORDER_NOT_ACTIVE_MESSAGE);
		}
	    } else {
		LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr) + " has missing vbOrder detatils in DB.",
			docNbr, companyNbr);
		throw new OpenDataException(VB_ORDER_UNAVAILABLE);
	    }
	    
	    // update invoiceAndAr
	    final InvoiceAndAr invoice = updateInvoiceAndArForBillCycles(sales, fromDate, toDate);
	    generateBillDocInfo(invoice, billDocInfo);
	    
	} else {
	    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr) + " Not Found");
	    billDocInfo.setMessage(ORDER_NOT_FOUND);
	}
	
	
	return billDocInfo;
    }
    
    private boolean generateBillForTermCyleMonth(List<Bill> bills, final Optional<PricingCharge> pricingChargeOpt,
	    final Integer itemNumber, final Integer subItemNumber,  final Integer docNbr, final String companyNbr) {

	boolean isBillGeneratedForMonth = false;
	// get the last cycle
	final Bill lastBillCycle = bills.get(bills.size() - 1);
	final String billDueDtOfLastBillCycle = lastBillCycle.getBillDueDt();

	// check whether the billDueDate of next month exists and billing not yet
	// completed
	boolean isNextBillCycleExists = DateUtils.isNextBillDueDtExists(billDueDtOfLastBillCycle);
	if (isNextBillCycleExists && !lastBillCycle.getBillStatus().equalsIgnoreCase("BILLING_COMPLETE")) {
	    // Billing exists for successive month.
	} else {
	    // create next cycle
	    final Bill nextBillCycle = createNextBillCycle(lastBillCycle);
	    // check whether the newly created BillCylce comes under current month dueDate
	    // for Pricing calculation
	    if (DateUtils.isBillingNeed(nextBillCycle.getBillDueDt())) {
		// do billing for the created BillCylce and generate new BillCylce for
		// successive month
		calculatePricingDetailsAndUpdate(pricingChargeOpt, nextBillCycle, itemNumber, subItemNumber, docNbr,
			companyNbr);
		bills.add(nextBillCycle);

		// Recursion call made until next month/successive month's billing cycle got
		// generated
		generateBillForTermCyleMonth(bills, pricingChargeOpt, itemNumber, subItemNumber, docNbr, companyNbr);
	    } else {
		bills.add(nextBillCycle);
	    }
	    isBillGeneratedForMonth = true;
	}
	return isBillGeneratedForMonth;
    }
    
    private void calculatePricingDetailsAndUpdate(final Optional<PricingCharge> pricingChargeOpt,
	    final Bill nextBillCycle, final Integer itemNumber, final Integer subItemNumber, final Integer docNbr, final String companyNbr) {

	if (pricingChargeOpt.isPresent()) {

	    final Optional<Charge> pChargeOpt = pricingChargeOpt.get().getCharges()
		    .stream().filter(charge -> charge.getPriceType().equalsIgnoreCase("List_Price")).findAny();
	    if (pChargeOpt.isPresent()) {
		// set and update values
		nextBillCycle.setBilledOn(DateUtils.getCurrentTimestamp());
		nextBillCycle.setBillDocNbr(nextBillCycle.getCycle().toString());
		nextBillCycle.setBillAmount(Double.valueOf(pChargeOpt.get().getAmount()));
		nextBillCycle.setBillStatus(TpSalesConstants.BILL_STATUS_BILLING_COMPLETE);
		// add charges to bill
		final List<Charge> bCharges = new ArrayList<>();
		final Charge BCharge = new Charge();
		BCharge.setAmount(pChargeOpt.get().getAmount());
		BCharge.setChargeType(pChargeOpt.get().getPriceType());
		bCharges.add(BCharge);
		nextBillCycle.setCharges(bCharges);
	    } else {
		LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Charge details for item: {}",itemNumber);
	    }
	} else {
	    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)+ " has missing Pricing Charges details for item: {}",itemNumber);
	}
    }


    /**
     * Creates the next bill cycle.
     *
     * @param lastBillCycle the last bill cycle
     * @return the bill
     */
    private Bill createNextBillCycle(final Bill lastBillCycle) {

	final String lastBillCycleBillDueDt = lastBillCycle.getBillDueDt();
	final Bill nextBillCycle = new Bill();
	nextBillCycle.setCycle(lastBillCycle.getCycle() + 1);
	nextBillCycle.setFrom(DateUtils.getNextCycleDate(lastBillCycle.getFrom()));
	nextBillCycle.setTo(DateUtils.getNextCycleDate(lastBillCycle.getTo()));
	nextBillCycle.setBillDueDt(DateUtils.getNextCycleDate(lastBillCycleBillDueDt));
	nextBillCycle.setBillStatus(TpSalesConstants.BILL_STATUS_TBD);
	return nextBillCycle;
    }
    
    /**
     * This method is used to update invoiceAndAr for bill cycles updated with in
     * the given input date range.
     *
     * @param sales    the sales
     * @param fromDate the from date
     * @param toDate   the to date
     */
    private InvoiceAndAr updateInvoiceAndArForBillCycles(TpSales sales, final String fromDate, final String toDate ) {
	
	InvoiceAndAr invoice = sales.getInvoiceAndAr();
	final List<Item> items = sales.getItems();
	// group items by bill cycle number
	Map<Integer, List<Bill>> cycleBillMap = new HashMap<>();
	
	items.stream().forEach(item -> {
	    // take all completed bills within the given date range and create a map with
	    // cycle as key and respective bills as values
	    item.getBill().stream()
	    	.filter(bill -> bill.getBillStatus().equalsIgnoreCase(TpSalesConstants.BILL_STATUS_BILLING_COMPLETE))
	    	.filter(bill -> DateUtils.isDateWithInRange(fromDate, toDate, bill.getBillDueDt()))
	    		.forEach(bill -> {
	    		    if (cycleBillMap.containsKey(bill.getCycle())) {
	    			cycleBillMap.get(bill.getCycle()).add(bill);
	    		    } else {
	    			final List<Bill> mapBill = new ArrayList<>();
	    			mapBill.add(bill);
	    			cycleBillMap.put(bill.getCycle(), mapBill);
	    		    }
	    		});
	});
	
	// sort the map by cycle key
	Map<Integer, List<Bill>> sortedCycleBillMap = cycleBillMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(
                	Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                	(key, value) -> value, LinkedHashMap::new));
	
	// generate invoice list
	if(Optional.ofNullable(invoice).isPresent() && Optional.ofNullable(invoice.getList()).isPresent()) {
	    
	    sortedCycleBillMap.forEach((invoiceCycle, bills) ->{
		Optional<com.billdoc.calculator.mongodb.model.List> listOpt = invoice.getList().stream().filter(list -> list.getCycle().equals(invoiceCycle)).findAny();
		if(listOpt.isPresent()) {
		    // update existing prices if there is a change
		    final Double billAmountByCycle =  bills.stream().filter(bill -> Optional.ofNullable(bill.getBillAmount()).isPresent())
			    .map(bill -> bill.getBillAmount()).collect(Collectors.summingDouble(Double::doubleValue));
		    final Double existingBillAmount = Optional.ofNullable(listOpt.get().getItemsTotalBillAmt()).isPresent() ? listOpt.get().getItemsTotalBillAmt(): 0D;
		    // update existing invoice's list data if billAmount of respective cycle is different
		    if(!billAmountByCycle.equals(existingBillAmount)) {
			listOpt.get().setItemsTotalBillAmt(billAmountByCycle);
			listOpt.get().setTotalAmount(billAmountByCycle);
			listOpt.get().setPostingDt(DateUtils.getCurrentTimestamp());
		    }
		} else {
		    // add new invoice list to existing list
		    invoice.getList().add(createNewInvoiceList(sales, invoiceCycle, bills));
		}
	    });
	} else {
	    // create a new Invoce
	    final InvoiceAndAr newInvoice = new InvoiceAndAr();
	    newInvoice.setApplyAt(TpSalesConstants.INVOICE_APPLY_AT_LINE_ITEM);
	    final List<com.billdoc.calculator.mongodb.model.List> list = new ArrayList<>();

	    sortedCycleBillMap.forEach((invoiceCycle, bills) -> {
		// create and add price list
		list.add(createNewInvoiceList(sales, invoiceCycle, bills));
	    });
	    newInvoice.setList(list);
	    sales.setInvoiceAndAr(newInvoice);
	}
	// TODO remove all other saves
	tpSalesRepository.save(sales);
	return invoice;
    }

    /**
     * This method creates new invoice list for the given invoice cycle and
     * respective bills.
     *
     * @param sales        the sales
     * @param invoiceCycle the invoice cycle
     * @param bills        the bills
     * @return the com.billdoc.calculator.mongodb.model. list
     */
    private com.billdoc.calculator.mongodb.model.List createNewInvoiceList(TpSales sales, final Integer invoiceCycle,
	    final List<Bill> bills) {

	final com.billdoc.calculator.mongodb.model.List newList = new com.billdoc.calculator.mongodb.model.List();
	newList.setCycle(invoiceCycle);
	newList.setSourceDocument(TpSalesConstants.SOURCE_DOCUMENT_BILL);
	// take any bill cycle to set common data for respective cycle
	final Optional<Bill> billCylceOpt = bills.stream().findAny();
	newList.setSourceDocNbr(billCylceOpt.get().getBillDocNbr());
	newList.setPostedBy(TpSalesConstants.BILL_POSTED_BY);
	newList.setStatus(TpSalesConstants.BILL_STATUS);
	newList.setPostingDt(DateUtils.getCurrentTimestamp());

	final Double billAmountByCycle = bills.stream()
		.filter(bill -> Optional.ofNullable(bill.getBillAmount()).isPresent()).map(bill -> bill.getBillAmount())
		.collect(Collectors.summingDouble(Double::doubleValue));
	newList.setItemsTotalBillAmt(billAmountByCycle);
	newList.setTotalAmount(billAmountByCycle);

	newList.setDueReferenceDt(billCylceOpt.get().getBillDueDt());
	final String paymentTerms = sales.getVbOrder().getPaymentTerms();
	newList.setPaymentTerms(paymentTerms);
	newList.setDueDate(getDueDtFromDueReferenceDt(newList.getDueReferenceDt(), paymentTerms, sales.getDocNbr(),
		sales.getCompanyNbr(), invoiceCycle));
	return newList;
    }

    /**
     * This method is used to get the Due Date from Due Reference Date based on the
     * Payment Terms.
     *
     * @param dueReferenceDate the due reference date
     * @param paymentTerms     the payment terms
     * @param docNbr           the doc nbr
     * @param companyNbr       the company nbr
     * @param invoiceCycle     the invoice cycle
     * @return the due date
     */
    private String getDueDtFromDueReferenceDt(final String dueReferenceDate, final String paymentTerms,
	    final Integer docNbr, final String companyNbr, final Integer invoiceCycle) {
	String dueDate = null;
	if (paymentTerms.equalsIgnoreCase(TpSalesConstants.VB_ORDER_PAYMENT_TERMS_PRE_PAID)) {
	    dueDate = DateUtils.addDaysToDate(dueReferenceDate, 0);
	} else if (paymentTerms.equalsIgnoreCase(TpSalesConstants.VB_ORDER_PAYMENT_TERMS_NET_ZERO)) {
	    dueDate = DateUtils.addDaysToDate(dueReferenceDate, 0);
	} else if (paymentTerms.equalsIgnoreCase(TpSalesConstants.VB_ORDER_PAYMENT_TERMS_NET_30)) {
	    dueDate = DateUtils.addDaysToDate(dueReferenceDate, 30);
	} else {
	    LOGGER.info(String.format(ORDER_DETAILS, docNbr, companyNbr)
		    + " has invalid Payment Terms. Failed to set DueDate for invoice cycle: {}", invoiceCycle);
	}
	return dueDate;
    }
    
    /**
     * This method is used to generate the Bill Documents details from InvoiceAndAr
     *
     * @param invoice     the invoice
     * @param billDocInfo the bill doc info
     */
    private void generateBillDocInfo(final InvoiceAndAr invoice, final BillDocInfo billDocInfo) {

	if (invoice.getList().isEmpty()) {
	    billDocInfo.setMessage("Invoice data not available.");
	} else {
	    final List<BillDoc> billDocs = new ArrayList<>();
	    invoice.getList().forEach(list -> {
		final BillDoc billDoc = new BillDoc();
		billDoc.setCycle(list.getCycle());
		billDoc.setBillDocNbr(list.getSourceDocNbr());
		billDoc.setBillAmount(list.getItemsTotalBillAmt());
		billDoc.setBillDueDt(list.getDueReferenceDt());
		billDoc.setBillCycleMessage(list.getStatus());
		billDocs.add(billDoc);
	    });
	    billDocInfo.setMessage("Bill Doument created Successfully");
	    billDocInfo.setBillDocs(billDocs);
	}
    }
    
}
