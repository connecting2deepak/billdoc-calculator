
package com.billdoc.calculator.mongodb.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TpSales {

    @Id
    private String _id;
    private String docType;
    private Integer docNbr;
    private Boolean newBP;
    private Integer vbBPNbr;
    private String country;
    private String companyNbr;
    private String orderReason;
    private String orderDate;
    private String sourceOrderNo;
    private String purchaseOrderNo;
    private String currency;
    private VbOrder vbOrder;
    private SoldTo soldTo;
    private ShipTo shipTo;
    private BillTo billTo;
    private List<PayCriterium> payCriteria = null;
    private List<Item> items = null;
    private InvoiceAndAr invoiceAndAr;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Integer getDocNbr() {
        return docNbr;
    }

    public void setDocNbr(Integer docNbr) {
        this.docNbr = docNbr;
    }

    public Boolean getNewBP() {
        return newBP;
    }

    public void setNewBP(Boolean newBP) {
        this.newBP = newBP;
    }

    public Integer getVbBPNbr() {
        return vbBPNbr;
    }

    public void setVbBPNbr(Integer vbBPNbr) {
        this.vbBPNbr = vbBPNbr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyNbr() {
        return companyNbr;
    }

    public void setCompanyNbr(String companyNbr) {
        this.companyNbr = companyNbr;
    }

    public String getOrderReason() {
        return orderReason;
    }

    public void setOrderReason(String orderReason) {
        this.orderReason = orderReason;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public VbOrder getVbOrder() {
        return vbOrder;
    }

    public void setVbOrder(VbOrder vbOrder) {
        this.vbOrder = vbOrder;
    }

    public SoldTo getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(SoldTo soldTo) {
        this.soldTo = soldTo;
    }

    public ShipTo getShipTo() {
        return shipTo;
    }

    public void setShipTo(ShipTo shipTo) {
        this.shipTo = shipTo;
    }

    public BillTo getBillTo() {
        return billTo;
    }

    public void setBillTo(BillTo billTo) {
        this.billTo = billTo;
    }

    public List<PayCriterium> getPayCriteria() {
        return payCriteria;
    }

    public void setPayCriteria(List<PayCriterium> payCriteria) {
        this.payCriteria = payCriteria;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public InvoiceAndAr getInvoiceAndAr() {
        return invoiceAndAr;
    }

    public void setInvoiceAndAr(InvoiceAndAr invoiceAndAr) {
        this.invoiceAndAr = invoiceAndAr;
    }

}
