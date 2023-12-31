
package com.mycompany.triviasection;

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        
        // create an object of Scanner class
        Scanner sc = new Scanner(System.in);
        
        // create an object of trivia section class
        TriviaSection trivia = new TriviaSection();

        // create an object of previous question class
        PreviousQuestion pq = new PreviousQuestion();
        
        // create an object of checkin class
        CheckIn check = new CheckIn();
        
        // create an object of donations class
        Donation donate = new Donation();
        
        // --------------- start the program ------------------- // 
        
        // if the user click on trivia part
        System.out.print("Do you want to answer the question? (Y/N) : ");
        String userRespond = sc.next();
        
        if(userRespond.equals("Y")) {
            trivia.playTrivia();   
            
            // ask user if he/she wants to retry the question without a ffecting the points obtained
            System.out.print("Do you want to retry the question? (Y/N) : ");
            userRespond = sc.next();
            if(userRespond.equals("Y")) {
                trivia.playTriviaWithoutAffectingPoints();
            } else {
                System.out.println("Quit");
            }
        }
        else {
            System.out.println("Quit");
        }
        
        // if user click on answer previous question
        System.out.print("Do you want to answer the previous question that you haven't done? (Y/N) : ");
        userRespond = sc.next();
        if(userRespond.equals("Y")) {
            pq.AnswerPreviousQuestion();
        } else {
            System.out.println("quit");
        }
        
        // if user click on see previous question
        System.out.print("Do you want to see the previous question that you have done? (Y/N) : ");
        userRespond = sc.next();
        if(userRespond.equals("Y")) {
            pq.DisplayAnsweredQuestion();
        } else {
            System.out.println("quit");
        }
        
        // if user click on check in
        System.out.print("Do you want to check in today? (Y/N) : ");
        userRespond = sc.next();
        if(userRespond.equals("Y")) {
            check.checkIn();
        } else {
            System.out.println("quit");
        }
        
        // if user click on donation
        System.out.print("Do you want to make a donation? (Y/N) : ");
        userRespond = sc.next();
        if(userRespond.equals("Y")) {
            donate.donate();
        } else {
            System.out.println("quit");
        }
    }
}
