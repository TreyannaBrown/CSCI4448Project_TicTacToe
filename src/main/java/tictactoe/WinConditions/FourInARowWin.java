package tictactoe.WinConditions;

import tictactoe.Board;
import tictactoe.BoardCell;

public class FourInARowWin implements WinCondition {
    private static final int WIN_LENGTH = 4;

    @Override
    public boolean checkForWin(Board board) {
        if (board.getLastMove() == null) {
            return false;
        }

        int row = board.getLastMove().row();
        int col = board.getLastMove().column();
        String symbol = board.getValue(row, col);

        return checkLine(board, row, col, symbol, 0, 1)
                || checkLine(board, row, col, symbol, 1, 0)
                || checkLine(board, row, col, symbol, 1, 1)
                || checkLine(board, row, col, symbol, 1, -1);
    }

    private boolean checkLine(Board board, int row, int col, String symbol,
                              int rowIncrement, int colIncrement) {
        int[][] cells = new int[WIN_LENGTH][2];
        int count = collectWinningCells(board, row, col, symbol, rowIncrement, colIncrement, cells);

        if (count >= WIN_LENGTH) {
            board.setWinningCells(cells);
            return true;
        }

        return false;
    }

    private int collectWinningCells(Board board, int row, int col, String symbol,
                                    int rowIncrement, int colIncrement, int[][] cells) {
        cells[0] = new int[]{row, col};

        int count = 1;

        count = collectDirection(
                board,
                row + rowIncrement,
                col + colIncrement,
                symbol,
                rowIncrement,
                colIncrement,
                cells,
                count
        );

        count = collectDirection(
                board,
                row - rowIncrement,
                col - colIncrement,
                symbol,
                -rowIncrement,
                -colIncrement,
                cells,
                count
        );

        return count;
    }

    private int collectDirection(Board board, int row, int col, String symbol,
                                 int rowStep, int colStep, int[][] cells, int count) {
        BoardCell[][] grid = board.getGrid();

        while (count < WIN_LENGTH && isMatchingCell(grid, row, col, symbol)) {
            cells[count] = new int[]{row, col};
            count++;
            row += rowStep;
            col += colStep;
        }

        return count;
    }

    private boolean isMatchingCell(BoardCell[][] grid, int row, int col, String symbol) {
        return row >= 0
                && row < grid.length
                && col >= 0
                && col < grid[row].length
                && grid[row][col].isValid()
                && symbol.equals(grid[row][col].getCellValue());
    }
}