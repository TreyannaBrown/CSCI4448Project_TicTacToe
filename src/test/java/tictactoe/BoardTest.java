package tictactoe;

import org.junit.jupiter.api.Test;
import tictactoe.WinConditions.ThreeInARowWin;
import tictactoe.enums.GameType;
import tictactoe.factories.BoardFactory;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private final BoardFactory boardFactory = new BoardFactory();
    @Test
    public void testBoardStartsEmpty(){
        Board board = boardFactory.createBoard(GameType.STANDARD);
        BoardCell[][] boardGrid = board.getGrid();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertNull(boardGrid[row][col].getCellValue());
            }
        }
    }

    @Test
    public void testPlaceMoveOnEmptySpot(){
        Board board = boardFactory.createBoard(GameType.STANDARD);
        BoardCell[][] boardGrid = board.getGrid();
        boolean movePlaced = board.placeMove(0, 0, "X");

        assertTrue(movePlaced);
        assertEquals("X", boardGrid[0][0].getCellValue());
    }

    @Test
    public void testCannotPlaceMoveOnOccupiedSpot(){
        Board board = boardFactory.createBoard(GameType.STANDARD);

        board.placeMove(1, 1, "X");
        boolean secondMovePlaced = board.placeMove(1, 1, "O");

        assertFalse(secondMovePlaced);
        assertEquals("X", board.getValue(1, 1));
    }

    @Test
    public void testBoardIsNotFullAtStart(){
        Board board = boardFactory.createBoard(GameType.STANDARD);
        assertFalse(board.isFull());
    }

    @Test
    public void testBoardIsFullAfterAllSpacesFilled(){
        Board board = boardFactory.createBoard(GameType.STANDARD);
        String symbol = "X";

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board.placeMove(row, col, symbol);
            }
        }

        assertTrue(board.isFull());
    }

    @Test
    public void testGetGridReturnsPlacedValues(){
        Board board = boardFactory.createBoard(GameType.STANDARD);

        board.placeMove(2, 2, "O");

        assertEquals("O", board.getValue(2,2));
    }

    @Test
    public void testPlaceInInvalidSpace(){
        Board board = boardFactory.createBoard(GameType.PYRAMID);
        boolean placeValue = board.placeMove(0,0,"X");
        assertFalse(placeValue);
        assertNull(board.getValue(0,0));
    }

    @Test
    public void testGetLastMove(){
        Board board = boardFactory.createBoard(GameType.STANDARD);
        Move placedMove = new Move(0,0);
        board.placeMove(placedMove.row(), placedMove.column(), "X");
        Move lastMove = board.getLastMove();

        assertEquals(placedMove,lastMove);
    }

    @Test
    public void testGetWinStrategy(){
        Board board = boardFactory.createBoard(GameType.STANDARD);

        assertInstanceOf(ThreeInARowWin.class, board.getWinCondition());
    }


    private boolean containsCell(int[][] cells, int row, int col) { //helper for following test
        for(int[] cell : cells){
            if(cell[0] == row && cell[1] == col){
                return true;
            }
        }
        return false;
    }
    @Test
    void testGetWinningCells(){
        Board board = boardFactory.createBoard(GameType.STANDARD);
        board.placeMove(0, 0, "X");
        board.placeMove(0, 1, "X");
        board.placeMove(0, 2, "X");

        board.getWinCondition().checkForWin(board);
        int[][] winningCells = board.getWinningCells();

        assertNotNull(winningCells);
        assertEquals(3, winningCells.length);
        assertTrue(containsCell(winningCells, 0, 0));
        assertTrue(containsCell(winningCells, 0, 1));
        assertTrue(containsCell(winningCells, 0, 2));
    }


}