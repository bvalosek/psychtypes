//-----------------------------------------------------------------------------
//
// (C) Brandon Valosek, 2012 <bvalosek@gmail.com>
//
//-----------------------------------------------------------------------------

package com.bvalosek.psychtypes;
import java.util.List;

/**
 *
 * Application for testing out the psychtypes stuff
 *
 */
public class TestApp {

    /** app entry point */
    public static void main(String[] args) {

        // try Type
        Type type = new Type("entp");

        for (int n=0; n < 16; n++) {
            Type t = new Type(n);
            List<Function> functions = t.getCognativeFunctions();
            String s = t + " has functions ";
            for (Function f : functions) {
                s += f + ", ";
            }
            //System.out.println (s);

        }

        BinaryResponseQuiz quiz = new BinaryResponseQuiz();
        BinaryResponseQuiz.BinaryQuestion q = quiz.new BinaryQuestion(
                "Would you rather find yourself",
                "getting sodomized by a buffalo",
                "shitting your brains out on your birthday",
                "Se", "Ne");
        quiz.addQuestion(q);


    }

}
