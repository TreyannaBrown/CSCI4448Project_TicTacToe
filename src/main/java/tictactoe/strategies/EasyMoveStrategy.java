package tictactoe.strategies;

import tictactoe.BoardCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyMoveStrategy implements MoveStrategy {
    private final Random random = new Random();

    @Override
    public int[] chooseMove(BoardCell[][] board, String computerSymbol, String opponentSymbol) {
        List<int[]> openMoves = getOpenMoves(board);

        if (openMoves.isEmpty()) {
            return null;
        }

        return openMoves.get(random.nextInt(openMoves.size()));
    }

    private List<int[]> getOpenMoves(BoardCell[][] board) {
        List<int[]> openMoves = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            addOpenMovesFromRow(board, row, openMoves);
        }

        return openMoves;
    }

    private void addOpenMovesFromRow(BoardCell[][] board, int row, List<int[]> openMoves) {
        for (int col = 0; col < board[row].length; col++) {
            if (board[row][col].isValid() && board[row][col].isEmpty()) {
                openMoves.add(new int[]{row, col});
            }
        }
    }
}