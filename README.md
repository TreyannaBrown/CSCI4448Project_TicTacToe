# Tic Tac Toe
Object-Oriented Programming Project 

Group Members: Derek Marraudino, Treyanna Brown 

**Project Overview**

Tic-Tac-Toe is a classic game played by people of all ages around the world! This project is a Java Swing Tic Tac Toe game with multiple themes, game modes, and computer difficulty levels. Players can enter their names, play against another player or the computer, and replay games while tracking wins. 

**Java Version**
25
**How To Run**

Run StartMenu.java to start the game. 

**Design Patterns Used**

-MVC (Model-View-Controller)

Separates the game logic (TicTacToe, Board) from the UI (SwingTicTacToe, StartMenu).

-Observer Pattern

The UI updates automatically when the game state changes using GameObserver.

-Strategy Pattern

Different computer difficulties (Easy, Hard) are implemented using interchangeable move strategies.

-Factory Pattern

GameFactory is used to create different game modes (Human vs Human, Human vs Computer, etc.).

**Citations**

OpenAI ChatGPT was used to assist with the initial UI skeleton and layout design; all logic and integration were implemented independently.
 
