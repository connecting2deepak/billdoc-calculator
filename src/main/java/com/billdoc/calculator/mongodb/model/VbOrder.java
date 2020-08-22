
package com.billdoc.calculator.mongodb.model;

public class VbOrder {

    private String category;
    private String paymentTerms;
    private String status;
    private Integer itemsTotal;
    private Integer dueToday;
    private Integer estShippingCharges;
    private Integer totalEstTax;
    private Integer totalOrderAmtAtCreate;
    private String ingestConfirmationNbr;
    private String invoicePreference;
    private String invoiceCycle;
    private String invoiceDeliveryType;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getItemsTotal() {
        return itemsTotal;
    }

    public void setItemsTotal(Integer itemsTotal) {
        this.itemsTotal = itemsTotal;
    }

    public Integer getDueToday() {
        return dueToday;
    }

    public void setDueToday(Integer dueToday) {
        this.dueToday = dueToday;
    }

    public Integer getEstShippingCharges() {
        return estShippingCharges;
    }

    public void setEstShippingCharges(Integer estShippingCharges) {
        this.estShippingCharges = estShippingCharges;
    }

    public Integer getTotalEstTax() {
        return totalEstTax;
    }

    public void setTotalEstTax(Integer totalEstTax) {
        this.totalEstTax = totalEstTax;
    }

    public Integer getTotalOrderAmtAtCreate() {
        return totalOrderAmtAtCreate;
    }

    public void setTotalOrderAmtAtCreate(Integer totalOrderAmtAtCreate) {
        this.totalOrderAmtAtCreate = totalOrderAmtAtCreate;
    }

    public String getIngestConfirmationNbr() {
        return ingestConfirmationNbr;
    }

    public void setIngestConfirmationNbr(String ingestConfirmationNbr) {
        this.ingestConfirmationNbr = ingestConfirmationNbr;
    }

    public String getInvoicePreference() {
        return invoicePreference;
    }

    public void setInvoicePreference(String invoicePreference) {
        this.invoicePreference = invoicePreference;
    }

    public String getInvoiceCycle() {
        return invoiceCycle;
    }

    public void setInvoiceCycle(String invoiceCycle) {
        this.invoiceCycle = invoiceCycle;
    }

    public String getInvoiceDeliveryType() {
        return invoiceDeliveryType;
    }

    public void setInvoiceDeliveryType(String invoiceDeliveryType) {
        this.invoiceDeliveryType = invoiceDeliveryType;
    }

}
