//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

// imports
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Generic class that is used when we need to score and sort objects against
 * an int score. Example: scoring various Function and Type objects
 *
 * Not necesarily fast, only used for small sets of data
 */
public class Scorer<T> {

    protected List<Entry> _entries = new ArrayList<Entry>();

    /** Entry class, T objects sorted by score */
    private class Entry implements Comparable {
        private T   _object = null;
        private int _score = 0;

        public Entry(T object, int score) {
            _object = object;
            _score = score;
        }

        /** sort based on the score */
        public int compareTo(Object o) {
            if (o.getClass() != getClass())
                return -1;

            Entry e = (Entry)o;
            if (_score == e._score)
                return 0;
            if (_score > e._score)
                return 1;
            else
                return -1;
        }

    }

    /** insert an element */
    public void add(T t, int score) {
        Entry entry = new Entry(t, score);
        _entries.add(entry);
    }

    /** get the sorted (high to low) array of just type T */
    public List<T> getSortedList() {
        List<T> list = new ArrayList<T>();

        Collections.sort(_entries);
        Collections.reverse(_entries);
        for (Entry e : _entries) {
            list.add(e._object);
        }

        return list;
    }
}
