
package com.mycompany.triviasection;

import java.util.Scanner;
import java.io.*;

public class Donation {
    
    public void donate() {
        
        ConnectDatabase database = new ConnectDatabase();
        
        Scanner sc = new Scanner(System.in);
        
        int points = database.getCurrentPoint();
       
        System.out.print("How much do you want to donate? : $");
        int donationAmount = sc.nextInt();
        
        // consume the newline character left in the buffer after reading the integer
        sc.nextLine();
        
        System.out.print("Which NGO do you want to donate to? : ");
        String NGOName = sc.nextLine();
        
        // convert the donation to points
        int pointsAwarded = donationAmount * 10;
        
        points += pointsAwarded;
        
        database.updateCurrentPoint(points);
        
        // calculate the amount of donation money goes to the mentioned NGO
        double amount = donationAmount * 0.9;
        
        // write to the donation.txt file
        try{
            
        PrintWriter wr = new PrintWriter(new FileOutputStream("/Users/zhengwei/NetBeansProjects/TriviaSection/src/main/java/com/mycompany/triviasection/Donations.txt",true));
        
        wr.printf("%s has donated $%.2f to %s\n", database.getUsername(), amount, NGOName);
        
        wr.close();
        sc.close();
        } catch (IOException e) {
            System.out.println("Fix this error now!!!!");
        }
    }
}
