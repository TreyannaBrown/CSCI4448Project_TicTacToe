package tictactoe.winStrategies;

import tictactoe.enums.GridValues;

public interface WinStrategy {
    public boolean checkWin(GridValues[][] grid);
}
