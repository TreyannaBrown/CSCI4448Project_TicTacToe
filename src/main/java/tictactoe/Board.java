package tictactoe;

public class Board {
    private final BoardCell[][] grid;

    public Board(BoardCell[][] grid) {
        this.grid = grid;
    }

    public boolean placeMove(int row, int col, String symbol) {
        if (!isInBounds(row, col) || !grid[row][col].isValid() || !grid[row][col].isEmpty()) {
            return false;
        }

        grid[row][col].setCellValue(symbol);
        return true;
    }

    public String getValue(int row, int col) {
        return grid[row][col].getCellValue();
    }

    public BoardCell[][] getGrid() {
        return grid;
    }

    public boolean isFull() {
        for (BoardCell[] row : grid) {
            for (BoardCell cell : row) {
                if (cell.isValid() && cell.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length;
    }
}