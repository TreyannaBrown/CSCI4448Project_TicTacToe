package tictactoe.strategies;

public interface MoveStrategy {
    int[] chooseMove(String[][] board);
}