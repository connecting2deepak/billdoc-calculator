
package com.billdoc.calculator.mongodb.model;

public class BillAddress {

    private String name;
    private String address1;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress1() {
	return address1;
    }

    public void setAddress1(String address1) {
	this.address1 = address1;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getZipcode() {
	return zipcode;
    }

    public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

}
