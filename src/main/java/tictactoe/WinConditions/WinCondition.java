package tictactoe.WinConditions;

import tictactoe.Board;

public interface WinCondition {
    boolean checkForWin(Board board);

}
