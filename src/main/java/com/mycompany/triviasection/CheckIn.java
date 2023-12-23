
package com.mycompany.triviasection;

import java.time.LocalDate;

public class CheckIn {
    
    public void checkIn() {
        // connect to database 
        ConnectDatabase database = new ConnectDatabase();
        
        // get the last check in date
        LocalDate lastCheckInDate = database.getLastCheckInDate();
        LocalDate currentDate = LocalDate.now();
        
        int points = database.getCurrentPoint();
        
        // check the conditions
        // case 1 : if the user just register his/her account and did not checkin yet
        if(lastCheckInDate == null) {
            points++;
            // update the point in database
            database.updateCurrentPoint(points);
            // update the check in date in database
            database.updateCheckInDate(currentDate);
            // feedback
            System.out.println("You have successfully checked in today !");
            System.out.println("You receive 1 point !");
        }
        // case 2 : if the user had already check in today
        else if (lastCheckInDate.equals(currentDate)) {
            System.out.println("You have already check in today.");
        } 
        // case 3 : if the user had check in previously and did not checkin today yet
        else {
            points++;
            // update the point in database
            database.updateCurrentPoint(points);
            // update the check in date in database
            database.updateCheckInDate(currentDate);
            // feedback
            System.out.println("You have successfully checked in today !");
            System.out.println("You receive 1 point !");
        }
        
    }
        
}
