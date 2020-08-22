
package com.billdoc.calculator.mongodb.model;

import java.util.HashMap;
import java.util.Map;

public class Distribution {

    private Integer itemNbr;
    private Integer subItemNbr;
    private Integer amount;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getItemNbr() {
        return itemNbr;
    }

    public void setItemNbr(Integer itemNbr) {
        this.itemNbr = itemNbr;
    }

    public Integer getSubItemNbr() {
        return subItemNbr;
    }

    public void setSubItemNbr(Integer subItemNbr) {
        this.subItemNbr = subItemNbr;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
