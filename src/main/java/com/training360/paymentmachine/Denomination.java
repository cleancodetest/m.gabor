package com.training360.paymentmachine;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class Denomination{
	public static TreeSet<Integer> allowedDenominations = new TreeSet<Integer>(Arrays.asList(1,2,5,10,20,50,100,200,500,1000,2000,5000,10000,20000));
}
