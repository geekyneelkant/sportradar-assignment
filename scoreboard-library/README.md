# Live Football World Cup Score Board Library Application

## Project Description
- This is a simple library that covers following list of tasks.
  - Start a match
  - Update the scores of live matches
  - Finish the match from the score board
  - Get a summary of scores of the live matches
   

## Features
 - Start a New Match
 - Finish the Live Match
 - Update Scores of Live Matches
 - Get Summary ScoreBoard of Live Matches


### Prerequisites
- Java Development Kit (JDK) 13 or higher
- Apache Maven

### Assumptions

 Collection Used:
 
	* Total number of Matches in the Football World Cup are very limited i.e. approximately 64 
	* Chose LinkedList because there would be even less live matches compared to total matches.
	* Retrieving the element would not be that costly.
	* Deletion and Adding operations will be more flexible
	* Helps with the summary sorting based on the recent matches - LinkedList preserves Insertion Order
    

 Start a match:
 
	*  Team Names would contain country name but not special characters because this is World Cup ScoreBoard Application
	*  Team Names wouldn't have super extra long names like exceeding the length of the string variable.
	
 Finish a match:
 
	*  Team Names would not have special characters because this is a World Cup ScoreBoard Application, Teams will be Country names
	*  Super extra long Team names like exceeding the length of the string variable because of the same reason above.
	
	

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/geekyneelkant/sportradar-assignment/scoreboard-libary.git