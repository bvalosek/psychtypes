//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

/**
 * A question with two responses, each with a "score code" assoiated with it.
 * Used by BinaryResponseQuiz object
 */
public class BinaryQuestion extends Question {

    // e.g. Si, Ne, E, I etc
    private String _scoreCodeA = "";
    private String _scoreCodeB = "";

    /** Create with scoring codes */
    public BinaryQuestion(  String s, String a,
                            String b, String as, String bs) {
        super(s, a, b);

        _scoreCodeA = as;
        _scoreCodeB = bs;
    }

    /* swap the order of the response */
    public void swapResponses() {
        String a = _responses.get(0);
        String b = _responses.get(1);

        String as = _scoreCodeA;
        String bs = _scoreCodeB;

        _responses.set(0, b);
        _responses.set(1, a);
        _scoreCodeA = bs;
        _scoreCodeB = as;
    }

    /** @return true if responses are mutually exclusive */
    public boolean isMutuallyExclusive() {
        return Function.isMutuallyExclusive(
                new Function(_scoreCodeA), new Function(_scoreCodeB));
    }

    /** @return string symbol for chosen response */
    public String getResponseCode() {
        return _response == 0 ? _scoreCodeA : _scoreCodeB;
    }

    /** @return what was not chosen */
    public String getOtherCode() {
        return _response == 0 ? _scoreCodeB : _scoreCodeA;
    }
}

