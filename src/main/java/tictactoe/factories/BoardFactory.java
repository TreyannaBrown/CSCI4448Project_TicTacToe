package tictactoe.factories;

import tictactoe.Board;
import tictactoe.BoardCell;
import tictactoe.WinConditions.WinCondition;
import tictactoe.enums.GameType;

public class BoardFactory {
    private static final int STANDARD_BOARD_DIMENSIONS = 3;
    private static final int PYRAMID_BOARD_HEIGHT = 3;
    private static final int PYRAMID_BOARD_WIDTH = 5;
    private static final int CONNECT_FOUR_HEIGHT = 4;
    private static final int CONNECT_FOUR_WIDTH = 4;
    private final WinConditionFactory factory = new WinConditionFactory();
    public Board createBoard(GameType type) {
        WinCondition condition = factory.createWinCondition(type);
        if(type == GameType.STANDARD){
            return new Board(initializeSquareBoard(STANDARD_BOARD_DIMENSIONS, STANDARD_BOARD_DIMENSIONS), condition);
        }
        if(type == GameType.PYRAMID){
            return new Board(initializePyramidBoard(), condition);
        }
        if(type == GameType.CONNECT_FOUR){
            return new Board(initializeSquareBoard(CONNECT_FOUR_HEIGHT, CONNECT_FOUR_WIDTH), condition);
        }
        else{
            throw new IllegalArgumentException("Unknown game type: " + type);
        }
    }

    private BoardCell[][] initializeSquareBoard(int height, int width){
        BoardCell[][] board = new BoardCell[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                board[i][j] = new BoardCell(true);
            }
        }
        return board;
    }

    private BoardCell[][] initializePyramidBoard(){
        BoardCell[][] board = new BoardCell[PYRAMID_BOARD_HEIGHT][PYRAMID_BOARD_WIDTH];

        int invalidCells = PYRAMID_BOARD_WIDTH / 2;
        for(int row = 0; row < PYRAMID_BOARD_HEIGHT; row++){
            for(int col = 0; col < PYRAMID_BOARD_WIDTH; col++){
                boolean isValidCell = (col >= invalidCells) && (col < (PYRAMID_BOARD_WIDTH - invalidCells));
                board[row][col] = new BoardCell(isValidCell);
            }
            invalidCells -= 1;
        }
        return board;
    }


}
