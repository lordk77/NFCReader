package io.ticketcoin.rest.response;

import javax.xml.bind.annotation.XmlRootElement;

import io.ticketcoin.dashboard.dto.TicketDTO;

@XmlRootElement
public class TicketDTOResponseWrapper {
	

	
	private boolean success=true;
	private String message;
	private TicketDTO data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TicketDTO getData() {
		return data;
	}
	public void setData(TicketDTO data) {
		this.data = data;
	}
	
	

	

}
