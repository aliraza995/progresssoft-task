package com.progresssoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deals_per_currency")
public class DealsPerCurrencyEntity {

	@Id
	@Column(name = "currency")
	private String currency;

	@Column(name = "deal_count", nullable = false)
	private Long orderingCurrency = 0l;

	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	

	public Long getOrderingCurrency() {
		return orderingCurrency;
	}
	

	public void setOrderingCurrency(Long orderingCurrency) {
		this.orderingCurrency = orderingCurrency;
	}
	
}
