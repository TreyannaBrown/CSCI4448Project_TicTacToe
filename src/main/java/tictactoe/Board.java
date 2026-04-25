package tictactoe;

public class Board {
    private String[][] grid = new String[3][3];

    public boolean placeMove(int row, int col, String symbol) {
        if (grid[row][col] != null) {
            return false;
        }

        grid[row][col] = symbol;
        return true;
    }

    public String getValue(int row, int col) {
        return grid[row][col];
    }

    public String[][] getGrid() {
        return grid;
    }

    public boolean isFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (grid[row][col] == null) {
                    return false;
                }
            }
        }
        return true;
    }
}