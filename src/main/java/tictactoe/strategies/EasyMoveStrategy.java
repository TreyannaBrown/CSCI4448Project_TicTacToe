package tictactoe.strategies;

import tictactoe.BoardCell;

public class EasyMoveStrategy implements MoveStrategy {

    @Override
    public int[] chooseMove(BoardCell[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }
}