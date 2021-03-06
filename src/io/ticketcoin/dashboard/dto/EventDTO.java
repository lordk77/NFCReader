package io.ticketcoin.dashboard.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EventDTO implements Serializable {
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 3812592240488418078L;
		private String eventUUID;
	    private String name;
	    private String shortDescription;
		private String description;
		private String imageUUID;
		private String imageURL;
		private String artist;
		private Long updatedOn;
		private Long organizationId;
		private String eventType;
		private String urlShare;
		
		
		private String email;
		private String phone;
		private String website;

		
		private String dateFrom;
		private String dateTo;
		
		private String country;
		private String city;
		private String address;
		private BigDecimal coordX;
		private BigDecimal coordY;	
		
		private String currency;
		
		
		
		public EventDTO() {}
		
		
		
		public String getEventUUID() {
			return eventUUID;
		}
		public void setEventUUID(String eventUUID) {
			this.eventUUID = eventUUID;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getImageUUID() {
			return imageUUID;
		}
		public void setImageUUID(String imageUUID) {
			this.imageUUID = imageUUID;
		}

		public String getArtist() {
			return artist;
		}

		public void setArtist(String artist) {
			this.artist = artist;
		}

		public Long getUpdatedOn() {
			return updatedOn;
		}

		public void setUpdatedOn(Long updatedOn) {
			this.updatedOn = updatedOn;
		}

		public Long getOrganizationId() {
			return organizationId;
		}

		public void setOrganizationId(Long organizationId) {
			this.organizationId = organizationId;
		}

		public String getShortDescription() {
			return shortDescription;
		}

		public void setShortDescription(String shortDescription) {
			this.shortDescription = shortDescription;
		}

		public String getEventType() {
			return eventType;
		}

		public void setEventType(String eventType) {
			this.eventType = eventType;
		}


		public String getUrlShare() {
			return urlShare;
		}

		public void setUrlShare(String urlShare) {
			this.urlShare = urlShare;
		}

		public String getImageURL() {
			return imageURL;
		}

		public void setImageURL(String imageURL) {
			this.imageURL = imageURL;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getWebsite() {
			return website;
		}

		public void setWebsite(String website) {
			this.website = website;
		}


		public String getDateFrom() {
			return dateFrom;
		}

		public void setDateFrom(String dateFrom) {
			this.dateFrom = dateFrom;
		}

		public String getDateTo() {
			return dateTo;
		}

		public void setDateTo(String dateTo) {
			this.dateTo = dateTo;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public BigDecimal getCoordX() {
			return coordX;
		}

		public void setCoordX(BigDecimal coordX) {
			this.coordX = coordX;
		}

		public BigDecimal getCoordY() {
			return coordY;
		}

		public void setCoordY(BigDecimal coordY) {
			this.coordY = coordY;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

}
