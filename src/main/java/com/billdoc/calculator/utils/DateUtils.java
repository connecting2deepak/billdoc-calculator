/**
 * 
 */
package com.billdoc.calculator.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.billdoc.calculator.Exception.OperationException;

/**
 * @author Deepak M S
 *
 */
public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    private static final DateFormat timeStampFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static final DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    private static final DateFormat monthFormatter = new SimpleDateFormat("MM/yyyy");

    public static String getMonthAndYear() {
	return monthFormatter.format(new Date());
    }

    public static String getCurrentTimestamp() {
	return timeStampFormatter.format(new Date());
    }

    public static String getCurrentDate() {
	return dateFormatter.format(new Date());
    }

    public static boolean isNextBillDueDtExists(final String billDueDtOfLastBillCycle) {

	final String lastBillCycleFormat = getMonthAndYear(billDueDtOfLastBillCycle);
	final String currentMonthFormat = getMonthAndYear(billDueDtOfLastBillCycle);
	return lastBillCycleFormat.compareToIgnoreCase(currentMonthFormat) >= 0;
    }

    public static boolean isDateWithInRange(final String inputFrom, final String inputTo, final String billDueDt) {
	try {
	    final Date inputFromDate = dateFormatter.parse(inputFrom);
	    final Date inputToDate = dateFormatter.parse(inputTo);
	    final Date billDueDate = dateFormatter.parse(billDueDt);

	    if (billDueDate.compareTo(inputFromDate) >= 0 && billDueDate.compareTo(inputToDate) <= 0) {
		return true;
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception occurred while processing data due to Invalid Date/Invalid Date Format.", e);
	}
	return false;
    }

    public static String getMonthAndYear(final String billDueDt) {
	try {
	    final Date billDueDate = dateFormatter.parse(billDueDt);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(billDueDate);
	    return monthFormatter.format(calendar.getTime());
	} catch (Exception e) {
	    LOGGER.error("Exception occurred while processing data due to Invalid Date/Invalid Date Format.", e);
	    throw new OperationException("Invalid Date/Invalid Date Format.", e);
	}
    }

    public static String getNextCycleDate(final String billDueDt) {
	try {
	    final Date billDueDate = dateFormatter.parse(billDueDt);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(billDueDate);
	    calendar.add(Calendar.MONTH, 1);
	    return dateFormatter.format(calendar.getTime());
	} catch (Exception e) {
	    LOGGER.error("Exception occurred while processing data due to Invalid Date/Invalid Date Format.", e);
	    throw new OperationException("Invalid Date/Invalid Date Format.", e);
	}
    }

    public static String addDaysToDate(final String date, final int noOfDaysToAdd) {
	try {
	    final Date givenDate = dateFormatter.parse(date);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(givenDate);
	    calendar.add(Calendar.DAY_OF_MONTH, noOfDaysToAdd);
	    return dateFormatter.format(calendar.getTime());
	} catch (Exception e) {
	    LOGGER.error("Exception occurred while processing data due to Invalid Date/Invalid Date Format.", e);
	    throw new OperationException("Invalid Date/Invalid Date Format.", e);
	}
    }

    public static boolean isBillingNeed(final String billDueDt) {
	try {
	    final Date billDueDate = dateFormatter.parse(billDueDt);
	    final Date currentDate = dateFormatter.parse(getCurrentDate());
	    if (billDueDate.compareTo(currentDate) <= 0) {
		return true;
	    }
	} catch (Exception e) {
	    LOGGER.error("Exception occurred while processing data due to Invalid Date/Invalid Date Format.", e);
	}
	return false;
    }

}
