package com.broodcamp.data.entity.base;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.broodcamp.data.annotation.CustomDateSerializer;
import com.broodcamp.data.utils.DateUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Edward P. Legaspi
 * @created 7 Feb 2018
 **/
@Embeddable
public class DatePeriod implements Comparable<DatePeriod>, Serializable {

    private static final long serialVersionUID = -8866446975347085981L;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date from;

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date to;

    public DatePeriod() {
    }

    public DatePeriod(Date from, Date to) {
        super();
        this.from = from;
        this.to = to;
    }

    public DatePeriod(String from, String to, String datePattern) {

        if (from != null) {
            this.from = DateUtils.parseDateWithPattern(from, datePattern);
        }
        if (to != null) {
            this.to = DateUtils.parseDateWithPattern(to, datePattern);
        }
    }

    /**
     * Check if date falls within period start and end dates
     * 
     * @param date Date to check
     * @return True/false
     */
    public boolean isCorrespondsToPeriod(Date date) {
        return (from == null || (date != null && date.compareTo(from) >= 0)) && (to == null || (date != null && date.compareTo(to) <= 0));
    }

    /**
     * Check if dates match period start and end dates (strict match) or overlap
     * period start and end dates (non-strict match)
     * 
     * @param period      Date period to check
     * @param strictMatch True If dates match period start and end dates (strict
     *                    match) or False when overlap period start and end dates
     *                    (non-strict match)
     * @return True if current period object corresponds to give dates and strict
     *         matching type
     */
    public boolean isCorrespondsToPeriod(DatePeriod period, boolean strictMatch) {
        if (period == null) {
            return isCorrespondsToPeriod(null, null, strictMatch);
        } else {
            return isCorrespondsToPeriod(period.getFrom(), period.getTo(), strictMatch);
        }
    }

    /**
     * Check if dates match period start and end dates (strict match) or overlap
     * period start and end dates (non-strict match)
     * 
     * @param checkFrom   Period start date to check
     * @param checkTo     Period end date to check
     * @param strictMatch True If dates match period start and end dates (strict
     *                    match) or False when overlap period start and end dates
     *                    (non-strict match)
     * @return True if current period object corresponds to give dates and strict
     *         matching type
     */
    public boolean isCorrespondsToPeriod(Date checkFrom, Date checkTo, boolean strictMatch) {

        if (strictMatch) {
            boolean match = (checkFrom == null && this.from == null) || (checkFrom != null && this.from != null && checkFrom.equals(this.from));
            match = match && ((checkTo == null && this.to == null) || (checkTo != null && this.to != null && checkTo.equals(this.to)));
            return match;
        }

        // Check non-strict match case when dates overlap
        return DateUtils.isPeriodsOverlap(this.from, this.to, checkFrom, checkTo);
    }

    /**
     * Check that start date is before end date or any of them is empty
     * 
     * @return True if start date is before end date or any of them is empty
     */
    public boolean isValid() {
        return from == null || to == null || from.equals(to) || from.before(to);
    }

    @Override
    public String toString() {
        return from + " - " + to;
    }

    public String toString(String datePattern) {
        if (isEmpty()) {
            return "";
        }

        String txt = " - ";
        if (from != null) {
            txt = DateUtils.formatDateWithPattern(from, datePattern) + txt;
        }
        if (to != null) {
            txt = txt + DateUtils.formatDateWithPattern(to, datePattern);
        }

        return txt;
    }

    @Override
    public int compareTo(DatePeriod other) {

        if (this.from == null && other.getFrom() == null) {
            return 0;
        } else if (this.from != null && other.getFrom() == null) {
            return 1;
        } else if (this.from == null && other.getFrom() != null) {
            return -1;
        } else {
            return this.from.compareTo(other.getFrom());
        }
    }

    /**
     * Is period empty - are both From and To values are not specified
     * 
     * @return True if both From and To values are not specified
     */
    public boolean isEmpty() {
        return from == null && to == null;
    }

    @Override
    public boolean equals(Object other) {
        if (isEmpty() && other == null) {
            return true;
        } else if (!isEmpty() && other == null) {
            return false;
        } else if (!(other instanceof DatePeriod)) {
            return false;
        }

        return isCorrespondsToPeriod((DatePeriod) other, true);
    }

    public static Date parseDateWithPattern(String dateValue, String pattern) {
        if (dateValue == null || dateValue.trim().length() == 0) {
            return null;
        }
        Date result = null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            result = sdf.parse(dateValue);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public boolean isSameMonth() {
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(from);
        Calendar calTo = Calendar.getInstance();
        calTo.setTime(to);

        return calFrom.get(Calendar.YEAR) == calTo.get(Calendar.YEAR) && calFrom.get(Calendar.MONTH) == calTo.get(Calendar.MONTH);
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

}
