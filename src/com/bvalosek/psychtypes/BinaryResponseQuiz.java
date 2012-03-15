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
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

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

    /** where we are in the quiz */
    private Phase _phase = Phase.INITIALIZED;

    /** how lowest a function score can go before getting dropped */
    public static final int FUNCTION_DROP_THRESHOLD = -2;

    /** Different quiz phases */
    public enum Phase {
        INITIALIZED, QUESTIONS_MUTEX, QUESTIONS_NORMAL, FINISHED;
    }

    /**
     * Question with scoring information attached
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

    /**
     * @return the next question off the stack, or the same one if unanswered
     */
    @Override public Question getNextQuestion() {

        // find the current question, move to answered pile
        if (_curQuestion != null) {
            _questions.remove(_curQuestion);
            _answeredQuestions.add(_curQuestion);
            _curQuestion = null;
        }

        /** advance to next phase if we need to, and figure out the question
         * pool we can pull from */
        List<BinaryQuestion> pool = new ArrayList<BinaryQuestion>();
        switch (_phase) {
            // advance to questions
            case INITIALIZED:
                _phase = Phase.QUESTIONS_MUTEX;

            // get all mutex questions, if we're out, advance to next phase
            case QUESTIONS_MUTEX:
                pool = filterQuestions();

                if (pool.size() > 0)
                    break;

                _phase = Phase.QUESTIONS_NORMAL;

            // get all non-mutex questions, if out, we're done
            case QUESTIONS_NORMAL:
                pool = filterQuestions();

                if (pool.size() > 0)
                    break;

                _phase = Phase.FINISHED;
            case FINISHED:

                break;
        }

        if (pool.size() == 0)
            return null;

        // pick a random next question
        int r = _rndGen.nextInt(pool.size());
        _curQuestion = pool.get(r);

        // mix order?
        if (_rndGen.nextBoolean())
            _curQuestion.swapResponses();

        return _curQuestion;
    }

    /** add another question */
    public void addQuestion(BinaryQuestion q) {
        _questions.add(q);
    }

    /** @return true if we can finish the quiz */
    @Override public boolean isQuizComplete() {
        return true;
    }

    /** @return the current quiz phase */
    public Phase getPhase() {
        return _phase;
    }

    /** @return a Type if we're done or null if not */
    @Override public Type getResult() {
        return getMostLikelyType();
    }

    /** @return the current question we're on, or getNextQuestion if we haven't
     * started the quiz yet */
    @Override public Question getCurrentQuestion() {
        if (_curQuestion == null)
            return getNextQuestion();

        return _curQuestion;
    }

    /** score the various functions and sort */
    public List<Function> scoreFunctions() {
        List<Function> functions = new ArrayList<Function>();

        // get the map of function code -> score and make a list
        HashMap<String, Integer> scores = getScoringInfo();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            Function f = new Function(entry.getKey());
            f.setScore(entry.getValue());
            functions.add(f);
        }

        // sort high-to-low and return
        Collections.sort(functions);
        Collections.reverse(functions);
        return functions;
    }

    /** @return The most likely Type thus far */
    public Type getMostLikelyType() {
        int[] scores = scoreTypes();
        int biggest = 0;


        for (int n = 0; n < 16; n++) {
            if (scores[n] > scores[biggest])
                biggest = n;
        }

        return new Type(biggest);
    }

    /** score the types thus far */
    public int[] scoreTypes() {
        int[] ret = new int[16];

        HashMap<String, Integer> scores = getScoringInfo();
        for (int n = 0; n < 16; n++) {
            Type t = new Type(n);

            int i = 0;
            int score = 0;
            for (Function fn : t.getCognativeFunctions()) {
                String f = fn.toString();
                switch(i++) {
                    case 0:
                        if (scores.containsKey(f))
                            score += scores.get(f) * 20;
                        break;
                    case 1:
                        if (scores.containsKey(f))
                            score += scores.get(f) * 12;
                        break;
                    case 2:
                        if (scores.containsKey(f))
                            score += scores.get(f) * 7;
                        break;
                    case 3:
                        if (scores.containsKey(f))
                            score += scores.get(f) * 5;
                        break;
                }
            }

            ret[n] = score;
        }

        return ret;
    }

    /** @return map of code-> score */
    @Override public HashMap<String, Integer> getScoringInfo() {
        /*
         * Loop over all answered questions, building a preference
         * based on what cognitive functions or attitudes are prevalent
         */
        HashMap<String, Integer> scoreMap = new HashMap<String, Integer>();
        for (BinaryQuestion q : _answeredQuestions) {
            String  r   = q.getResponseCode();
            String  o   = q.getOtherCode();

            /* increment the maps, and decrement the other choice if mutually
             * exclusive options */
            scoreMap.put(r, scoreMap.containsKey(r) ? scoreMap.get(r)+1 : 1);
            if (q.isMutuallyExclusive()) {
                scoreMap.put(o,
                        scoreMap.containsKey(o) ? scoreMap.get(o)-1 : -1);
            }
        }

        return scoreMap;
    }

    /** @return a list of functions that we should keep asking about */
    public List<String> getViableFunctions() {
        List<String> ret = new ArrayList<String>();

        for (Function f : scoreFunctions()) {
            if(f.getScore() >= FUNCTION_DROP_THRESHOLD) {
                ret.add(f.toString());
            }
        }

        return ret;
    }

    /** @return a filtered List of Questions based on certain criteria */
    private List<BinaryQuestion> filterQuestions() {

        /* brute-force iterate over all the questions and test each to see if
         * it matches our give conditions */
        List<BinaryQuestion> ret = new ArrayList<BinaryQuestion>();
        List<String> funcs = getViableFunctions();
        for (BinaryQuestion q : _questions) {

            // if we're on the mutex phase and its a mutex question
            if (q.isMutuallyExclusive() && _phase == Phase.QUESTIONS_MUTEX) {
                ret.add(q);

            // if we're on normal and both function codes are viable
            } else if (!q.isMutuallyExclusive() &&
                    _phase == Phase.QUESTIONS_NORMAL &&
                    funcs.contains(q._scoreCodeA) &&
                    funcs.contains(q._scoreCodeB)) {
                ret.add(q);
            }
        }

        return ret;
    }

    /** @return total questions answered thus far */
    @Override public int getAnsweredCount() {
        return _answeredQuestions.size();
    }

    /** @return total potential questions remaining */
    @Override public int getRemainingCount() {
        return _questions.size();
    }

    /** @return total questions offered (answered + skipped) */
    @Override public int getOfferedCount() {
        return _answeredQuestions.size();
    }
}
