
package com.mycompany.triviasection;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;


public class PreviousQuestion {
    
        // method for display previous question
        public void DisplayAnsweredQuestion() {
            
            ConnectDatabase database = new ConnectDatabase();
            
            if(database.getQuestionAnswered() == null) {
                System.out.println("There is no answered question");
                return;
            }
            
            String[] questionAnsweredStr = database.getQuestionAnswered().split(",");
        
            // convert the string array to int array
            int[] questionAnsweredInt = new int[questionAnsweredStr.length];
        
            for(int i = 0; i < questionAnsweredStr.length; i++) {
                questionAnsweredInt[i] = Integer.parseInt(questionAnsweredStr[i]);
            }
            
            // create an object of Trivia Section
            TriviaSection tr = new TriviaSection();
            
            for(int i = 0; i < questionAnsweredInt.length; i++) {
                tr.displayTrivia(questionAnsweredInt[i]);
            }
            
        }
        
        // method for answer previous question
        public void AnswerPreviousQuestion() {
        
        ConnectDatabase database = new ConnectDatabase();
        
        // make the array global
        String[] questionAnsweredStr;
        int[] questionAnsweredInt = new int[0];
        
        // check for case 1
        if(database.getQuestionAnswered() != null) {
            // get the question number 
            questionAnsweredStr = database.getQuestionAnswered().split(",");
        
            // convert the string array to int array
            questionAnsweredInt = new int[questionAnsweredStr.length];
        
            for(int i = 0; i < questionAnsweredStr.length; i++) {
                questionAnsweredInt[i] = Integer.parseInt(questionAnsweredStr[i]);
            }
            
            // sort the array
            Arrays.sort(questionAnsweredInt);
        }

        // ------------ unanswer question part -------------
        
        // create an object of Trivia Section
        TriviaSection tr = new TriviaSection();
        
        // getting the date of registration and current date
        LocalDate registrationDate = database.getResgistrationDate();
        LocalDate currentDate = LocalDate.now();
        
        // compute the different in days
        int currentQuestionIndex = (int) ChronoUnit.DAYS.between(registrationDate, currentDate);
        
        boolean hasUnansweredQuestion = false;
        
        // check for unanswer question
        // case 1 : if user didn't answer any question previously
        if(database.getQuestionAnswered() == null) {
            for(int i = 0; i < currentQuestionIndex;i++) {
                tr.playTrivia(i);
            }
        }
        // case 2 : if user already answer today's question
        else if(questionAnsweredInt[questionAnsweredInt.length-1] == (currentQuestionIndex)) {
           
            int[] answeredQuestion = new int[currentQuestionIndex+1];
 
            for(int i = 0; i < questionAnsweredInt.length; i++) {
                int questionNumber = questionAnsweredInt[i];
                answeredQuestion[questionNumber]++;
            }
            
            // display the unanswer question
            for(int i = 0; i < answeredQuestion.length; i++) {
                if(answeredQuestion[i] == 0) {
                    tr.playTrivia(i);
                    hasUnansweredQuestion = true;
                }
            }
            
            // handle the case when there are no unanswered questions
            if (!hasUnansweredQuestion) {
            System.out.println("All questions are answered.");
            }
            
        } 
        
        // case 3 : if user did not answer today's question yet
        else {
            int[] answeredQuestion = new int[currentQuestionIndex];
 
            for(int i = 0; i < questionAnsweredInt.length; i++) {
                int questionNumber = questionAnsweredInt[i];
                answeredQuestion[questionNumber]++;
            }
            
            // display the unanswer question
            for(int i = 0; i < answeredQuestion.length; i++) {
                if(answeredQuestion[i] == 0) {
                    tr.playTrivia(i);
                    hasUnansweredQuestion = true;
                }
            }
            
            // handle the case when there are no unanswered questions
            if (!hasUnansweredQuestion) {
            System.out.println("All questions are answered.");
            }
        }
    }
}
