package tictactoe.strategies;

import tictactoe.BoardCell;

public interface MoveStrategy {
    int[] chooseMove(BoardCell[][] board);
}