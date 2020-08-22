package com.billdoc.calculator.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.billdoc.calculator.Exception.OperationException;
import com.billdoc.calculator.model.BillDocInfo;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<BillDocInfo> handleIOException() {
	final BillDocInfo billDocInfo = new BillDocInfo();
	billDocInfo.setMessage("IOException occured");
	return new ResponseEntity<BillDocInfo>(billDocInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { OperationException.class })
    protected ResponseEntity<BillDocInfo> operationException(RuntimeException ex, WebRequest request) {
	final BillDocInfo billDocInfo = new BillDocInfo();
	billDocInfo.setMessage(ex.getMessage());
	return new ResponseEntity<BillDocInfo>(billDocInfo, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<BillDocInfo> runtimeException(RuntimeException ex, WebRequest request) {
	final BillDocInfo billDocInfo = new BillDocInfo();
	billDocInfo.setMessage(ex.getMessage() != null ? ex.getMessage() : "Unexpected Error Occurred");
	return new ResponseEntity<BillDocInfo>(billDocInfo, HttpStatus.ACCEPTED);
    }

}