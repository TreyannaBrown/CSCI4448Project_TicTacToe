package tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testBoardStartsEmpty() {
        Board board = new Board();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertNull(board.getValue(row, col));
            }
        }
    }

    @Test
    public void testPlaceMoveOnEmptySpot() {
        Board board = new Board();

        boolean movePlaced = board.placeMove(0, 0, "X");

        assertTrue(movePlaced);
        assertEquals("X", board.getValue(0, 0));
    }

    @Test
    public void testCannotPlaceMoveOnOccupiedSpot() {
        Board board = new Board();

        board.placeMove(1, 1, "X");
        boolean secondMovePlaced = board.placeMove(1, 1, "O");

        assertFalse(secondMovePlaced);
        assertEquals("X", board.getValue(1, 1));
    }

    @Test
    public void testBoardIsNotFullAtStart() {
        Board board = new Board();

        assertFalse(board.isFull());
    }

    @Test
    public void testBoardIsFullAfterAllSpacesFilled() {
        Board board = new Board();

        String symbol = "X";

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board.placeMove(row, col, symbol);
            }
        }

        assertTrue(board.isFull());
    }

    @Test
    public void testGetGridReturnsPlacedValues() {
        Board board = new Board();

        board.placeMove(2, 2, "O");

        BoardCell[][] grid = board.getGrid();

        assertEquals("O", grid[2][2].getCellValue());
    }
}
