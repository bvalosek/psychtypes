//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

// import
import java.util.List;
import java.util.ArrayList;

/**
 * Question used in quizes
 */
public class Question {

    protected String        _content = "";
    protected int           _response = 0;
    protected List<String>  _responses = new ArrayList<String>();

    /** empty ctor */
    public Question() { }

    /** Create wit question response as strings */
    public Question (String s, String ... responses) {
        _content = s;
        _responses = new ArrayList<String>();

        for (String r : responses) {
            _responses.add(r);
        }
    }

    /** Answer the question */
    public void choseResponse(int n) {
        _response = n;
    }

    /** @return the answer */
    public int getResponse() {
        return _response;
    }

    /** @return the actual question string */
    public String getContent() {
        return _content;
    }

    /** @return the list of possible responses */
    public List<String> getResponses() {
        return _responses;
    }
}
