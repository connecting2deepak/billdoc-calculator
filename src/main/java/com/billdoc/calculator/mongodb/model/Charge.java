
package com.billdoc.calculator.mongodb.model;

public class Charge {

    private String chargeType;
    private String priceType;
    private Double amount;

    public String getChargeType() {
	return chargeType;
    }

    public void setChargeType(String chargeType) {
	this.chargeType = chargeType;
    }

    public String getPriceType() {
	return priceType;
    }

    public void setPriceType(String priceType) {
	this.priceType = priceType;
    }

    public Double getAmount() {
	return amount;
    }

    public void setAmount(Double amount) {
	this.amount = amount;
    }

}
