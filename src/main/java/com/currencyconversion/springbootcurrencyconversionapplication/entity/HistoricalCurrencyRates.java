package com.currencyconversion.springbootcurrencyconversionapplication.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Historical_Currency_Rates")
public class HistoricalCurrencyRates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String currency;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@Lob 
	@Column(name="CONTENT", length=10000)
	private String rates;
	
	public HistoricalCurrencyRates() {
		super();
	}

	public HistoricalCurrencyRates(String currency, Date date, String rates) {
		super();
		this.currency = currency;
		this.date = date;
		this.rates = rates;
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

	public String getRates() {
		return rates;
	}

	public void setRates(String rates) {
		this.rates = rates;
	}
	
}
