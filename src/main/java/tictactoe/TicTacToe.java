package tictactoe;

import tictactoe.enums.GameType;
import tictactoe.factories.BoardFactory;
import tictactoe.observers.GameObserver;
import tictactoe.players.Player;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe {
    private final Player player1;
    private final Player player2;
    private final GameType gameType;
    private final BoardFactory boardFactory;
    private final List<GameObserver> observers;

    private Board board;
    private Player currentPlayer;
    private Player winner;
    private Player startingPlayer;
    private boolean gameOver;
    private int player1Wins;
    private int player2Wins;

    public TicTacToe(Player player1, Player player2, GameType gameType) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameType = gameType;
        this.boardFactory = new BoardFactory();
        this.observers = new ArrayList<>();
        this.startingPlayer = player1;

        resetBoardState(player1);
    }

    public boolean makeMove(int row, int col) {
        if (gameOver || !board.placeMove(row, col, currentPlayer.getSymbol())) {
            return false;
        }

        updateGameState();
        notifyObservers();
        return true;
    }

    public void resetGame() {
        startingPlayer = getOtherPlayer(startingPlayer);
        resetBoardState(startingPlayer);
        notifyObservers();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    public int getPlayer2Wins() {
        return player2Wins;
    }

    public Player getOtherPlayer(Player player) {
        return player == player1 ? player2 : player1;
    }

    private void resetBoardState(Player firstPlayer) {
        board = boardFactory.createBoard(gameType);
        currentPlayer = firstPlayer;
        winner = null;
        gameOver = false;
    }

    private void updateGameState() {
        if (board.getWinCondition().checkForWin(board)) {
            handleWin();
        } else if (board.isFull()) {
            gameOver = true;
        } else {
            currentPlayer = getOtherPlayer(currentPlayer);
        }
    }

    private void handleWin() {
        winner = currentPlayer;
        gameOver = true;
        updateWinCount();
    }

    private void updateWinCount() {
        if (winner == player1) {
            player1Wins++;
        } else {
            player2Wins++;
        }
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }
}