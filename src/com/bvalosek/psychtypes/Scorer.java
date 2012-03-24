//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

// imports
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * Generic class that is used when we need to score and sort objects against
 * an int score. Example: scoring various Function and Type objects
 *
 * Not necesarily fast, only used for small sets of data
 */
public class Scorer<T> {

	private final Comparator<Map.Entry<T, Integer>> COUNT_COMPARATOR 
		= new Comparator<Map.Entry<T,Integer>>() {
			@Override
			public int compare(Entry<T, Integer> left, Entry<T, Integer> right) {
				return left.getValue().compareTo(right.getValue());
			}
		};
		
	private final Map<T, Integer> counter = new HashMap<T, Integer>();
	
    /** insert an element */
    public void add(T t, int score) {
    	counter.put(t, score);
    }

    /** Add the elements of a T => Integer map */
    public void add(Map<T, Integer> map) {
    	counter.putAll(map);
    }

    /** get the sorted (high to low) array of just type T */
    public List<T> getSortedList() {
        return getSortedList(Integer.MIN_VALUE);
    }

    /** Get the sorted (high to low) array such that score is greater than or
     * equal to the threshold */
    public List<T> getSortedList(int threshold) {
    	PriorityQueue<Map.Entry<T,Integer>> pscorer = new PriorityQueue<Map.Entry<T,Integer>>(counter.size(), COUNT_COMPARATOR);
    	pscorer.addAll(counter.entrySet());
    	
        List<T> list = new ArrayList<T>();
        Map.Entry<T, Integer> entry;
    	while((entry = pscorer.poll()) != null) {
    		if (entry.getValue() >= threshold)
    			list.add(entry.getKey());
    	}
        return list;
    }
}
