package com.training360.paymentmachine;

import java.util.SortedMap;
import java.util.TreeMap;

import javax.security.auth.login.FailedLoginException;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PaymentMachineTest {

	private TicketInventory tickets;
	private CoinInventory coins;
	private PaymentMachine machine;

	public  TicketInventory generateTicketInventory(){
		TicketInventory tickets = new TicketInventoryImpl();
		tickets.addParkingTicket(new MyParkingTicket("K1", 100));
		tickets.addParkingTicket(new MyParkingTicket("K2", 500));
		tickets.addParkingTicket(new MyParkingTicket("K3", 600));
		tickets.addParkingTicket(new MyParkingTicket("K4", 700));
		tickets.addParkingTicket(new MyParkingTicket("K5", 200));
		tickets.addParkingTicket(new MyParkingTicket("K6", 300));
		tickets.addParkingTicket(new MyParkingTicket("K7", 100));
		tickets.addParkingTicket(new MyParkingTicket("K8", 200));
		tickets.addParkingTicket(new MyParkingTicket("K9", 400));


		return tickets;
	}

	public static CoinInventory generateInitalCoinInventory(){
		CoinInventory coins = new CoinInventoryImpl();
		SortedMap<Integer, Integer> coinMap = new TreeMap<Integer, Integer>();

		coinMap.put(5, 30);
		coinMap.put(10, 30);
		//coinMap.put(20, 30);
		coinMap.put(50, 30);
		coinMap.put(100, 30);
		coinMap.put(200, 30);
		coinMap.put(500, 30);
		coinMap.put(1000, 30);

		coins.putCoins(coinMap);
		return coins;
	}
	@BeforeTest
	public void setUp(){
		tickets =  generateTicketInventory();
		coins = generateInitalCoinInventory();
		machine = new PaymentMachineImpl(tickets,coins);
	}
	@Test(expectedExceptions=TicketNotFoundException.class)
	public void testNonExistingTicketThrowsException(){
		machine.insertTicket("L1");
	}

	@Test
	public void testAfterInsertionOfExistingTicketActiveTicketIdisSet(){
		machine.insertTicket("K1");
		Assert.assertEquals(machine.getActiveTicketId(), "K1");
	}
	@Test
	public void testInsertTwoTicketsActiveWillBeTheSecond(){
		machine.insertTicket("K1");
		machine.insertTicket("K2");
		Assert.assertEquals(machine.getActiveTicketId(), "K2");
	}
	@Test
	public void testInsertTicketWith50AndPay100GivesRefund(){
		//given
		TicketInventory tickets = new TicketInventoryImpl();
		tickets.addParkingTicket(new MyParkingTicket("Amt50", 50));

		CoinInventory coins = new CoinInventoryImpl();
		coins.addCoin(50);

		PaymentMachine machine = new PaymentMachineImpl(tickets, coins);

		machine.insertTicket("Amt50");
		int refund = machine.insertCoinGetReturnRefund(100);

		Assert.assertEquals(refund,50);
	}
	@Test(expectedExceptions=NoActiveTicketException.class)
	public void testCancelThrowsExceptionWhenNoTicketInserted(){
		//given
		TicketInventory tickets = Mockito.mock(TicketInventory.class);
		CoinInventory coins = Mockito.mock(CoinInventory.class);

		PaymentMachine machine = new PaymentMachineImpl(tickets, coins);

		//when
		machine.cancelOperation();
	}
	@Test(expectedExceptions=NoActiveTicketException.class)
	public void testCancelMakesTicketEmpty(){
		//given
		TicketInventory tickets = new TicketInventoryImpl();
		CoinInventory coins = Mockito.mock(CoinInventory.class);
		
		tickets.addParkingTicket(new MyParkingTicket("T1", 200));
		
		PaymentMachine machine = new PaymentMachineImpl(tickets, coins);

		//when
		machine.insertTicket("T1");
		machine.cancelOperation();
		
		//then
		machine.getActiveTicketId();
	}
}

