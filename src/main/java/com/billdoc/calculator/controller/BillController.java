/**
 * 
 */
package com.billdoc.calculator.controller;

import javax.management.openmbean.OpenDataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.billdoc.calculator.model.BillDocInfo;
import com.billdoc.calculator.serive.BillService;

/**
 * @author Deepak M S
 *
 */
@RequestMapping("/billdoc")
@RestController
public class BillController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    @PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public BillDocInfo createBillDoc(@RequestParam("docNbr") Integer docNbr,
	    @RequestParam("companyNbr") String companyNbr, @RequestParam("fromDate") String fromDate,
	    @RequestParam("toDate") String toDate) throws OpenDataException {

	LOGGER.info("Request received to create Bill Doc for docNbr: {} & companyNbr: {} for the date range: {} to {}",
		docNbr, companyNbr, fromDate, toDate);
	return billService.createBillDoc(docNbr, companyNbr, fromDate, toDate);

    }

}
