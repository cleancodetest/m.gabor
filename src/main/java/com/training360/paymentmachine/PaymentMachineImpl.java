package com.training360.paymentmachine;

import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class PaymentMachineImpl implements PaymentMachine{

	private TicketInventory ticketInventory;
	private CoinInventory coinInventory;
	private ParkingTicket ticket;

	public PaymentMachineImpl(TicketInventory ticketInventory, CoinInventory coinInventory){
		this.ticketInventory = ticketInventory;
		this.coinInventory = coinInventory;
	}

	public void insertTicket(String ticketId)
	{
		ticket = ticketInventory.getParkingTicketById(ticketId);
	}

	public void cancelOperation(){
		if(ticket == null){
			throw new NoActiveTicketException();
		}
		ticket = null;
	}

	public int insertCoinGetReturnRefund(int coin){
		
		checkArgument(coin);
		
		int handlePaymentReturnRefund = handlePaymentReturnRefund(coin);
		
		ifFullyPaidThenPrintMessageAndClearTicket();
		
		return handlePaymentReturnRefund;
	}

	private int handlePaymentReturnRefund(int coin){
		
		int amtToReturn = 0;
		if(noRefundOrRefundPossible(coin)){
	
				amtToReturn = ticket.payAmtAndGetReturnAmt(coin);
				
				giveBackRefundIfNecessary(amtToReturn);
				
		}else{
				throw new NoRefundAvailableException();
		}
		return amtToReturn;
	}

	private void giveBackRefundIfNecessary(int amtToReturn) {
		if(amtToReturn > 0){
			CoinInventory denominations =  coinInventory.GetCoinsForReturn(amtToReturn);
			printOutDenominations(denominations);
		}
	}

	private boolean noRefundOrRefundPossible(int coin) {
		int amtToPayBack = getAmountToPayBack(coin);
		return amtToPayBack == 0 || coinInventory.canReturnAmt(amtToPayBack);
	}

	private void ifFullyPaidThenPrintMessageAndClearTicket() {
		if(ticket.isPayed()){
			System.out.printf("Ticket %s fully paid with amount %d%n",ticket.getTicketId(),ticket.getOriginalAmountToPay());
			ticket = null;
		}
	}

	private void checkArgument(int coin) {
		if(ticket == null){
			throw new NoActiveTicketException();
		}
		if(!Denomination.allowedDenominations.contains(coin)){
			throw new UnknownCoinException();
		}
	}

	private void printOutDenominations(CoinInventory denominations) {
		
		System.out.println("Returning coins");
		
		for(Entry<Integer, Integer> entry:denominations.getInventory().entrySet()){
			System.out.printf("Denomination: %d, count: %d%n",entry.getKey(),entry.getValue());
		}
	}

	private int getAmountToPayBack(int coin) {

		int diff = coin - ticket.getAmountToPay();
		return diff > 0  ? diff : 0;
	}

	public String getActiveTicketId(){
		if(ticket == null)
			throw new NoActiveTicketException();
		return ticket.getTicketId();
	}
}
