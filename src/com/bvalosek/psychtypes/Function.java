//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

/**
 * Cognitive function
 */
public class Function {

    private boolean _isExtroverted = false;

    /** judging = T or F, !judging = S or N */
    private boolean _isJudging = false;

    /** strong = N or T, !strong = S or F */
    private boolean _isStrong = false;

    /** create via the degrees directly */
    public Function (boolean e, boolean j, boolean s) {
        _isExtroverted = e;
        _isJudging = j;
        _isStrong = s;
    }

    /** create via code */
    public Function (int n) {
        _isExtroverted  = (n & 4) == 4 ? true : false;
        _isJudging      = (n & 2) == 2 ? true : false;
        _isStrong       = (n & 1) == 1 ? true : false;
    }

    /** create via string symbol */
    public Function (String s) {
        s = s.toLowerCase();

        // error?
        if (s.length() != 2)
            return;

        char[] symbol = s.toCharArray();

        // determine degrees
        if (symbol[1] == 'e')
            _isExtroverted = true;
        switch (symbol[0]) {
            case 't':
                _isJudging = true; _isStrong = true; break;
            case 'f':
                _isJudging = true; _isStrong = false; break;
            case 'n':
                _isJudging = false; _isStrong = true; break;
            case 's':
                _isJudging = false; _isStrong = false; break;
        }
    }

    /** @return a integer code */
    public int getId() {
        int n = 0;
        n += _isExtroverted ? 1 : 0;
        n += _isJudging ? 2 : 0;
        n += _isStrong ? 4 : 0;

        return n;
    }

    /** @return opposite function */
    public Function getOpposite() {
        return new Function(!_isExtroverted, _isJudging, !_isStrong);
    }

    /** @return true if two given functions are mutually exclussive */
    public static boolean isMutuallyExclusive(Function a, Function b) {
        /* the same, not mutually exclusive ?!?! */
        if (    a._isExtroverted == b._isExtroverted &&
                a._isJudging == b._isJudging &&
                a._isStrong == b._isStrong)
            return false;

        /* same attitude, same domain (juding or percieving), eg Ti/Fi,
            * Ne/Se, etc */
        if (    a._isExtroverted == b._isExtroverted &&
                a._isJudging == b._isJudging)
            return true;

        /* same letter, different attitude, eg Si/Se, Fi/Fe, etc*/
        if (    a._isJudging == b._isJudging &&
                a._isStrong == b._isStrong &&
                a._isExtroverted != b._isExtroverted)
            return true;

        // otherwise, not mutually exclusive
        return false;
    }

    /** @return true of this is a Judging (T/F) function */
    public boolean isJudging() {
        return _isJudging;
    }

    /** @return true if this is an Extroverted function */
    public boolean isExtroverted() {
        return _isExtroverted;
    }

    /** use ID as the hash */
    @Override public int hashCode() {
        return getId();
    }

    /** @return true if equal ID */
    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Function t = (Function)o;

        // equal if same ID
        return t.getId() == getId();
    }
    /** @return symbol for this function */
    @Override public String toString() {
        String s = "";
        if (_isJudging && _isStrong) s = "T";
        if (_isJudging && !_isStrong) s = "F";
        if (!_isJudging && _isStrong) s = "N";
        if (!_isJudging && !_isStrong) s = "S";

        // add e or i at the end
        s += _isExtroverted ? "e" : "i";

        return s;
    }
}
