//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

/**
 *
 * Application for testing out the psychtypes stuff
 *
 */
public class TestApp {

    /** app entry point */
    public static void main(String[] args) {

        // try Type
        Type type = new Type("entp");

        for (int n=0; n < 16; n++) {
            Type t = new Type(n);
            System.out.println("Code " + n + " is type " + t);
            Type u = new Type(t.toString());
            System.out.println("  checked as " + u);
        }
    }

}
