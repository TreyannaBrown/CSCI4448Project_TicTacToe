package tictactoe;

import tictactoe.WinConditions.WinCondition;

public class Board {
    private final BoardCell[][] grid;
    private final WinCondition winCondition;

    private Move lastMove;
    private int[][] winningCells;

    public Board(BoardCell[][] grid, WinCondition winCondition) {
        this.grid = grid;
        this.winCondition = winCondition;
        this.lastMove = null;
        this.winningCells = null;
    }

    public boolean placeMove(int row, int col, String symbol) {
        if (!grid[row][col].isValid() || !grid[row][col].isEmpty()) {
            return false;
        }

        grid[row][col].setCellValue(symbol);
        lastMove = new Move(row, col);
        return true;
    }

    public String getValue(int row, int col) {
        return grid[row][col].getCellValue();
    }

    public BoardCell[][] getGrid() {
        return grid;
    }

    public boolean isFull() {
        for (BoardCell[] boardCells : grid) {
            for (BoardCell boardCell : boardCells) {
                if (boardCell.isValid() && boardCell.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public WinCondition getWinCondition() {
        return winCondition;
    }

    public int[][] getWinningCells() {
        return winningCells;
    }

    public void setWinningCells(int[][] winningCells) {
        this.winningCells = winningCells;
    }
}