//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * Psychological Type class based on the Jungian Typology theory, used in
 * MBTI-like tests, etc
 *
 */
public class Type {

    /** Internal represenation of the code, 0-15 */
    int     _typeCode = 0;

    /** Create via code number, mask with 0b1111 just to be safe */
    public Type(int n) {
        _typeCode = n & 0xf;
    }

    /** Create via a String code */
    public Type(String sType) {
        String s = sType.toLowerCase();

        for (char c : s.toCharArray()) {
            switch (c) {
                case 'e': _typeCode += 8; break;
                case 'n': _typeCode += 4; break;
                case 't': _typeCode += 2; break;
                case 'j': _typeCode += 1; break;
            }
        }
    }

    /** figure out functions */
    public List<Function> getCognativeFunctions() {
        ArrayList<Function> functions = new ArrayList<Function>();

        return functions;
    }

    /** return true if a code contains character c in it */
    public boolean has(char c) {
        switch (c) {
            case 'i':
                return (_typeCode & 8) == 0;
            case 'e':
                return (_typeCode & 8) == 8;
            case 's':
                return (_typeCode & 4) == 0;
            case 'n':
                return (_typeCode & 4) == 4;
            case 'f':
                return (_typeCode & 2) == 0;
            case 't':
                return (_typeCode & 2) == 2;
            case 'p':
                return (_typeCode & 1) == 0;
            case 'j':
                return (_typeCode & 1) == 1;
        }

        // wut?
        return false;
    }

    /** @return A pretty string code, e.g. ENTJ  */
    @Override public String toString() {
        String sOut = "";

        sOut += (_typeCode & 8) == 8 ? "E" : "I";
        sOut += (_typeCode & 4) == 4 ? "N" : "S";
        sOut += (_typeCode & 2) == 2 ? "T" : "F";
        sOut += (_typeCode & 1) == 1 ? "J" : "P";

        return sOut;
    }

    private void log (String s) {
        System.out.println(s);
    }
}
