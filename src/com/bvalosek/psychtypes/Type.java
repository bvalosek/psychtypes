//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

/**
 *
 * Psychological Type class based on the Jungian Typology theory, used in
 * MBTI-like tests, etc
 *
 */
public class Type {

    /** Internal represenation of the code, 0-15 */
    int     _typeCode = 0;

    /** The 8 different cognitive functions */
    public enum CognativeFunction {
        SENSING_INTROVERTED("Si"),
        SENSING_EXTROVERTED("Se"),
        INTUITION_INTROVERTED("Ni"),
        INTUITION_EXTROVERTED("Ne"),
        FEELING_INTROVERTED("Fi"),
        FEELING_EXTROVERTED("Fe"),
        THINKING_INTROVERTED("Ti"),
        THINKING_EXTROVERTED("Te");

        public String _symbol;

        CognativeFunction(String s) {
            _symbol = s;
        }
    }

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
