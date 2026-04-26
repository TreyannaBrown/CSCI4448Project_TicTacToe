package tictactoe;

import tictactoe.observers.GameObserver;
import tictactoe.players.Player;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe {
    private static final int BOARD_SIZE = 3;
    private static final int FIRST_INDEX = 0;
    private static final int SECOND_INDEX = 1;
    private static final int THIRD_INDEX = 2;

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
    private int[][] winningCells;

    public TicTacToe(Player player1, Player player2) {
        this.board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.startingPlayer = player1;
        this.observers = new ArrayList<>();
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
        board = new Board();
        startingPlayer = getNextStartingPlayer();
        currentPlayer = startingPlayer;
        winner = null;
        gameOver = false;
        winningCells = null;
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

    public int[][] getWinningCells() {
        return winningCells;
    }

    private void updateGameState() {
        if (hasCurrentPlayerWon()) {
            handleWin();
        } else if (board.isFull()) {
            gameOver = true;
        } else {
            switchPlayer();
        }
    }

    private boolean hasCurrentPlayerWon() {
        String symbol = currentPlayer.getSymbol();
        return checkRows(symbol) || checkColumns(symbol) || checkDiagonals(symbol);
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

    private void switchPlayer() {
        currentPlayer = getOtherPlayer(currentPlayer);
    }

    private Player getOtherPlayer(Player player) {
        return player == player1 ? player2 : player1;
    }

    private Player getNextStartingPlayer() {
        return startingPlayer == player1 ? player2 : player1;
    }

    private boolean checkRows(String symbol) {
        String[][] grid = board.getGrid();

        for (int row = 0; row < BOARD_SIZE; row++) {
            if (matches(symbol, grid[row][FIRST_INDEX], grid[row][SECOND_INDEX], grid[row][THIRD_INDEX])) {
                winningCells = new int[][]{{row, FIRST_INDEX}, {row, SECOND_INDEX}, {row, THIRD_INDEX}};
                return true;
            }
        }

        return false;
    }

    private boolean checkColumns(String symbol) {
        String[][] grid = board.getGrid();

        for (int col = 0; col < BOARD_SIZE; col++) {
            if (matches(symbol, grid[FIRST_INDEX][col], grid[SECOND_INDEX][col], grid[THIRD_INDEX][col])) {
                winningCells = new int[][]{{FIRST_INDEX, col}, {SECOND_INDEX, col}, {THIRD_INDEX, col}};
                return true;
            }
        }

        return false;
    }

    private boolean checkDiagonals(String symbol) {
        return checkLeftDiagonal(symbol) || checkRightDiagonal(symbol);
    }

    private boolean checkLeftDiagonal(String symbol) {
        String[][] grid = board.getGrid();

        if (matches(symbol, grid[FIRST_INDEX][FIRST_INDEX], grid[SECOND_INDEX][SECOND_INDEX],
                grid[THIRD_INDEX][THIRD_INDEX])) {
            winningCells = new int[][]{
                    {FIRST_INDEX, FIRST_INDEX},
                    {SECOND_INDEX, SECOND_INDEX},
                    {THIRD_INDEX, THIRD_INDEX}
            };
            return true;
        }

        return false;
    }

    private boolean checkRightDiagonal(String symbol) {
        String[][] grid = board.getGrid();

        if (matches(symbol, grid[FIRST_INDEX][THIRD_INDEX], grid[SECOND_INDEX][SECOND_INDEX],
                grid[THIRD_INDEX][FIRST_INDEX])) {
            winningCells = new int[][]{
                    {FIRST_INDEX, THIRD_INDEX},
                    {SECOND_INDEX, SECOND_INDEX},
                    {THIRD_INDEX, FIRST_INDEX}
            };
            return true;
        }

        return false;
    }

    private boolean matches(String symbol, String firstCell, String secondCell, String thirdCell) {
        return symbol.equals(firstCell) && symbol.equals(secondCell) && symbol.equals(thirdCell);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }
}