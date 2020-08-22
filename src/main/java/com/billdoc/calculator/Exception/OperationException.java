/**
 * 
 */
package com.billdoc.calculator.Exception;

/**
 * @author Deepak M S
 *
 */
public class OperationException extends RuntimeException {

    private static final long serialVersionUID = -6598983127772623284L;

    public OperationException() {
	super();
    }

    public OperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

    public OperationException(String message, Throwable cause) {
	super(message, cause);
    }

    public OperationException(String message) {
	super(message);
    }

    public OperationException(Throwable cause) {
	super(cause);
    }

}
