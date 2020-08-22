
package com.billdoc.calculator.mongodb.model;

import java.util.List;

public class InvoiceAndAr {

    private String applyAt;
    private List<com.billdoc.calculator.mongodb.model.List> list = null;

    public String getApplyAt() {
	return applyAt;
    }

    public void setApplyAt(String applyAt) {
	this.applyAt = applyAt;
    }

    public List<com.billdoc.calculator.mongodb.model.List> getList() {
	return list;
    }

    public void setList(List<com.billdoc.calculator.mongodb.model.List> list) {
	this.list = list;
    }

}
