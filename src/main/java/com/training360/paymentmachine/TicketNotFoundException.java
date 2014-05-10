package com.training360.paymentmachine;

public class TicketNotFoundException extends RuntimeException {
	
	private String ticketId;
	
	public TicketNotFoundException(String ticketId){
		this.ticketId = ticketId;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Ticket not found";
	}
	public String getTicketId() {
		return ticketId;
	}
}
