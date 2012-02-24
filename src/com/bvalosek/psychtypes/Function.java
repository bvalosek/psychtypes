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
        n += _isStrong ? 2 : 0;
        n += _isJudging ? 4 : 0;

        return n;
    }

    /** @return opposite function */
    public Function getOpposite() {
        return new Function(!_isExtroverted, _isJudging, !_isStrong);
    }

    /** @return true of this is a Judging (T/F) function */
    public boolean isJudging() {
        return _isJudging;
    }

    /** @return true if this is an Extroverted function */
    public boolean isExtroverted() {
        return _isExtroverted;
    }

    /** @return symbol for this function */
    public String toString() {
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
