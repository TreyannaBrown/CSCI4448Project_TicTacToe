package tictactoe;

import tictactoe.players.Player;

public class TicTacToe {
    private final Player playerOne;
    private final Player playerTwo;
    private boolean gameOver;
    private Player currentPlayer;
    private Player winner;

    public TicTacToe(Player playerOne, Player playerTwo){
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;

    }

    public boolean makeMove(int row, int col) {
        if (gameOver) return false;

        //input players choice

        if (hasWinner()) {
            gameOver = true;
            winner = currentPlayer;
        } else if (isBoardFull()) {
            gameOver = true;
        } else {
            switchPlayer();
        }
        return true;
    }

    private boolean isBoardFull() {
        // implement here or in board
        return true;
    }

    private boolean hasWinner() {
        return true;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    private void switchPlayer(){
        if (currentPlayer == playerOne){
            currentPlayer = playerTwo;
        }
        else
            currentPlayer = playerOne;
    }

    public Player getWinner(){
        return winner;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void reset() {
        // initialize the board
        currentPlayer = playerOne;
        gameOver = false;
        winner = null;
    }
}
