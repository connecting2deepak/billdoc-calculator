
package com.billdoc.calculator.mongodb.model;

public class List {

    private Integer cycle;
    private String sourceDocument;
    private String sourceDocNbr;
    private String postedBy;
    private String status;
    private String postingDt;
    private Double itemsTotalBillAmt;
    private Double totalAmount;
    private String dueReferenceDt;
    private String paymentTerms;
    private String dueDate;
    private java.util.List<Distribution> distribution = null;

    public Integer getCycle() {
	return cycle;
    }

    public void setCycle(Integer cycle) {
	this.cycle = cycle;
    }

    public String getSourceDocument() {
	return sourceDocument;
    }

    public void setSourceDocument(String sourceDocument) {
	this.sourceDocument = sourceDocument;
    }

    public String getSourceDocNbr() {
	return sourceDocNbr;
    }

    public void setSourceDocNbr(String sourceDocNbr) {
	this.sourceDocNbr = sourceDocNbr;
    }

    public String getPostedBy() {
	return postedBy;
    }

    public void setPostedBy(String postedBy) {
	this.postedBy = postedBy;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getPostingDt() {
	return postingDt;
    }

    public void setPostingDt(String postingDt) {
	this.postingDt = postingDt;
    }

    public Double getItemsTotalBillAmt() {
	return itemsTotalBillAmt;
    }

    public void setItemsTotalBillAmt(Double itemsTotalBillAmt) {
	this.itemsTotalBillAmt = itemsTotalBillAmt;
    }

    public Double getTotalAmount() {
	return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
	this.totalAmount = totalAmount;
    }

    public String getDueReferenceDt() {
	return dueReferenceDt;
    }

    public void setDueReferenceDt(String dueReferenceDt) {
	this.dueReferenceDt = dueReferenceDt;
    }

    public String getPaymentTerms() {
	return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
	this.paymentTerms = paymentTerms;
    }

    public String getDueDate() {
	return dueDate;
    }

    public void setDueDate(String dueDate) {
	this.dueDate = dueDate;
    }

    public java.util.List<Distribution> getDistribution() {
	return distribution;
    }

    public void setDistribution(java.util.List<Distribution> distribution) {
	this.distribution = distribution;
    }

}
