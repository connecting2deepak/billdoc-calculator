
package com.billdoc.calculator.mongodb.model;

public class PayCriterium {

    private Boolean isDefault;
    private String type;
    private Boolean useForRecurring;
    private String cardCompany;
    private String tokenID;
    private String tokenExpDt;
    private Integer cardNo;
    private Integer cvv;
    private String expiryDt;
    private BillAddress billAddress;

    public Boolean getIsDefault() {
	return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
	this.isDefault = isDefault;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Boolean getUseForRecurring() {
	return useForRecurring;
    }

    public void setUseForRecurring(Boolean useForRecurring) {
	this.useForRecurring = useForRecurring;
    }

    public String getCardCompany() {
	return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
	this.cardCompany = cardCompany;
    }

    public String getTokenID() {
	return tokenID;
    }

    public void setTokenID(String tokenID) {
	this.tokenID = tokenID;
    }

    public String getTokenExpDt() {
	return tokenExpDt;
    }

    public void setTokenExpDt(String tokenExpDt) {
	this.tokenExpDt = tokenExpDt;
    }

    public Integer getCardNo() {
	return cardNo;
    }

    public void setCardNo(Integer cardNo) {
	this.cardNo = cardNo;
    }

    public Integer getCvv() {
	return cvv;
    }

    public void setCvv(Integer cvv) {
	this.cvv = cvv;
    }

    public String getExpiryDt() {
	return expiryDt;
    }

    public void setExpiryDt(String expiryDt) {
	this.expiryDt = expiryDt;
    }

    public BillAddress getBillAddress() {
	return billAddress;
    }

    public void setBillAddress(BillAddress billAddress) {
	this.billAddress = billAddress;
    }

}
