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

    protected String _content = "";
    protected List<String> _responses = new ArrayList<String>();
    protected int _chosenResponse = 0;
    protected boolean _isAnswered = false;

    public Question() { }

    /** Create with question/response */
    public Question (String s, List<String> responses) {
        _content = s;
        _responses = responses;
    }

    /** Create wit question response as strings */
    public Question (String s, String a, String b) {
        _content = s;
        _responses = new ArrayList<String>();
        _responses.add(a);
        _responses.add(b);
    }

    /** Answer the question */
    public void choseResponse(int n) {
        _chosenResponse = n;
        _isAnswered = true;
    }

    /** @return the answer */
    public int getResponse() {
        return _chosenResponse;
    }

    public String getContent() {
        return _content;
    }

    /** @return true if answered */
    public boolean isAnswered() {
        return _isAnswered;
    }
}
