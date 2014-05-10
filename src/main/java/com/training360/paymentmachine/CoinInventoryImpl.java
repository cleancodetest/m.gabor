package com.training360.paymentmachine;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class CoinInventoryImpl implements CoinInventory {

	private TreeMap<Integer , Integer> coins = new TreeMap<Integer, Integer>();


	public void addCoin(int coin){

		checkArgument(coin);

		insertOrUpdateCoinInventoryWithCoin(coin,1);
	}

	private void checkArgument(int coin){
		if(!Denomination.allowedDenominations.contains(coin)){
			throw new UnknownCoinException();
		}
	}
	public void removeCoin(int coin){
		
		checkArgument(coin);

		updateOrRemoveCoinRepository(coin);
	}

	private void updateOrRemoveCoinRepository(int coin) {
		
		updateOrRemoveCoinFromInventory(this,coin);
		
	}

	private void updateOrRemoveCoinFromInventory(
			CoinInventory inventory, int coin) {
		
		if(inventory.getInventory().containsKey(coin)){
			int cnt = inventory.getInventory().get(coin);
			if(cnt > 1){
				inventory.getInventory().put(coin, --cnt);
			}else{
				inventory.getInventory().remove(coin);
			}
		}
		
	}

	public NavigableMap<Integer, Integer> getInventory() {

		return coins;
	}

	public void putCoins(SortedMap<Integer , Integer>  coinsToPut) {

		for(  Entry<Integer, Integer> coinEntry : coinsToPut.entrySet()){
			updateOrInsertCoins(coinEntry);
		}

	}

	private void updateOrInsertCoins(Entry<Integer, Integer> coinEntry) {

		insertOrUpdateCoinInventoryWithCoin(coinEntry.getKey(), coinEntry.getValue());

	}
	private void insertOrUpdateCoinInventoryWithCoin(int coin, int count) {

		insertOrUpdateInventoryWithCoin(this,coin,count);
		
	}

	private void insertOrUpdateInventoryWithCoin(
			CoinInventory inventory, int coin, int count) {
		if(inventory.getInventory().containsKey(coin)){
			int value = inventory.getInventory().get(coin);
			inventory.getInventory().put(coin, value + count);
		}
		else{
			inventory.getInventory().put(coin, count);
		}
		
	}

	public boolean canReturnAmt(int amtToPayBack){
		
		CoinInventory coinsRemaining = new CoinInventoryImpl();
		coinsRemaining.putCoins(coins);
		CoinInventory tmpInventory = new CoinInventoryImpl();
		
		return canReturnAmtFromInventory(amtToPayBack, coinsRemaining, tmpInventory);
	}

	private boolean canReturnAmtFromInventory(int amtToPayBack, CoinInventory inventoryFrom, CoinInventory inventoryTo) {

		int amtToReturn = amtToPayBack;

		boolean areMoreDenomination = true;

		while(amtToReturn > 0  && areMoreDenomination){
			Entry<Integer, Integer> floorEntry = inventoryFrom.getInventory().floorEntry(amtToReturn);
			if(floorEntry == null){
				areMoreDenomination = false;
				break;
			}
			amtToReturn -= floorEntry.getKey();
			handoverCoin(inventoryFrom, inventoryTo, floorEntry.getKey());
		}
		return amtToReturn == 0;
	}

	private void handoverCoin(
			CoinInventory coinsFrom,
			CoinInventory coinsTo, Integer coinToPut) {

		insertOrUpdateInventoryWithCoin(coinsTo, coinToPut, 1);
		
		updateOrRemoveCoinFromInventory(coinsFrom, coinToPut);
		
	}


	public CoinInventory GetCoinsForReturn(int amtToReturn) {
		CoinInventory tmpInventory = new CoinInventoryImpl();
		
		if(canReturnAmtFromInventory(amtToReturn, this, tmpInventory)){
			return tmpInventory;
		}else{
			throw new NoRefundAvailableException();
		}


	}
}
