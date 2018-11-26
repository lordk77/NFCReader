package io.ticketcoin.dashboard.dto;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TicketCategoryDTO {

	private String ticketCategoryUUID;
	private String description;
	private BigDecimal price;
	private BigDecimal priceETH;
	private String currency;
	private String title;
	private Integer maxQty;
	private String date; 	
	
	public TicketCategoryDTO(){}
	

	
	
	public String getTicketCategoryUUID() {
		return ticketCategoryUUID;
	}
	public void setTicketCategoryUUID(String ticketCategoryUUID) {
		this.ticketCategoryUUID = ticketCategoryUUID;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMaxQty() {
		return maxQty;
	}

	public void setMaxQty(Integer maxQty) {
		this.maxQty = maxQty;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getPriceETH() {
		return priceETH;
	}

	public void setPriceETH(BigDecimal priceETH) {
		this.priceETH = priceETH;
	}

	
}
