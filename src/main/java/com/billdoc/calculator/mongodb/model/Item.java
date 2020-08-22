
package com.billdoc.calculator.mongodb.model;

import java.util.List;

public class Item {

    private Integer itemNbr;
    private String status;
    private String productNbr;
    private Boolean isABundle;
    private Integer subItemNbr;
    private Integer quantity;
    private String uom;
    private String productGroup;
    private String productClass;
    private String description;
    private String soldAs;
    private String billingLevel;
    private String revenueLevel;
    private Boolean usageRelevant;
    private String paymentTerms;
    private String subscriptionTerm;
    private String subscriptionType;
    private Integer termLength;
    private String termCycle;
    private String billCycle;
    private String billTerm;
    private String revCycle;
    private String contractStartDt;
    private String contractEndDt;
    private Integer profitCenter;
    private String pricingModel;
    private String companyNbr;
    private String salesOrganization;
    private String distributionChannel;
    private Integer itemSaleCatalogPrice;
    private Integer itemOrderNetPrice;
    private List<PricingCharge> pricingCharges = null;
    private List<Obligation> obligations = null;
    private List<Bill> bill = null;

    public Integer getItemNbr() {
	return itemNbr;
    }

    public void setItemNbr(Integer itemNbr) {
	this.itemNbr = itemNbr;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getProductNbr() {
	return productNbr;
    }

    public void setProductNbr(String productNbr) {
	this.productNbr = productNbr;
    }

    public Boolean getIsABundle() {
	return isABundle;
    }

    public void setIsABundle(Boolean isABundle) {
	this.isABundle = isABundle;
    }

    public Integer getSubItemNbr() {
	return subItemNbr;
    }

    public void setSubItemNbr(Integer subItemNbr) {
	this.subItemNbr = subItemNbr;
    }

    public Integer getQuantity() {
	return quantity;
    }

    public void setQuantity(Integer quantity) {
	this.quantity = quantity;
    }

    public String getUom() {
	return uom;
    }

    public void setUom(String uom) {
	this.uom = uom;
    }

    public String getProductGroup() {
	return productGroup;
    }

    public void setProductGroup(String productGroup) {
	this.productGroup = productGroup;
    }

    public String getProductClass() {
	return productClass;
    }

    public void setProductClass(String productClass) {
	this.productClass = productClass;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getSoldAs() {
	return soldAs;
    }

    public void setSoldAs(String soldAs) {
	this.soldAs = soldAs;
    }

    public String getBillingLevel() {
	return billingLevel;
    }

    public void setBillingLevel(String billingLevel) {
	this.billingLevel = billingLevel;
    }

    public String getRevenueLevel() {
	return revenueLevel;
    }

    public void setRevenueLevel(String revenueLevel) {
	this.revenueLevel = revenueLevel;
    }

    public Boolean getUsageRelevant() {
	return usageRelevant;
    }

    public void setUsageRelevant(Boolean usageRelevant) {
	this.usageRelevant = usageRelevant;
    }

    public String getPaymentTerms() {
	return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
	this.paymentTerms = paymentTerms;
    }

    public String getSubscriptionTerm() {
	return subscriptionTerm;
    }

    public void setSubscriptionTerm(String subscriptionTerm) {
	this.subscriptionTerm = subscriptionTerm;
    }

    public String getSubscriptionType() {
	return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
	this.subscriptionType = subscriptionType;
    }

    public Integer getTermLength() {
	return termLength;
    }

    public void setTermLength(Integer termLength) {
	this.termLength = termLength;
    }

    public String getTermCycle() {
	return termCycle;
    }

    public void setTermCycle(String termCycle) {
	this.termCycle = termCycle;
    }

    public String getBillCycle() {
	return billCycle;
    }

    public void setBillCycle(String billCycle) {
	this.billCycle = billCycle;
    }

    public String getBillTerm() {
	return billTerm;
    }

    public void setBillTerm(String billTerm) {
	this.billTerm = billTerm;
    }

    public String getRevCycle() {
	return revCycle;
    }

    public void setRevCycle(String revCycle) {
	this.revCycle = revCycle;
    }

    public String getContractStartDt() {
	return contractStartDt;
    }

    public void setContractStartDt(String contractStartDt) {
	this.contractStartDt = contractStartDt;
    }

    public String getContractEndDt() {
	return contractEndDt;
    }

    public void setContractEndDt(String contractEndDt) {
	this.contractEndDt = contractEndDt;
    }

    public Integer getProfitCenter() {
	return profitCenter;
    }

    public void setProfitCenter(Integer profitCenter) {
	this.profitCenter = profitCenter;
    }

    public String getPricingModel() {
	return pricingModel;
    }

    public void setPricingModel(String pricingModel) {
	this.pricingModel = pricingModel;
    }

    public String getCompanyNbr() {
	return companyNbr;
    }

    public void setCompanyNbr(String companyNbr) {
	this.companyNbr = companyNbr;
    }

    public String getSalesOrganization() {
	return salesOrganization;
    }

    public void setSalesOrganization(String salesOrganization) {
	this.salesOrganization = salesOrganization;
    }

    public String getDistributionChannel() {
	return distributionChannel;
    }

    public void setDistributionChannel(String distributionChannel) {
	this.distributionChannel = distributionChannel;
    }

    public Integer getItemSaleCatalogPrice() {
	return itemSaleCatalogPrice;
    }

    public void setItemSaleCatalogPrice(Integer itemSaleCatalogPrice) {
	this.itemSaleCatalogPrice = itemSaleCatalogPrice;
    }

    public Integer getItemOrderNetPrice() {
	return itemOrderNetPrice;
    }

    public void setItemOrderNetPrice(Integer itemOrderNetPrice) {
	this.itemOrderNetPrice = itemOrderNetPrice;
    }

    public List<PricingCharge> getPricingCharges() {
	return pricingCharges;
    }

    public void setPricingCharges(List<PricingCharge> pricingCharges) {
	this.pricingCharges = pricingCharges;
    }

    public List<Obligation> getObligations() {
	return obligations;
    }

    public void setObligations(List<Obligation> obligations) {
	this.obligations = obligations;
    }

    public List<Bill> getBill() {
	return bill;
    }

    public void setBill(List<Bill> bill) {
	this.bill = bill;
    }

}
