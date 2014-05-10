package com.training360.paymentmachine;

public interface ParkingTicket {
	
	String getTicketId();
	
	boolean isPayed();
	
	int payAmtAndGetReturnAmt(int amt);
	
	int getAmountToPay();
	
	int getOriginalAmountToPay();
}
