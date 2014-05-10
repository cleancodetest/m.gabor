package com.training360.paymentmachine;

import java.util.TreeMap;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CoinInventoryTest {

	private CoinInventory coins;
	
	@BeforeTest
	public void setUp(){
		coins = new CoinInventoryImpl();
	}
	@Test
	public void testInitialInventoryIsEmpty(){
		CoinInventory emptyInventory = new CoinInventoryImpl();
		Assert.assertEquals(0, emptyInventory.getInventory().size());
	}
	@Test(expectedExceptions=UnknownCoinException.class)
	public void testUnknownCoinInsertionThrowsException(){
		
		//given
		CoinInventory testInventory = new CoinInventoryImpl();
		
		//when
		testInventory.addCoin(21);
	}
	@Test
	public void testCannotReturnFromEmptyInventory(){
		//given
		CoinInventory testInventory = new CoinInventoryImpl();
		Assert.assertEquals(false, testInventory.canReturnAmt(10));
	}
	@Test
	public void testCanReturnInsertedCoin(){
		//given
		CoinInventory testInventory = new CoinInventoryImpl();
		
		//when
		testInventory.addCoin(20);
		
		//then
		Assert.assertEquals(true, testInventory.canReturnAmt(20));
	}
	@Test
	public void testCanAfterRemoveCoinInventoryIsEmpty(){
		//given
		CoinInventory testInventory = new CoinInventoryImpl();
		
		//when
		testInventory.addCoin(20);
		testInventory.removeCoin(20);
		//then
		Assert.assertEquals(0, testInventory.getInventory().size());
	}
	@Test
	public void testCanReturnInsertedCoins(){
		//given
		CoinInventory testInventory = new CoinInventoryImpl();
		
		//when
		testInventory.addCoin(20);
		testInventory.addCoin(20);
		testInventory.addCoin(10);

		//then
		Assert.assertEquals(true, testInventory.canReturnAmt(50));
	}
	
	@Test
	public void testReturnsInsertedCoinsWhenAskedBack(){
		//given
		CoinInventory testInventory = new CoinInventoryImpl();
		
		//when
		testInventory.addCoin(20);
		testInventory.addCoin(20);
		testInventory.addCoin(10);

		CoinInventory getCoinsForReturn = testInventory.GetCoinsForReturn(50);
		
		//then
		Assert.assertEquals(2, getCoinsForReturn.getInventory().size());
	}
	@Test(expectedExceptions=NoRefundAvailableException.class)
	public void testWhenNoRefundThrowException(){
		//given
		CoinInventory testInventory = new CoinInventoryImpl();
		
		//when
		CoinInventory getCoinsForReturn = testInventory.GetCoinsForReturn(50);
		
		//then
		
	}
}
