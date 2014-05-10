package com.training360.paymentmachine;

public class MyParkingTicket implements ParkingTicket {

	private int amtToPay;
	private int originalAmountToPay;
	private String id;

	public MyParkingTicket(String id, int amtToPay){
		this.id = id;
		this.amtToPay = amtToPay;
		this.originalAmountToPay = amtToPay;
	}
	
	public String getTicketId() {
		// TODO Auto-generated method stub
		return id;
	}

	public boolean isPayed() {
		// TODO Auto-generated method stub
		return amtToPay == 0;
	}

	public int payAmtAndGetReturnAmt(int amt) {
		
		if(amt <= amtToPay){
			amtToPay -= amt;
			return 0;
		}else{
			int amtToReturn = amt - amtToPay;
			amtToPay = 0;
			return amtToReturn;
		}

	}

	public int getAmountToPay() {
		// TODO Auto-generated method stub
		return amtToPay;
	}

	public int getOriginalAmountToPay() {
		// TODO Auto-generated method stub
		return originalAmountToPay;
	}

}
