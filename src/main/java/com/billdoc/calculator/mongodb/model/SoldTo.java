
package com.billdoc.calculator.mongodb.model;

public class SoldTo {

    private String firstName;
    private String lastName;
    private String email;
    private String group;
    private String sourceSystemNbr;
    private String soldToTaxCode;
    private Address address;

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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSourceSystemNbr() {
        return sourceSystemNbr;
    }

    public void setSourceSystemNbr(String sourceSystemNbr) {
        this.sourceSystemNbr = sourceSystemNbr;
    }

    public String getSoldToTaxCode() {
        return soldToTaxCode;
    }

    public void setSoldToTaxCode(String soldToTaxCode) {
        this.soldToTaxCode = soldToTaxCode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
