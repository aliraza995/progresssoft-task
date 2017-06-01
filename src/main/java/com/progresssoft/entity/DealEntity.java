package com.progresssoft.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.progresssoft.util.LocalDateTimeSqlTimestampConverter;

@Entity
@Table(name = "deal")
public class DealEntity {

	@Id
	@Column(name = "deal_id")
	private String dealId;

	@Column(name = "ordering_currency")
	private String orderingCurrency;

	@Column(name = "to_currency")
	private String toCurrency;

	@Column(name = "time")
	@Convert(converter = LocalDateTimeSqlTimestampConverter.class)
	private LocalDateTime dealTime;

	@Column(name = "amount", scale = 2, columnDefinition = "DECIMAL(19,2)")
	private BigDecimal amount;
	
    @ManyToOne
    @JoinColumn(name = "source", nullable = false)
    private FileEntity source;

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
	

	public LocalDateTime getDealTime() {
		return dealTime;
	}
	

	public void setDealTime(LocalDateTime dealTime) {
		this.dealTime = dealTime;
	}
	

	public BigDecimal getAmount() {
		return amount;
	}
	

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public FileEntity getSource() {
		return source;
	}
	


	public void setSource(FileEntity source) {
		this.source = source;
	}
}
