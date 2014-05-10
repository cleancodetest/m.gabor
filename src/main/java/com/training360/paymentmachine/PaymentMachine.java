package com.training360.paymentmachine;

public interface PaymentMachine {
	
	void insertTicket(String ticketId);
	
	void cancelOperation();
	
	int insertCoinGetReturnRefund(int coin);
	
	String getActiveTicketId();
	
}
