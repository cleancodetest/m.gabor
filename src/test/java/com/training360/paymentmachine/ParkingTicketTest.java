package com.training360.paymentmachine;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ParkingTicketTest {

	@Test
	public void testParkingTicketPaid() {
		// given
		ParkingTicket ticket = new MyParkingTicket("T1", 50);

		// when
		ticket.payAmtAndGetReturnAmt(ticket.getAmountToPay());

		// then
		Assert.assertEquals(true, ticket.isPayed());
	}

	@Test
	public void testParkingTicketIdStored() {
		// given
		ParkingTicket ticket = new MyParkingTicket("T1", 50);

		// when

		// then
		Assert.assertEquals(ticket.getTicketId(), "T1");
	}

	@Test
	public void testParkingTicketAmountStored() {
		// given
		ParkingTicket ticket = new MyParkingTicket("T1", 50);

		// when

		// then
		Assert.assertEquals(ticket.getAmountToPay(), 50);
	}
	@Test
	public void testParkingTicketNotPayed() {
		// given
		ParkingTicket ticket = new MyParkingTicket("T1", 50);

		// when

		// then
		Assert.assertEquals(false,ticket.isPayed());
	}
	@Test
	public void testParkingTicketInitial50AmountToPayIs30If20PaidIn() {
		// given
		ParkingTicket ticket = new MyParkingTicket("T1", 50);

		// when
		ticket.payAmtAndGetReturnAmt(20);
		// then
		Assert.assertEquals(ticket.getAmountToPay(), 30);
	}
}
