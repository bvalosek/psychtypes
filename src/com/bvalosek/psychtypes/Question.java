//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

/**
 * Question used in quizes
 */
public class Question {

    private String _content = "";
    private List<String> _responses = new ArrayList<String>();
    private int _chosenResponse = null;

    /** Create with question/response */
    public Question (String s, List<String> responses) {
        _content = s;
        _responses = response;
    }

    /** Answer the question */
    public choseResponse(int n) {
        _chosenResponse = n;
    }

    /** @return the answer */
    public getResponse() {
        return _chosenResponse;
    }
}
