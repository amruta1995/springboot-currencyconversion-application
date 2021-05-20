package com.currencyconversion.springbootcurrencyconversionapplication.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class HistoricalCurrencyRatesQuery {
	
	private String currency;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	public HistoricalCurrencyRatesQuery() {
		super();
	}
	
	public HistoricalCurrencyRatesQuery(String currency, Date date) {
		super();
		this.currency = currency;
		this.date = date;
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
