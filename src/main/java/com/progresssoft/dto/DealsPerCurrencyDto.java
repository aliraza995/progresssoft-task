package com.progresssoft.dto;

public class DealsPerCurrencyDto {

	private String currency;
	private Long count = 0l;

	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	

	public Long getCount() {
		return count;
	}
	

	public void setCount(Long count) {
		this.count = count;
	}
	
}
