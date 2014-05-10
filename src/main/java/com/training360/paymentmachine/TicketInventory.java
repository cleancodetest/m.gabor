package com.training360.paymentmachine;

public interface TicketInventory {
	ParkingTicket getParkingTicketById(String ticketId);
	void addParkingTicket(ParkingTicket ticket);
	void updateParkingTicket(ParkingTicket ticket);
}
