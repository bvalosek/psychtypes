//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

// imports
import java.util.HashMap;

/**
 * Interface used for quizes
 */
public interface Quizable {

    /** Stash the answered question and get the next one */
    public Question getNextQuestion();

    /** Return the current question */
    public Question getCurrentQuestion();

    /** @return True if the quiz is over, or at least could be finished at this
     * time in the case of optional-length quizes */
    public boolean isQuizComplete();

    /** @return Quiz results */
    public Type getResult();

    /** @return number of answered questions */
    public int getAnsweredCount();

    /** @return number of possible questions left */
    public int getRemainingCount();

    /** @return number of offered questions (answered + skipped) */
    public int getOfferedCount();

    /** @return a map of various degrees and integer values of weight. For
     * example scoring the various cognitive function or dichotomies
     */
    public HashMap<String, Integer> getScoringInfo();
}
