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
import java.util.LinkedHashMap;

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

    /** where we are in the quiz */
    private Phase _phase = Phase.INITIALIZED;

    /** how low a function score can go before getting dropped */
    public static final int FUNCTION_DROP_THRESHOLD = -2;

    /** Different quiz phases */
    public enum Phase {
        INITIALIZED, QUESTIONS_MUTEX, QUESTIONS_NORMAL, FINISHED;
    }

    private static final Random RANDOM = new Random();

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
                pool = getFilteredQuestions();

                if (pool.size() > 0)
                    break;

                _phase = Phase.QUESTIONS_NORMAL;

            // get all non-mutex questions, if out, we're done
            case QUESTIONS_NORMAL:
                pool = getFilteredQuestions();

                if (pool.size() > 0)
                    break;

                _phase = Phase.FINISHED;
            case FINISHED:

                break;
        }

        if (pool.size() == 0)
            return null;

        // pick a random next question
        _curQuestion = pool.get(RANDOM.nextInt(pool.size()));

        // mix order?
        if (RANDOM.nextBoolean())
            _curQuestion.swapResponses();

        return _curQuestion;
    }

    /** @return The most likely Type thus far */
    @Override public Type getResult() {
        // score the Types and return the top one
        Scorer<Type> scorer = new Scorer<Type>();
        scorer.add(getTypeScores());
        return scorer.getSortedList().get(0);
    }

    /** @return the current question we're on, or getNextQuestion if we haven't
     * started the quiz yet */
    @Override public Question getCurrentQuestion() {
        if (_curQuestion == null)
            return getNextQuestion();

        return _curQuestion;
    }

    /** @return A map of the function scores thus far */
    @Override public Map<Function, Integer> getFunctionScores() {
        /*
         * Loop over all answered questions, building a preference
         * based on what cognitive functions or attitudes are prevalent
         */
        HashMap<Function, Integer> scoreMap = new HashMap<Function, Integer>();
        for (BinaryQuestion q : _answeredQuestions) {
            Function  r   = new Function(q.getResponseCode());
            Function  o   = new Function(q.getOtherCode());

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

    /** @return A map from from a type to its current score */
    @Override public Map<Type, Integer> getTypeScores() {

        Map<Function, Integer> funcs = getFunctionScores();

        /** loop over all the types and calculate the score based on the
         * weighted sum of the 4 cognitive functions */
        Map<Type, Integer> scores = new HashMap<Type, Integer>();
        for (Type t : Type.allTypes()) {
            int i = 0;
            int score = 0;

            // add to score for each function
            for (Function f : t.getCognativeFunctions()) {
                switch (i++) {
                    case 0:
                        score += funcs.containsKey(f) ? funcs.get(f) * 20 : 0;
                        break;
                    case 1:
                        score += funcs.containsKey(f) ? funcs.get(f) * 15 : 0;
                        break;
                    case 2:
                        score += funcs.containsKey(f) ? funcs.get(f) * 12 : 0;
                        break;
                    case 3:
                        score += funcs.containsKey(f) ? funcs.get(f) * 8 : 0;
                        break;

                }
            }

            // add to big score map
            scores.put(t, score);
        }

        return scores;
    }

    /** @return total questions answered thus far */
    @Override public int getAnsweredCount() {
        return _answeredQuestions.size();
    }

    /** @return total potential questions remaining */
    @Override public int getRemainingCount() {
        return _questions.size();
    }

    /** @return the score of chosing fA over fB, given the quiz thus far */
    public int getResponseScore(Function fA, Function fB) {
        int score = 0;

        /* Iterate over all types scored thus far, if fA occurs before fB, then
         * add the score of the type. If either fA or fB don't appear, or they
         * occur in opposite order, then subtract */
        for (Map.Entry<Type, Integer> entry : getTypeScores().entrySet()) {
            Type t = entry.getKey();
            int typeScore = entry.getValue();

            int aLoc = 0;
            int bLoc = 0;
            int n = 0;
            for (Function f : t.getCognativeFunctions()) {
                n++;
                if (f.equals(fA))
                    aLoc = n;
                if (f.equals(fB))
                    bLoc = n;
            }

            if (aLoc != 0 && bLoc !=0) {
                if (aLoc < bLoc) {
                    score += typeScore;
                } else {
                    score -= typeScore;
                }
            }
        }

        return score;
    }

    /** @return the current phase */
    public Phase getPhase() {
        return _phase;
    }

    /** add another question */
    public void addQuestion(BinaryQuestion q) {
        _questions.add(q);
    }

    /** @return a list of functions that we should keep asking about */
    public List<Function> getViableFunctions() {
        Scorer<Function> scorer = new Scorer<Function>();

        // iterate over scored functions and add to the scorer
        for (Map.Entry<Function, Integer> entry :
                getFunctionScores().entrySet()) {
            scorer.add(entry.getKey(), entry.getValue());
        }

        // return a sorted list, drop everything that's below the thresh
        return scorer.getSortedList(FUNCTION_DROP_THRESHOLD);
    }

    /** @return A filtered List of Questions based on what phase the quiz is
     * currently in*/
    private List<BinaryQuestion> getFilteredQuestions() {

        // iterate over all questions and see if it we should add it
        List<BinaryQuestion> ret = new ArrayList<BinaryQuestion>();
        List<Function> funcs = getViableFunctions();
        for (BinaryQuestion q : _questions) {

            // if we're on the mutex phase and its a mutex question
            if (q.isMutuallyExclusive() && _phase == Phase.QUESTIONS_MUTEX) {
                ret.add(q);

            // if we're on normal and both function codes are viable
            } else if (!q.isMutuallyExclusive() &&
                    _phase == Phase.QUESTIONS_NORMAL &&
                    funcs.contains(new Function(q.getResponseCode())) &&
                    funcs.contains(new Function(q.getOtherCode()))) {
                ret.add(q);
            }
        }

        return ret;
    }
}
