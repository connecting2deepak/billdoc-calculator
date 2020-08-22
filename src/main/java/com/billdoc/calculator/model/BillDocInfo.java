/**
 * 
 */
package com.billdoc.calculator.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Deepak M S
 *
 */
public class BillDocInfo implements Serializable {

    private static final long serialVersionUID = 2457102192123954613L;

    private String message;
    private List<BillDoc> billDocs;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BillDoc> getBillDocs() {
        return billDocs;
    }

    public void setBillDocs(List<BillDoc> billDocs) {
        this.billDocs = billDocs;
    }

}
