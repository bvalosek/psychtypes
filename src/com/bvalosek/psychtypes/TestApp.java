//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;
import java.util.List;

/**
 *
 * Application for testing out the psychtypes stuff
 *
 */
public class TestApp {

    /** app entry point */
    public static void main(String[] args) {
        /*
        for (int n=0; n < 16; n++) {
            Type t = new Type(n);
            List<Function> functions = t.getCognativeFunctions();
            String s = t + " has functions ";
            for (Function f : functions) {
                s += f + ", ";
            }
            System.out.println (s);

            s = "";
            for (Type.Transform x : Type.Transform.values()) {
                s += x + ": " + t.transform(x) + ", ";
            }
            System.out.println (s);

        }
        */

        Type entp = new Type("esfJ");

        for (Type.Transform x : Type.Transform.values()) {
            Type t = entp.transform(x);
            String funcs = "";
            for (Function f : t.getCognativeFunctions())
                funcs += f + " ";
            System.out.println(x + "\n  " + t + "\n   " + funcs);
        }

    }

}
