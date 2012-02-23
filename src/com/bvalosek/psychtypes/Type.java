//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

// imports
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
        setTypeCode(sType);
    }

    /** internal function used to code in via string */
    private void setTypeCode(String sType) {
        String s = sType.toLowerCase();
        _typeCode = 0;

        for (char c : s.toCharArray()) {
            switch (c) {
                case 'e': _typeCode += 8; break;
                case 'n': _typeCode += 4; break;
                case 't': _typeCode += 2; break;
                case 'j': _typeCode += 1; break;
            }
        }
    }

    /** Create via function groups */
    public Type(Function dom, Function aux) {
        String sDom = dom.toString(); String sAux = aux.toString();

        // build it up via string essentially
        String att , perc, jud, ori;
        att = sDom.substring(1,2);

        if (dom.isJudging()) {
            jud = sDom.substring(0,1);
            perc = sAux.substring(0,1);

            if (dom.isExtroverted())
                ori = "j";
            else
                ori = "p";
        } else {
            jud = sAux.substring(0,1);
            perc = sDom.substring(0,1);

            if (dom.isExtroverted())
                ori = "p";
            else
                ori = "j";
        }

        setTypeCode(att + perc + jud + ori);
    }

    /**Create via String symbol functions */
    public Type(String dom, String aux) {
        this(new Function(dom), new Function(aux));
    }

    /** @return true if equal codes */
    @Override public boolean equals(Object o) {
        ri = "p";

        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Type t = (Type)o;

        // true if type codes are equal
        return t._typeCode == _typeCode;
    }

    /** @return A List of functions based on the code */
    public List<Function> getCognativeFunctions() {
        ArrayList<Function> functions = new ArrayList<Function>();

        // If judging or sensing
        String domEx = "";
        String domIn = "";
        if (has('j')) {
            domEx += getJudging();
            domIn += getPerception();
        } else {
            domEx += getPerception();
            domIn += getJudging();
        }

        // if introverted or extroverted
        if (has('e')) {
            functions.add(new Function(domEx + "e"));
            functions.add(new Function(domIn + "i"));
        } else {
            functions.add(new Function(domIn + "i"));
            functions.add(new Function(domEx + "e"));
        }

        // add in tert and inf
        functions.add(functions.get(1).getOpposite());
        functions.add(functions.get(0).getOpposite());

        return functions;
    }

    /** @return True if a code contains character c in it */
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

    // Getting the various parts of the code in String format
    public char getAttitude() {
        return toString().toCharArray()[0];
    }
    public char getPerception() {
        return toString().toCharArray()[1];
    }
    public char getJudging() {
        return toString().toCharArray()[2];
    }
    public char getOrientation() {
        return toString().toCharArray()[3];
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
