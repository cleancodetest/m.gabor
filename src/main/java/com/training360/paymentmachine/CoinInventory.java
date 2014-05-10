package com.training360.paymentmachine;

import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public interface CoinInventory  {
	public void addCoin(int coin);
	public void removeCoin(int coin);
	public void putCoins(SortedMap<Integer , Integer>  coins); 
	public NavigableMap<Integer, Integer> getInventory();
	public boolean canReturnAmt(int amtToPayBack);
	public CoinInventory GetCoinsForReturn(int amtToReturn);
}
