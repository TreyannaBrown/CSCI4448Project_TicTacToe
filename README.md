# CSCI4448Project_TicTacToe
Object-Oriented Programming Project 

Group Members: Derek Marraudino, Treyanna Brown 

**Project Description**

Tic-Tac-Toe is a classic game played by people of all ages around the world! We will apply Object-Oriented Programming fundamentals to program a fun interractive game. 

**Mid Project Review Description**

Three Design Patterns:
The first pattern used is within the SwingTicTacToe class. This uses a type of observer pattern because it is dependent upon input clicks from the user. When a button is clicked, the listener responds. The second design pattern will be in the CheckWinCondition class. The class will utilize the Strategy Pattern to check the different kind of win conditions that can be met, such as a diagonal line, across a row, or down a column. This will be useful in case the game is extended to different variatons and shapes of the board. The third design pattern will be the Factory Pattern, this will be used to create different board sizes and configurations.

Core OO Principles: 
Our project invokes polymorphism within our Player classes. We have implemented a Player super class and two subclasses; computer player and human player. This allows the UI to determine player type upon each players turn and handle the situations differently while simultaneously keeping things DRY by having most methods within the super class. This is also an example of coding to an abstraction since the TicTacToe class does't need to know what the player type is to execute. Dependency Injection will be applied once we are further in the project with player and possibly board injections. 

Tests:
During this project we have chosen to follow Test Driven Development (TDD) to make sure we are actually developing useful methodology that will execute as expected. 






Ideas : players can pick different icons, player vs player, player vs computer, add a reset button, best of __, scoreboard. 
