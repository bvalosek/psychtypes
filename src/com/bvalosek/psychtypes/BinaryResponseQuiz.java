//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

/**
 * Quiz that has 2 possible responses, each adding to either a cognitive
 * function score or the Extroverted/Introverted score
 */
public class BinaryResponseQuiz implements Quizable {

    /**
     * @return the next question off the stack, or the same one if unanswered
     */
    public Question getNextQuestion() {
        Question q = new Question("Some question...", "A", "B");


        return q;
    }

    public boolean isQuizComplete() {

        return true;
    }

    public Type getResults() {

        return new Type("entp");
    }

    public int getAnsweredCount() {

        return 0;
    }

    public int getRemainingCount() {

        return 0;
    }

}
