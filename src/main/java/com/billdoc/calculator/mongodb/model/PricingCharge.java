
package com.billdoc.calculator.mongodb.model;

import java.util.List;

public class PricingCharge {

    private Integer tier;
    private Integer minimum;
    private Integer maximum;
    private String unit;
    private List<Charge> charges = null;

    public Integer getTier() {
	return tier;
    }

    public void setTier(Integer tier) {
	this.tier = tier;
    }

    public Integer getMinimum() {
	return minimum;
    }

    public void setMinimum(Integer minimum) {
	this.minimum = minimum;
    }

    public Integer getMaximum() {
	return maximum;
    }

    public void setMaximum(Integer maximum) {
	this.maximum = maximum;
    }

    public String getUnit() {
	return unit;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

    public List<Charge> getCharges() {
	return charges;
    }

    public void setCharges(List<Charge> charges) {
	this.charges = charges;
    }

}
