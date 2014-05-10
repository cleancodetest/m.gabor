package com.training360.paymentmachine;

import java.util.SortedMap;
import java.util.TreeMap;

public class TicketInventoryImpl implements TicketInventory {
	
	private SortedMap<String, ParkingTicket> tickets = new TreeMap<String, ParkingTicket>();

	public ParkingTicket getParkingTicketById(String ticketId){
		
		if(!tickets.containsKey(ticketId))
			throw new TicketNotFoundException(ticketId);
		
		return tickets.get(ticketId);
		
	}

	public void addParkingTicket(ParkingTicket ticket) {
		tickets.put(ticket.getTicketId(), ticket);
	}

	public void updateParkingTicket(ParkingTicket ticket) {
		tickets.put(ticket.getTicketId(), ticket);
	}

}
