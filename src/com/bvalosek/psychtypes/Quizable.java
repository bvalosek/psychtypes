//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

// imports
import java.util.HashMap;
import java.util.Map;

/**
 * Interface used for the quizes that determine psychological type
 */
public interface Quizable {

    /** Stash the answered question and get the next one */
    public Question getNextQuestion();

    /** Return the current question */
    public Question getCurrentQuestion();

    /** @return Quiz results */
    public Type getResult();

    /** @return Number of answered questions */
    public int getAnsweredCount();

    /** @return Number of possible questions left */
    public int getRemainingCount();

    /** @retrun A map from a function to its current score */
    public Map<Function, Integer> getFunctionScores();

    /** @return A map from from a type to its current score */
    public Map<Type, Integer> getTypeScores();
}
