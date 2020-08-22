
package com.billdoc.calculator.mongodb.model;

import java.util.List;

public class Bill {

    private Integer cycle;
    private String from;
    private String to;
    private String billDueDt;
    private String billStatus;
    private String billedOn;
    private String billDocNbr;
    private Double billAmount;
    private Integer usageQuantity;
    private List<Charge> charges = null;

    public Integer getCycle() {
	return cycle;
    }

    public void setCycle(Integer cycle) {
	this.cycle = cycle;
    }

    public String getFrom() {
	return from;
    }

    public void setFrom(String from) {
	this.from = from;
    }

    public String getTo() {
	return to;
    }

    public void setTo(String to) {
	this.to = to;
    }

    public String getBillDueDt() {
	return billDueDt;
    }

    public void setBillDueDt(String billDueDt) {
	this.billDueDt = billDueDt;
    }

    public String getBillStatus() {
	return billStatus;
    }

    public void setBillStatus(String billStatus) {
	this.billStatus = billStatus;
    }

    public String getBilledOn() {
	return billedOn;
    }

    public void setBilledOn(String billedOn) {
	this.billedOn = billedOn;
    }

    public String getBillDocNbr() {
	return billDocNbr;
    }

    public void setBillDocNbr(String billDocNbr) {
	this.billDocNbr = billDocNbr;
    }

    public Double getBillAmount() {
	return billAmount;
    }

    public void setBillAmount(Double billAmount) {
	this.billAmount = billAmount;
    }

    public Integer getUsageQuantity() {
	return usageQuantity;
    }

    public void setUsageQuantity(Integer usageQuantity) {
	this.usageQuantity = usageQuantity;
    }

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
    }

}
