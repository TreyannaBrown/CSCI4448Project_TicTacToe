package tictactoe.players;

import tictactoe.BoardCell;
import tictactoe.strategies.MoveStrategy;

public class ComputerPlayer extends Player {
    private MoveStrategy moveStrategy;

    public ComputerPlayer(String name, String symbol, MoveStrategy moveStrategy) {
        super(name, symbol);
        this.moveStrategy = moveStrategy;
    }

    @Override
    public boolean isComputer() {
        return true;
    }

    public int[] getMove(BoardCell[][] board) {
        return moveStrategy.chooseMove(board);
    }
}
