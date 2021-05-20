package com.currencyconversion.springbootcurrencyconversionapplication.model;

public class UserLoginDetails {
	
	private String emailId;
	private String password;
	
	public UserLoginDetails() {
		super();
	}

	public UserLoginDetails(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
