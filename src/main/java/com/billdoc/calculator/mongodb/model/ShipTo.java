
package com.billdoc.calculator.mongodb.model;

public class ShipTo {

    private Boolean sameAsSoldTo;
    private Integer shipToBPNbr;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;

    public Boolean getSameAsSoldTo() {
	return sameAsSoldTo;
    }

    public void setSameAsSoldTo(Boolean sameAsSoldTo) {
	this.sameAsSoldTo = sameAsSoldTo;
    }

    public Integer getShipToBPNbr() {
	return shipToBPNbr;
    }

    public void setShipToBPNbr(Integer shipToBPNbr) {
	this.shipToBPNbr = shipToBPNbr;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

}
