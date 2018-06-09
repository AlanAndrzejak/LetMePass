package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

public final class Question {
    private final String question;
    private final int answer;
    private final String optionA;
    private final String optionB;
    private final String optionC;
    static int score;
    private static int max;


    static ArrayList<Question> actualQuestions = new ArrayList<Question>();

    public Question(int answer, String question, String optionA, String optionB, String optionC) {
        this.answer = answer;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        actualQuestions.add(this);
    }

    public static void setDefualtActualQuestions() {
        new Question(1, " 6:2(1+2)?", "5", "1", "9");
        new Question(-1, " 6+10*(14−4)", "106", "160", "0");
        new Question(-1, " 21−7+22−15", "21", "22", "30");
        new Question(-1, " 4*4−28÷7", "12", "-41", "0");
        new Question(-1, " 7+3*(9−3)", "25", "60", "22");
        new Question(0, " 13−3⋅0", "10", "13", "0");
        new Question(0, " 6⋅8+12÷4", "30", "51", "42");
        new Question(-1, " (10−8)⋅6+3", "15", "18", "16");
        new Question(1, " 5+(20−19)⋅7", "13", "42", "12");
        new Question(0, " (a+b)^2", "a^2+b^2", "a^2+b^2+2ab", "(a+b)*2");
        new Question(0, " 0*0/0", "0", "Inf", "1");
        new Question(-1, " Ocena za projekt", "5", "4", "3");
    }

    public ArrayList<Question> getActualQuestions() {
        return actualQuestions;
    }

    public boolean isCorrectAnswer(int userAnswer) {
        if (this.answer == userAnswer) {
            {
                increaseScore();
                return true;
            }
        } else return false;
    }

    private static void increaseScore() {
        ++score;
    }


    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    private static void randomGenerator() {
        Random rand = new Random();
        max = rand.nextInt(actualQuestions.size());
    }


    public void removeElements(ArrayList<Question> input, Question deleteMe) {
        input.remove(deleteMe);
        randomGenerator();
    }
}