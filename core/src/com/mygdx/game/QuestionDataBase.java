package com.mygdx.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dzerry on 04-Jun-18.
 */

public class QuestionDataBase {
    String actualQuestion;
  static  String[] answers={"A","B","C"};
  static  String[] questions;
    private static final Map<String, String> correctAnswers = createMap();
    private static Map<String, String> createMap()
    {
        Map<String,String> myMap = new HashMap<String,String>();
        myMap.put(questions[0],answers[0] );
        myMap.put(questions[1], answers[1]);
        return myMap;
    }
    public boolean isCorrectAnswer( ){
            return true;
    }
}
