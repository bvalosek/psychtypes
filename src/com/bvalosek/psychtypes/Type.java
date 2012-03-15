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

    /** Internal representation of the code, 0-15 */
    int     _typeCode = 0;

    /** Codes for the various transforms Type => Type
     *
     * Inspired by info at http://typelogic.com/pairs.html
     */
    public enum Transform {
        IDENTITY(0),    /* identity */
        COMPLEMENT(1),  /* compatible strengths, opposite emphasis */
        COMPANION(2),   /* similar modes of expression, work well together */
        ADVISOR(3),     /* each has area of insight the other lacks */
        NEIGHBOR(4),    /* arrive at same place by variant process */
        SUITEMATE(5),   /* prefer similar climates, but different world view */
        ENGIMA(6),      /* a puzzle, totally different and foreign */
        SUPPLEMENT(7),  /* work and play well together, but further removed */
        PAL(8),         /* work and play well together, minimal conflict */
        CONTRAST(9),    /* point and counterpoint each function */
        TRIBESMAN(10),  /* share a sense of culture, but with diff values */
        PEDAGOGUE(11),  /* each both mentor and student to one another */
        COUNTERPART(12),/* similar functions in totally different realms */
        COHORT(13),     /* mutually drawn into experimental escapades */
        NOVELTY(14),    /* intriguingly different, interestingly so */
        ANIMA(15);      /* opposites */

        public int transformCode;
        Transform(int n) { transformCode = n; }
    }

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

        /* Essentially, do the code analysis via a string and
         * create the code from the resulting letters */
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

    /** Create via String symbol functions */
    public Type(String dom, String aux) {
        this(new Function(dom), new Function(aux));
    }

    /** return a list of all the possible types */
    public static List<Type> allTypes() {
        List<Type> types = new ArrayList<Type>();

        for (int n = 0; n < 16; n++)
            types.add(new Type(n));

        return types;
    }

    /** use typecode as the hash */
    @Override public int hashCode() {
        return _typeCode;
    }

    /** @return true if equal codes */
    @Override public boolean equals(Object o) {
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

    /** @return integer code for this type */
    public int getCode() {
        return _typeCode;
    }

    /** Transform a type */
    public Type transform(Transform x) {
        // XOR our code to make the new type
        return new Type(_typeCode ^ x.transformCode);
    }

    /** Get what transform/relationship we have with another type */
    public Transform getTransform(Type t) {
        // just brute-force try them all
        for (Transform u : Transform.values()) {
            if (t.transform(u).equals(this))
                return u;
        }

        // shouldn't happen, but...
        return null;
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
            case 'e':
                return (_typeCode & 8) == 8;
            case 's':
            case 'n':
                return (_typeCode & 4) == 4;
            case 'f':
            case 't':
                return (_typeCode & 2) == 2;
            case 'p':
            case 'j':
                return (_typeCode & 1) == 1;
        }

        // wut?
        return false;
    }

    // Getting the various parts of the code in String format
    public String getAttitude() {
        return toString().substring(0,1);
    }
    public String getPerception() {
        return toString().substring(1,2);
    }
    public String getJudging() {
        return toString().substring(2,3);
    }
    public String getOrientation() {
        return toString().substring(3);
    }

    // toggle various parts
    public Type toggleAttitude() {
        return new Type(_typeCode ^= 8);
    }
    public Type togglePerception() {
        return new Type(_typeCode ^= 4);
    }
    public Type toggleJudging() {
        return new Type(_typeCode ^= 2);
    }
    public Type toggleOrientation() {
        return new Type(_typeCode ^= 1);
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
