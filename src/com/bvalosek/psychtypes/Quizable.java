//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

/**
 * Interface used for quizes
 */
public Interface Quizable {

    /** Request another question from the stack. Consecutive calls will return
     * the same question until answered */
    public Question getNextQuestion();

    /** @return True if the quiz is over, or at least could be finished at this
     * time in the case of optional-length quizes */
    public boolean isQuizComplete();

    /** @return Quiz results */
    public Type getResults();

    /** @return number of answered questions */
    public int getAnsweredCount();

    /** @return number of possible questions left */
    public int getRemainingCount();
}
