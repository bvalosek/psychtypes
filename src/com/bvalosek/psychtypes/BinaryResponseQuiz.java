//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

// imports
import java.util.List;
import java.util.ArrayList;

/**
 * Quiz that has 2 possible responses, each adding to either a cognitive
 * function score or the Extroverted/Introverted score
 */
public class BinaryResponseQuiz implements Quizable {

    /** main list of questions */
    private List<BinaryResponseQuestion> _questions =
        new ArrayList<BinaryResponseQuestion>();

    /**
     * question with scoring information attached
     */
    public class BinaryResponseQuestion extends Question {

        // e.g. Si, Ne, E, I etc
        private String _scoreCodeA = "";
        private String _scoreCodeB = "";

        /** Create with scoring codes */
        public BinaryResponseQuestion(  String s, String a,
                                        String b, String as, String bs) {
            _content = s;
            List<String> r = new ArrayList<String>(); r.add(a); r.add(b);
            _scoreCodeA = as;
            _scoreCodeB = bs;
        }
    }

    /**
     * @return the next question off the stack, or the same one if unanswered
     */
    public Question getNextQuestion() {
        Question q = new Question("Some question...", "A", "B");
        return q;
    }

    /** @return true if we can finish the quiz */
    public boolean isQuizComplete() {
        return true;
    }

    /** @return a Type if we're done or null if not */
    public Type getResults() {

        return new Type("entp");
    }

    /** @return total questions answered thus far */
    public int getAnsweredCount() {

        return 0;
    }

    /** @return total potential questions remaining */
    public int getRemainingCount() {

        return 0;
    }

}
