# FinalProject
For Android final project group activitives
CST2335 Graphical Interface Programming-draft2
Project Assignment         

Overview
This is a group project for groups of 4 members (all in the same lab section). If you would like to be assigned a group, send an email to your lab instructor.  If you have chosen partners yourself, then please email the names of the group to your lab instructor.  You must work in the group to which you are assigned.  Use your assigned group number WITH your names in labeling submissions.  Be sure to work through the Group Activity Worksheet together to exchange contact information amongst your group (Algonquin student email addresses at a minimum). You should also determine who would be working on what part of the project. All work must be the work of the group members and ONLY the group members: 
•	Each person will be responsible for their own part of the project and graded on this separately 
•	A portion of the grade will come from an evaluation of your participation as a group member
•	Final demonstrations must be done in your regular lab period that you are registered in. You will be given a demonstration time that you must attend for marks.
Purpose:
The Project is assigned to give you experience in: 
•	Developing software in a group environment. 
•	Dividing workload to meet deadlines. 
•	Designing modular software that allows for that division. 
•	Learning from the work of others 
Requirements:
Here is a list of the requirements for the final project:
1.	Each person’s project must have a ListView somewhere to present items. Selecting an item from the ListView must show detailed information about the item selected.
2.	Each activity must have at least 1 progress bar and at least 1 button.
3.	Each activity must have at least 1 edit text with appropriate text input method and at least 1 Toast, Snackbar, and custom dialog notification.
4.	The software must have 1 different activity written by each person in your group. The activity must be accessible by selecting a graphical icon from a Toolbar.
5.	Each Activity must use a fragment somewhere in its graphical interface.
6.	Each activity must have a help menu item that displays a dialog with the author’s name, Activity version number, and instructions for how to use the interface.
7.	There must be at least 1 other language supported by your Activity. If you are not bilingual, then you must support both British and American English (words like colour, color, neighbour, neighbor, etc). If you know a language other than English, then you can support that language in your application and don’t need to support American English.
8.	The items listed in the ListView must be stored by the application so that appear the next time the application is launched. The user must be able to add and delete items, which would then also be stored in a database.
9.	Each activity must use an AsyncTask to retrieve data from an http server.
10.	Each activity must use SharedPreferences to save something about the application for use the next time the application is launched.
11.	All activities must be integrated into a single working application, on a single device or emulator.
12.	The interfaces must look professional, with GUI elements properly laid out and aligned. 
13.	The functions and variables you write must be properly documented using JavaDoc comments.

Milestones:
Bonus marks will be awarded for displaying correct functionality by the following dates:
Milestone # and date	Requirements implemented #	Bonus Marks available
#1 – November 11 - 13	1, 2, 3, 11, 13	1
#2 – November 18 - 20	4, 6, 7, 9, 11, 13	1
#3 – November 25 - 27	5, 8, 10, 11, 12, 13




Beginning Steps
•	Create a new GitHub repository from one of the group members’ accounts. That group member must then invite the other group members to contribute. This is done by clicking on the “Settings” tab in Github, then click “Collaborators” on the left side menu, and search the group member names to add them to the project. Other team members should then clone that project to their computer and start making branches for their work. You will not be able to integrate your work if you do not start by first cloning the project!
•	As early as possible:
o	Decide who will work on which application.
o	Determine the additional tasks and decide who will take on each, for example: Technical Lead, Action Bar, Project Management and Communication Lead, Code Custodian, Documentation, Test Plan Integrator, Integration Tester, and any others you can determine
o	Discuss and document a code-freeze date for the group project, I recommend 72 hours before the actual due date so final code files can be merged into the project in preparation for upload to Blackboard.
•	Attempt to write your own code on your own branch and then merge that branch often (after each requirement is finished). Don’t try to merge the code only on the last week.
Grading Guide
•	Grading in 3 parts – Read each one carefully:
•	Group activity worksheet (5%)
o	By November 1st – Please fill in the Excel file “CST2335_FinalProject_Team_Activity_Workbook.xlxs” and submit on Brightspace, under Final Project. Everyone in the group should submit the file. 
•	Each student is graded on his or her application separately (85%) 
o	Week of November 11 - 13 – demonstrate the Milestone 1 requirements for bonus
o	Week of November 18 - 20 – demonstrate the Milestone 2 requirements for bonus
o	Week of November 25 - 27 – demonstrate the Milestone 3 requirements for bonus
o	Week of December 2 - 4 – Project demonstration during your scheduled lab sessions. Arrange a single submission of the group deliverable by one of the group members on behalf of the entire group. The professor will ask to see all of the required items. You must be in the lab in person. Code submitted on Brightspace will not be marked. 
•	Each member of the group must submit their final code as a record of what was finished at the end of the project. (10%)


The Applications
Each of the applications (as they are intended) requires similar programming techniques.  Each application takes information from the user, and stores it in a database.  Each application also provides functionality to summarize or analyze the whole body of data entered into the application.  Beyond that you are free to get creative.

Electric Car Charging Station finder
The user can search for electric car charging stations within a given area. The documentation is posted here: https://openchargemap.org/site/develop/api?ref=public-apis
•	The user can input a latitude & longitude location, and the server returns a list of charging stations near that location. For example, this URL returns locations near Algonquin College: https://api.openchargemap.io/v3/poi/?output=xml&countrycode=CA&latitude=45.347571&longitude=-75.756140&maxresults=10
•	The results should be loaded in a list of results. The user can then select an item from the list and see details about that charging station. You should display the stations:
o	Location Title
o	Latitude
o	Longitude
o	Contact Telephone number
o	A “load directions in google maps” button which loads the latitude and longitude in the google maps activity. Here are instructions how to do that: https://developers.google.com/maps/documentation/urls/android-intents

•	The user should be able to save a charging station into a list of favourites, saved in a database. This list should be accessible from a menu item. The user should be able to remove items from the list and database.
•	Your application should save the last item searched to display the next time the application is launched.
  

Recipe search engine
•	Create an interface that allows the user to enter a search term about recipes. There should be a “search” button that will search for all recipes that include that term. For example, this is a search for “chicken breast”:
•	https://www.food2fork.com/api/search?key=YOUR_API_KEY&q=chicken%20breast

•	Your application will call the web server to retrieve a list of recipes that match the term. Your application should create a list of titles that are retrieved in the results, and display those results in a list. Clicking on a title should display the title, image, and the URL of the recipe. Clicking on the URL should go to a web page that shows the recipe. 
•	The user should be able to save a recipe into a list of favourites, saved in a database. This list should be accessible from a menu item. The user should be able to remove items from the list and database.
•	Your application should save the last topic that was searched to display the next time the application is launched.
Sign up for a free api key at: https://www.food2fork.com/about/api
 
Foreign currency conversion api
•	Create an interface that allows the user to enter a number, and convert that number from one currency to another. Have a look at the website for examples: https://exchangeratesapi.io/
•	This url is an example of converting USD to EUR and GBP: https://api.exchangeratesapi.io/latest?base=USD&symbols=EUR,GBP
•	The results should show the different exchange rates. The user can save these conversions to a list of favourites, for example “USD  CAD”, “CAD  EUR”. The user should be able to search for conversion rates for various currencies. The application will show conversion rates for that currency. The user can save the conversion type (USD – EUR), or (CAD – GBP) for later viewing in a favourites list. Don’t save the specific rate because that changes from day to day. The idea is to re-run the conversion at a later date.  This list of conversions should be accessible from a menu item. The user should be able to remove items from the list and database.
•	Your application should save the last conversion (currency, and amount) that was searched to display the next time the application is launched. 
•	The list of currencies that are supported can be found by going to: https://api.exchangeratesapi.io/latest

News Api.org headlines api
•	Create an interface that searches for news stories using Newsapi.org. You should sign up for a free API key by going here: https://newsapi.org/register.
•	Look at: https://newsapi.org/docs/endpoints/everything for examples of different searches.
•	The user can search for articles related to a given topic that the user enters. Your application should show a list of results from the server. For example, this URL will search for Tesla cars: https://newsapi.org/v2/everything?apiKey=YOUR_KEY&q=Tesla
•	The user should be able to select an item from the list, and your application should show the Title, Description, Image, and button to go to the article in a web browser.
•	When viewing the details about a news article, there should be a button on the article to save this article to your device for later viewing. From the list of saved articles, the user should be able to remove items from the list and database.
•	Your application should save the last topic that was searched to display the next time the application is launched.
