package tictactoe.WinConditions;

import tictactoe.Board;
import tictactoe.BoardCell;

public class FourInARowWin implements WinCondition {
    private static final int WIN_LENGTH = 4;

    @Override
    public boolean checkForWin(Board board){
        if(board.getLastMove() == null){
            return false;
        }

        int row = board.getLastMove().row();
        int col = board.getLastMove().column();
        String symbol = board.getValue(row, col);

        return(checkLine(board, row, col, symbol, 0, 1)   // horizontal
                || checkLine(board, row, col, symbol, 1, 0)   // vertical
                || checkLine(board, row, col, symbol, 1, 1)   // diagonal \ (column increment is down)
                || checkLine(board, row, col, symbol, 1, -1)); // diagonal /
    }

    private boolean checkLine(Board board, int row, int col, String symbol, int rowIncrement, int colIncrement) {
        int count = 1; //last placed symbol already counts for 1

        count += countMatches(board, row, col, symbol, rowIncrement, colIncrement);
        count += countMatches(board, row, col, symbol, -rowIncrement, -colIncrement);

        return (count >= WIN_LENGTH);
    }

    private int countMatches(Board board, int row, int col, String symbol, int rowStep, int colStep) {
        int count = 0;
        BoardCell[][] grid = board.getGrid();

        row += rowStep;
        col += colStep;

        while(row >= 0 && row < grid.length && col >= 0 && col < grid[row].length && grid[row][col].isValid() && symbol.equals(grid[row][col].getCellValue())){
            count++;
            row += rowStep;
            col += colStep;
        }

        return count;
    }
}
