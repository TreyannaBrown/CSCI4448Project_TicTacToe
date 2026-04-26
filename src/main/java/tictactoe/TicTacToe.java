package tictactoe;

import tictactoe.enums.GameType;
import tictactoe.factories.BoardFactory;
import tictactoe.observers.GameObserver;
import tictactoe.players.Player;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe {
    private Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private Player winner;
    private boolean gameOver;
    private Player startingPlayer;
    private int player1Wins;
    private int player2Wins;
    private final List<GameObserver> observers;
    private final GameType gameType;
    private final BoardFactory factory = new BoardFactory();

    public TicTacToe(Player player1, Player player2, GameType type) {
        this.board = factory.createBoard(type);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.winner = null;
        this.gameOver = false;
        this.observers = new ArrayList<>();
        this.startingPlayer = player1;
        this.player1Wins = 0;
        this.player2Wins = 0;
        this.gameType = type;
    }

    public boolean makeMove(int row, int col) {
        if (gameOver) {
            return false;
        }

        boolean moveMade = board.placeMove(row, col, currentPlayer.getSymbol());

        if (!moveMade) {
            return false;
        }

        if (board.getWinCondition().checkForWin(board)) {
            winner = currentPlayer;
            gameOver = true;

            if (winner == player1) {
                player1Wins++;
            } else {
                player2Wins++;
            }
        } else if (board.isFull()) {
            gameOver = true;
        } else {
            switchPlayer();
        }

        notifyObservers();
        return true;
    }

    private void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
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

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }
    public void resetGame() {
        this.board = factory.createBoard(gameType);

        if (startingPlayer == player1) {
            startingPlayer = player2;
        } else {
            startingPlayer = player1;
        }
        this.currentPlayer = startingPlayer;
        this.winner = null;
        this.gameOver = false;
        notifyObservers();
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
}

//MVC: Board/TicTacToe + Swing UI
//Observer: GameObserver
//Strategy: MoveStrategy
//Factory: GameFactory