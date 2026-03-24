package tictactoe;

import tictactoe.enums.GridValues;

public class Board {
    private GridValues[][] grid;
    private CheckWinCondition winChecker;
    public Board(GridValues[][] grid, CheckWinCondition winChecker){
        this.grid = grid;
        this.winChecker = winChecker;
    }

    public void EnterCellValue(int row, int column, GridValues value){
        grid[row][column] = value;
    }

    public GridValues[][] getGrid() {
        GridValues[][] gridCopy = new GridValues[grid.length][grid.length];

        for(int i = 0; i < grid.length; i++) {
            gridCopy[i] = grid[i].clone();
        }

        return gridCopy;
    }

    public boolean checkForWin(){
        return winChecker.CheckIfWin(getGrid());
    }
}
