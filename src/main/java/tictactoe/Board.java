package tictactoe;

import tictactoe.WinConditions.WinCondition;

public class Board {
    private final BoardCell[][] grid;
    private Move lastMove;
    private final WinCondition winCondition;

    public Board(BoardCell[][] grid, WinCondition winCondition){
        this.grid = grid;
        this.lastMove = null;
        this.winCondition = winCondition;
    }
    public boolean placeMove(int row, int col, String symbol) {
        if (grid[row][col] != null) {
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
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (grid[row][col].getCellValue() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public Move getLastMove(){
        return lastMove;
    }

    public WinCondition getWinCondition(){
        return winCondition;
    }
}