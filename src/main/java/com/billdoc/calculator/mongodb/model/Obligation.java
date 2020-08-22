
package com.billdoc.calculator.mongodb.model;

public class Obligation {

    private Integer obligNbr;
    private Integer cycle;
    private String type;
    private String toBeUpdatedBy;
    private String status;
    private String frequency;
    private String fulfilledDate;
    private Integer fulfilledQuantity;
    private Integer fullFilledListPrice;
    private Integer fullFilledDiscount;
    private Integer netPrice;
    private Integer ssp;
    private Integer weightFactor;
    private Integer revenueAllocAmnt;

    public Integer getObligNbr() {
	return obligNbr;
    }

    public void setObligNbr(Integer obligNbr) {
	this.obligNbr = obligNbr;
    }

    public Integer getCycle() {
	return cycle;
    }

    public void setCycle(Integer cycle) {
	this.cycle = cycle;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getToBeUpdatedBy() {
	return toBeUpdatedBy;
    }

    public void setToBeUpdatedBy(String toBeUpdatedBy) {
	this.toBeUpdatedBy = toBeUpdatedBy;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getFrequency() {
	return frequency;
    }

    public void setFrequency(String frequency) {
	this.frequency = frequency;
    }

    public String getFulfilledDate() {
	return fulfilledDate;
    }

    public void setFulfilledDate(String fulfilledDate) {
	this.fulfilledDate = fulfilledDate;
    }

    public Integer getFulfilledQuantity() {
	return fulfilledQuantity;
    }

    public void setFulfilledQuantity(Integer fulfilledQuantity) {
	this.fulfilledQuantity = fulfilledQuantity;
    }

    public Integer getFullFilledListPrice() {
	return fullFilledListPrice;
    }

    public void setFullFilledListPrice(Integer fullFilledListPrice) {
	this.fullFilledListPrice = fullFilledListPrice;
    }

    public Integer getFullFilledDiscount() {
	return fullFilledDiscount;
    }

    public void setFullFilledDiscount(Integer fullFilledDiscount) {
	this.fullFilledDiscount = fullFilledDiscount;
    }

    public Integer getNetPrice() {
	return netPrice;
    }

    public void setNetPrice(Integer netPrice) {
	this.netPrice = netPrice;
    }

    public Integer getSsp() {
	return ssp;
    }

    public void setSsp(Integer ssp) {
	this.ssp = ssp;
    }

    public Integer getWeightFactor() {
	return weightFactor;
    }

    public void setWeightFactor(Integer weightFactor) {
	this.weightFactor = weightFactor;
    }

    public Integer getRevenueAllocAmnt() {
	return revenueAllocAmnt;
    }

    public void setRevenueAllocAmnt(Integer revenueAllocAmnt) {
	this.revenueAllocAmnt = revenueAllocAmnt;
    }

}
