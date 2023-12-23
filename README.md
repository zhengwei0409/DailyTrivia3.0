# DailyTrivia 3.0

This is the complete CLI daily trivia section  

There are 5 classes in this section  
- ConnectDatabase
- CheckIn
- TriviaSection
- PreviousQuestion
- Donation

There are 2 text file in this section
- TriviaSample.txt
- Donations.txt

*Execute the code in the main class to run the program*  

For database  
execute the sql below to create the same table that this program using  
  
create table UserAccount (  
	id int auto_increment primary key,  
    	email varchar(100),  
    	username varchar(50) unique,  
    	password varchar(255),  
    	current_point int default 0,  
    	registration_date date default (current_date),  
	question_answered varchar(50),  
	last_checkin_date date  
);  
  
** And remember to change the username to your database username as well as the password in ConnectDatabase class    
Arhhh this section ruined my Christmas Holiday !!!

