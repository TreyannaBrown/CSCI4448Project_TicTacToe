package tictactoe.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HardMoveStrategy implements MoveStrategy {
    private Random random = new Random();

    @Override
    public int[] chooseMove(String[][] board) {
        List<int[]> availableMoves = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    availableMoves.add(new int[]{row, col});
                }
            }
        }

        if (availableMoves.isEmpty()) {
            return null;
        }

        return availableMoves.get(random.nextInt(availableMoves.size()));
    }
}
