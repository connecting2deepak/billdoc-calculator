package com.billdoc.calculator.model;

/**
 * @author Deepak M S
 *
 */
public class BillDoc {

    private Integer cycle;
    private String billDocNbr;
    private Double billAmount;
    private String billDueDt;
    private String billCycleMessage;

    public Integer getCycle() {
	return cycle;
    }

    public void setCycle(Integer cycle) {
	this.cycle = cycle;
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

    public String getBillDueDt() {
	return billDueDt;
    }

    public void setBillDueDt(String billDueDt) {
	this.billDueDt = billDueDt;
    }

    public String getBillCycleMessage() {
	return billCycleMessage;
    }

    public void setBillCycleMessage(String billCycleMessage) {
	this.billCycleMessage = billCycleMessage;
    }

}
