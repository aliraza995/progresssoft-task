package com.progresssoft.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.progresssoft.validator.ISOCurrencyCode;

public class DealDto {

	@NotBlank
	private String dealId;
	@NotBlank
	@ISOCurrencyCode
	private String orderingCurrency;
	@NotBlank
	@ISOCurrencyCode
	private String toCurrency;
	@NotNull
	private Long dealTime;
	@NotBlank
	@Pattern(regexp = "^[\\d]+(\\.[\\d]+)?$")
	private String amount;
	@NotNull
	private FileDto source;
	
	public String getDealId() {
		return dealId;
	}
	

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	

	public String getOrderingCurrency() {
		return orderingCurrency;
	}
	

	public void setOrderingCurrency(String orderingCurrency) {
		this.orderingCurrency = orderingCurrency;
	}
	

	public String getToCurrency() {
		return toCurrency;
	}
	

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	

	public Long getDealTime() {
		return dealTime;
	}
	

	public void setDealTime(Long dealTime) {
		this.dealTime = dealTime;
	}
	

	public String getAmount() {
		return amount;
	}
	

	public void setAmount(String amount) {
		this.amount = amount;
	}


	public FileDto getSource() {
		return source;
	}
	


	public void setSource(FileDto fileDto) {
		this.source = fileDto;
	}
	
}
