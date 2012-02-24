//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;

// imports
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Quiz that has 2 possible responses, each adding to either a cognitive
 * function score or the Extroverted/Introverted score
 */
public class BinaryResponseQuiz implements Quizable {

    /** main list of questions */
    private List<BinaryQuestion> _questions =
        new ArrayList<BinaryQuestion>();

    /** questions moved here when answered */
    private List<BinaryQuestion> _answeredQuestions =
        new ArrayList<BinaryQuestion>();

    /** current question that's out */
    private BinaryQuestion _curQuestion = null;

    /** random generator */
    private static final Random _rndGen = new Random();

    /**
     * question with scoring information attached
     */
    public class BinaryQuestion extends Question {

        // e.g. Si, Ne, E, I etc
        private String _scoreCodeA = "";
        private String _scoreCodeB = "";

        /** Create with scoring codes */
        public BinaryQuestion(  String s, String a,
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
        /*
         * This should check if the question was skipped before adding it to
         * the completed questions array
         */

        // find the current question, move to answered pile
        if (_curQuestion != null) {
            _questions.remove(_curQuestion);
            _answeredQuestions.add(_curQuestion);
            _curQuestion = null;
        }

        if (_questions.size() == 0)
            return null;

        // pick a random next question
        int r = _rndGen.nextInt(_questions.size());
        _curQuestion = _questions.get(r);

        return _curQuestion;
    }

    /** add another question */
    public void addQuestion(BinaryQuestion q) {
        _questions.add(q);
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
