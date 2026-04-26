# Tic Tac Toe
Object-Oriented Programming Project 

Group Members: Derek Marraudino, Treyanna Brown 

**Project Overview**

Tic-Tac-Toe is a classic strategy game played by people of all ages. This project implements the game using Java Swing with a focus on object-oriented design and clean architecture. The application allows users to select different visual themes, choose between multiple game modes, and play against either another player or a computer opponent with varying difficulty levels.

Players can enter custom names, view real-time score tracking, and replay games with alternating starting turns. The project emphasizes both user experience and maintainable, well-structured code.

**Java Version**

25

**How To Run**

Run StartMenu.java to start the game. 

**Design Patterns Used**

-_MVC (Model-View-Controller)_

 Separates the game logic (TicTacToe, Board) from the UI (SwingTicTacToe, StartMenu).

-_Observer Pattern_

 The UI updates automatically when the game state changes using GameObserver.

-_Strategy Pattern_

 Different computer difficulties (Easy, Hard) are implemented using interchangeable move strategies.

-_Factory Pattern_

 GameFactory is used to create different game modes (Human vs Human, Human vs Computer, etc.). 
 BoardFactory is used to create boards of different shapes and sizes. 

**Citations**

OpenAI ChatGPT was used to assist with the initial UI skeleton and layout design; all logic and integration were implemented independently.
 
