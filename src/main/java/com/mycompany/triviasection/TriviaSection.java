

package com.mycompany.triviasection;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TriviaSection {
    
    public void playTrivia() {
        
        // -------- connecting to database --------
        ConnectDatabase database = new ConnectDatabase();
        
        
        // -------- reading the text file --------
        int numberOfQuestions = 10;

        // create an array for storing question
        String[] questions = new String[numberOfQuestions];

        // create an 2D array for storing choices
        String[][] options = new String[numberOfQuestions][];

        // create an anrray for storing correct answer's index
        String[] answers = new String[numberOfQuestions];

        File triviaSample = new File("/Users/zhengwei/NetBeansProjects/TriviaSection/src/main/java/com/mycompany/triviasection/TriviaSample.txt");
        
        try {
            
            Scanner sc = new Scanner(triviaSample);


            while (sc.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {
                    // store the question into the array
                    questions[i] = sc.nextLine();

                    // skip 3 line
                    for(int j = 0; j < 3 && sc.hasNextLine(); j++) {
                        sc.nextLine();
                    }
                }
            }
            sc.close();

            Scanner sc2 = new Scanner(triviaSample);

            while (sc2.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    // skip for the question line
                    sc2.nextLine();

                    // store the choices into a string
                    // eg. 4%,10%,25%,50%
                    String choice = sc2.nextLine();

                    // store the choices into the array
                    // eg. {"4%", "10%", "25%", "50%"}
                    String temporaryArr[] = choice.split(",");

                    // store the choices into 2D array
                    // eg. {
                    //      {"4%", "10%", "25%", "50%"},
                    //      {"500 million", "900 million", "1.6 billion", "5 billion"}
                    //      ...
                    //     }
                    options[i] = temporaryArr;

                    // skip 2 line
                    for(int z = 0; z < 2 && sc2.hasNextLine(); z++) {
                        sc2.nextLine();
                    }
                }
            }

            sc2.close();

            Scanner sc3 = new Scanner(triviaSample);

            while (sc3.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    // skip 2 line
                    for (int j = 0; j < 2; j++) {
                        sc3.nextLine();
                    }

                    // store the answer
                    answers[i] = sc3.nextLine();
                
                    // skip 1 line
                    if(sc3.hasNextLine()) {
                        sc3.nextLine();
                    }
                }
            }

            sc3.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // -------- trivia section --------
        
        // getting the date of registration and current date
        LocalDate registrationDate = database.getResgistrationDate();
        LocalDate currentDate = LocalDate.now();
        
        // create an object of scanner class
        Scanner sc = new Scanner(System.in);
        
        // compute the different in days
        int currentQuestionIndex = (int) ChronoUnit.DAYS.between(registrationDate, currentDate);
        int i = currentQuestionIndex;
        
        // handle the case where the numbers of days is exceed 10 days
        if(i > 10) {
            System.out.println("There are no trivia for you anymore ~ ");
            return;
        }
        
        
        // check for whether this question already being answered 
        if(alreadyAnswer(currentQuestionIndex)) {
            System.out.println("You have done today's question!");
            return;
        }
        
        // getting the question
        String question = questions[i];
        String [] answerOptions = options[i];
        String correctAnswer = answers[i];
        
        // storing the answer
        String A,B,C,D;
        A = answerOptions[0];
        B = answerOptions[1];
        C = answerOptions[2];
        D = answerOptions[3];
        
        // define all the necessary variable
        int attempts = 1;
        int points = database.getCurrentPoint();
        
        System.out.println("\nDay " +  (i+1)  + " Trivia (Attempt #" + attempts + ")");
        System.out.println(question);
        displayAnswerOptions(A,B,C,D);
        System.out.println("=======================================================");
        System.out.print("Enter your Answer (A/B/C/D): ");
        String userAnswer = sc.next();
        System.out.println("=======================================================");
        // check for the correct answer
        if (userAnswer.equals("A") && A.equals(correctAnswer) ||
            userAnswer.equals("B") && B.equals(correctAnswer) ||
            userAnswer.equals("C") && C.equals(correctAnswer) ||
            userAnswer.equals("D") && D.equals(correctAnswer)) {
                points += 2;
                System.out.println("Congratulations! You answered it correctly. \nYou have been awarded 2 point, you now have " + points + " points.");
                 
        } else {
                System.out.println("Whoops, that doesn’t look right, try again!");
                attempts++;
                // shuffle the question
                shuffleAnswerOptions(answerOptions);
                A = answerOptions[0];
                B = answerOptions[1];
                C = answerOptions[2];
                D = answerOptions[3];
                System.out.println("\nDay " +  (i+1)  + " Trivia (Attempt #" + attempts + ")");
                System.out.println(question);
                displayAnswerOptions(A,B,C,D);
                System.out.println("=======================================================");
                System.out.print("Enter your Answer (A/B/C/D): ");
                userAnswer = sc.next();
                System.out.println("=======================================================");
                
                // check for the correct answer
                if (userAnswer.equals("A") && A.equals(correctAnswer) ||
                    userAnswer.equals("B") && B.equals(correctAnswer) ||
                    userAnswer.equals("C") && C.equals(correctAnswer) ||
                    userAnswer.equals("D") && D.equals(correctAnswer)) {
                    points += 1;
                    System.out.println("Congratulations! You answered it correctly. \nYou have been awarded 1 point, you now have " + points + " points.");
                } else {
                    System.out.println("Your answer is still incorrect, the correct answer is: \n" +  correctAnswer);
                    System.out.println("Total marks achieved so far: " + points);
                }
        }
        // update the point in database
        database.updateCurrentPoint(points);
        
        // update the answered question in database
        if(database.getQuestionAnswered() != null) {
            String newAnsweredQuestion = database.getQuestionAnswered() + "," + Integer.toString(currentQuestionIndex);
            database.updateQuestionAnswered(newAnsweredQuestion);
        } else {
            String newAnsweredQuestion = Integer.toString(currentQuestionIndex);
            database.updateQuestionAnswered(newAnsweredQuestion);
        }
        
    }
    
    // play trivia with one parameter
    public void playTrivia(int questionNumber) {
                
        // -------- connecting to database --------
        ConnectDatabase database = new ConnectDatabase();
        
        
        // -------- reading the text file --------
        int numberOfQuestions = 10;

        String[] questions = new String[numberOfQuestions];

        String[][] options = new String[numberOfQuestions][];

        String[] answers = new String[numberOfQuestions];

        File triviaSample = new File("/Users/zhengwei/NetBeansProjects/TriviaSection/src/main/java/com/mycompany/triviasection/TriviaSample.txt");
        
        try {
            
            Scanner sc = new Scanner(triviaSample);


            while (sc.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    questions[i] = sc.nextLine();

                    for(int j = 0; j < 3 && sc.hasNextLine(); j++) {
                        sc.nextLine();
                    }
                }
            }
            sc.close();

            Scanner sc2 = new Scanner(triviaSample);

            while (sc2.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    sc2.nextLine();

                    String choice = sc2.nextLine();

                    String temporaryArr[] = choice.split(",");

                    options[i] = temporaryArr;

                    for(int z = 0; z < 2 && sc2.hasNextLine(); z++) {
                        sc2.nextLine();
                    }
                }
            }

            sc2.close();

            Scanner sc3 = new Scanner(triviaSample);

            while (sc3.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    for (int j = 0; j < 2; j++) {
                        sc3.nextLine();
                    }

                    answers[i] = sc3.nextLine();

                    if(sc3.hasNextLine()) {
                        sc3.nextLine();
                    }
                }
            }

            sc3.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Scanner sc = new Scanner(System.in);
        
        // getting the question
        String question = questions[questionNumber];
        String [] answerOptions = options[questionNumber];
        String correctAnswer = answers[questionNumber];
        
        // storing the answer
        String A,B,C,D;
        A = answerOptions[0];
        B = answerOptions[1];
        C = answerOptions[2];
        D = answerOptions[3];
        
        // define all the necessary variable
        int attempts = 1;
        int points = database.getCurrentPoint();
        
        System.out.println("\nDay " +  (questionNumber+1)  + " Trivia (Attempt #" + attempts + ")");
        System.out.println(question);
        displayAnswerOptions(A,B,C,D);
        System.out.println("=======================================================");
        System.out.print("Enter your Answer (A/B/C/D): ");
        String userAnswer = sc.next();
        System.out.println("=======================================================");
        // check for the correct answer
        if (userAnswer.equals("A") && A.equals(correctAnswer) ||
            userAnswer.equals("B") && B.equals(correctAnswer) ||
            userAnswer.equals("C") && C.equals(correctAnswer) ||
            userAnswer.equals("D") && D.equals(correctAnswer)) {
                points += 2;
                System.out.println("Congratulations! You answered it correctly. \nYou have been awarded 2 point, you now have " + points + " points.");
                 
        } else {
                System.out.println("Whoops, that doesn’t look right, try again!");
                attempts++;
                // shuffle the question
                shuffleAnswerOptions(answerOptions);
                A = answerOptions[0];
                B = answerOptions[1];
                C = answerOptions[2];
                D = answerOptions[3];
                System.out.println("\nDay " +  (questionNumber+1)  + " Trivia (Attempt #" + attempts + ")");
                System.out.println(question);
                displayAnswerOptions(A,B,C,D);
                System.out.println("=======================================================");
                System.out.print("Enter your Answer (A/B/C/D): ");
                userAnswer = sc.next();
                System.out.println("=======================================================");
                
                // check for the correct answer
                if (userAnswer.equals("A") && A.equals(correctAnswer) ||
                    userAnswer.equals("B") && B.equals(correctAnswer) ||
                    userAnswer.equals("C") && C.equals(correctAnswer) ||
                    userAnswer.equals("D") && D.equals(correctAnswer)) {
                    points += 1;
                    System.out.println("Congratulations! You answered it correctly. \nYou have been awarded 1 point, you now have " + points + " points.");
                } else {
                    System.out.println("Your answer is still incorrect, the correct answer is: \n" +  correctAnswer);
                    System.out.println("Total marks achieved so far: " + points);
                }
        }
        // update the point in database
        database.updateCurrentPoint(points);
        
        // update the answered question in database
        if(database.getQuestionAnswered() != null) {
            String newAnsweredQuestion = database.getQuestionAnswered() + "," + Integer.toString(questionNumber);
            database.updateQuestionAnswered(newAnsweredQuestion);
        } else {
            String newAnsweredQuestion = Integer.toString(questionNumber);
            database.updateQuestionAnswered(newAnsweredQuestion);
        }
        
    }
    
    public void playTriviaWithoutAffectingPoints() {
        
        // -------- connecting to database --------
        ConnectDatabase database = new ConnectDatabase();
        
        
        // -------- reading the text file --------
        int numberOfQuestions = 10;

        // create an array for storing question
        String[] questions = new String[numberOfQuestions];

        // create an 2D array for storing choices
        String[][] options = new String[numberOfQuestions][];

        // create an anrray for storing correct answer's index
        String[] answers = new String[numberOfQuestions];

        File triviaSample = new File("/Users/zhengwei/NetBeansProjects/TriviaSection/src/main/java/com/mycompany/triviasection/TriviaSample.txt");
        
        try {
            
            Scanner sc = new Scanner(triviaSample);


            while (sc.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    questions[i] = sc.nextLine();

                    for(int j = 0; j < 3 && sc.hasNextLine(); j++) {
                        sc.nextLine();
                    }
                }
            }
            sc.close();

            Scanner sc2 = new Scanner(triviaSample);

            while (sc2.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    sc2.nextLine();

                    String choice = sc2.nextLine();

                    String temporaryArr[] = choice.split(",");

                    options[i] = temporaryArr;

                    for(int z = 0; z < 2 && sc2.hasNextLine(); z++) {
                        sc2.nextLine();
                    }
                }
            }

            sc2.close();

            Scanner sc3 = new Scanner(triviaSample);

            while (sc3.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    for (int j = 0; j < 2; j++) {
                        sc3.nextLine();
                    }

                    answers[i] = sc3.nextLine();

                    if(sc3.hasNextLine()) {
                        sc3.nextLine();
                    }
                }
            }

            sc3.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // -------- trivia section --------
        
        // getting the date of registration and current date
        LocalDate registrationDate = database.getResgistrationDate();
        LocalDate currentDate = LocalDate.now();
        
        // create an object of scanner class
        Scanner sc = new Scanner(System.in);
        
        // compute the different in days
        int currentQuestionIndex = (int) ChronoUnit.DAYS.between(registrationDate, currentDate);
        int i = currentQuestionIndex;
        
        // handle the case where the numbers of days is exceed 10 days
        if(i > 10) {
            System.out.println("There are no trivia for you anymore ~ ");
            return;
        }
        
        // getting the question
        String question = questions[i];
        String [] answerOptions = options[i];
        String correctAnswer = answers[i];
        
        // storing the answer
        String A,B,C,D;
        A = answerOptions[0];
        B = answerOptions[1];
        C = answerOptions[2];
        D = answerOptions[3];
        
        // define all the necessary variable
        int attempts = 1;
        int points = database.getCurrentPoint();
        
        System.out.println("\nDay " +  (i+1)  + " Trivia (Attempt #" + attempts + ")");
        System.out.println(question);
        displayAnswerOptions(A,B,C,D);
        System.out.println("=======================================================");
        System.out.print("Enter your Answer (A/B/C/D): ");
        String userAnswer = sc.next();
        System.out.println("=======================================================");
        // check for the correct answer
        if (userAnswer.equals("A") && A.equals(correctAnswer) ||
            userAnswer.equals("B") && B.equals(correctAnswer) ||
            userAnswer.equals("C") && C.equals(correctAnswer) ||
            userAnswer.equals("D") && D.equals(correctAnswer)) {
                points += 2;
                System.out.println("Congratulations! You answered it correctly. \n");
                 
        } else {
                System.out.println("Whoops, that doesn’t look right, try again!");
                attempts++;
                // shuffle the question
                shuffleAnswerOptions(answerOptions);
                A = answerOptions[0];
                B = answerOptions[1];
                C = answerOptions[2];
                D = answerOptions[3];
                System.out.println("\nDay " +  (i+1)  + " Trivia (Attempt #" + attempts + ")");
                System.out.println(question);
                displayAnswerOptions(A,B,C,D);
                System.out.println("=======================================================");
                System.out.print("Enter your Answer (A/B/C/D): ");
                userAnswer = sc.next();
                System.out.println("=======================================================");
                
                // check for the correct answer
                if (userAnswer.equals("A") && A.equals(correctAnswer) ||
                    userAnswer.equals("B") && B.equals(correctAnswer) ||
                    userAnswer.equals("C") && C.equals(correctAnswer) ||
                    userAnswer.equals("D") && D.equals(correctAnswer)) {
                    points += 1;
                    System.out.println("Congratulations! You answered it correctly.");
                } else {
                    System.out.println("Your answer is still incorrect, the correct answer is: \n" +  correctAnswer);
                }
        }
    }
    
    // method for displaying trivia question 
    public void displayTrivia(int questionNum) {
        
        // -------- reading the text file --------
        int numberOfQuestions = 10;

        String[] questions = new String[numberOfQuestions];

        String[][] options = new String[numberOfQuestions][];

        String[] answers = new String[numberOfQuestions];

        File triviaSample = new File("/Users/zhengwei/NetBeansProjects/TriviaSection/src/main/java/com/mycompany/triviasection/TriviaSample.txt");
        
        try {
            
            Scanner sc = new Scanner(triviaSample);


            while (sc.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    questions[i] = sc.nextLine();

                    for(int j = 0; j < 3 && sc.hasNextLine(); j++) {
                        sc.nextLine();
                    }
                }
            }
            sc.close();

            Scanner sc2 = new Scanner(triviaSample);

            while (sc2.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    sc2.nextLine();

                    String choice = sc2.nextLine();

                    String temporaryArr[] = choice.split(",");

                    options[i] = temporaryArr;

                    for(int z = 0; z < 2 && sc2.hasNextLine(); z++) {
                        sc2.nextLine();
                    }
                }
            }

            sc2.close();

            Scanner sc3 = new Scanner(triviaSample);

            while (sc3.hasNextLine()) {
                for(int i = 0; i < numberOfQuestions; i++) {

                    for (int j = 0; j < 2; j++) {
                        sc3.nextLine();
                    }

                    answers[i] = sc3.nextLine();

                    if(sc3.hasNextLine()) {
                        sc3.nextLine();
                    }
                }
            }

            sc3.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // getting the question
        String question = questions[questionNum];
        String [] answerOptions = options[questionNum];
        String correctAnswer = answers[questionNum];
        
        // storing the answer
        String A,B,C,D;
        A = answerOptions[0];
        B = answerOptions[1];
        C = answerOptions[2];
        D = answerOptions[3];
        
        System.out.println("\nDay " +  (questionNum+1)  + " Trivia");
        System.out.println(question);
        displayAnswerOptions(A,B,C,D);
        System.out.println("=======================================================");
        System.out.println("The correct answer is " + correctAnswer);
        System.out.println("=======================================================");
    }
    
    // method check for whether a question already being answered 
    public boolean alreadyAnswer(int questionNum) {
        // create an object of database class
        ConnectDatabase database = new ConnectDatabase();
        
        String questionAnsweredStr = database.getQuestionAnswered();
        
        if(questionAnsweredStr == null) {
            return false;
        }
        
        // get the question number 
        String[] questionAnsweredStrArr = questionAnsweredStr.split(",");
        
        String questionNumStr = Integer.toString(questionNum);
        
        for(int i = 0; i < questionAnsweredStrArr.length; i++) {
            if(questionNumStr.equals(questionAnsweredStrArr[i])) {
                return true;
            } 
        }
        
        return false;
    }

    // method for display the answer options
    public void displayAnswerOptions(String A,String B,String C,String D) {
        System.out.println("[A] " + A);
        System.out.println("[B] " + B);
        System.out.println("[C] " + C);
        System.out.println("[D] " + D);
    }
    
    // method for shuffle answer options
    private void shuffleAnswerOptions(String[] answerOptions) {
        List<String> optionsList = Arrays.asList(answerOptions);
        Collections.shuffle(optionsList);
        optionsList.toArray(answerOptions);
    }
   
}
