# TaskMaster

## Lab26

### Summary

Today I created a home page that has two buttons. The add Task button is routed to the add task xml which displays two input text boxes, a total task text, and a add task button. The add task button has an onClick event listener that will change the text of a blank text box at the bottom of the screen to show submitted. The routing for the all task is also set up but no functionality at this time.

![Lab26 Home](screenshots/Lab26/Lab26_Home.PNG)
![Lab26 addTask](screenshots/Lab26/Lab26_allTask.PNG)
![Lab26 allTask](screenshots/Lab26/Lab26_AddTask.PNG)

## Lab27

### Summary

Today's Lab we dipped into shared preferences and extras. The homepage was updated to have buttons that go to a settings activity as well as task detail activity. On the settings page you are able to update the username shared preference which is displayed in multiple places. The Task Detail buttons on the home page will pass in an extra of the Text adjacent to the button and sets the Title of the task page.

![Lab27 Home](screenshots/Lab27/Lab27_Home.PNG)
![Lab27 Settings](screenshots/Lab27/Lab27_Settings.PNG)
![Lab27 TaskDetail](screenshots/Lab27/Lab27_TaskDetail.PNG)

## Lab28

### Summary

Today's lab was all about setting up a RecyclerView. The buttons on the homepage were instead replaced with a RecyclerViewer that iterates over a list of Tasks and displays them on screen. Each task has an event listener that will pass on extras of the tasks properties which will populate the Task Detail page.

![Lab28 Home](screenshots/Lab28/Lab28_Home.PNG)

## Lab29

### Summary

In Today's Lab we implemented Rooms which is a SQL database. A StateEnum was created as the state options for the tasks and was set as one of the properties for the Task class. Functionality was added so that when the user presses the add task button the information gets taken and saved into the database.

![Lab29 Home](screenshots/lab29/Lab29_Home.PNG)
![Lab29 Task Add](screenshots/lab29/Lab29_AddTask.PNG)
![Lab29 Task Detail](screenshots/lab29/Lab29_TaskDetail.PNG)