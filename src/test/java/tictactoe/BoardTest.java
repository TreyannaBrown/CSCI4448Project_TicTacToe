package tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private static final int BOARD_SIZE = 3;

    @Test
    public void testBoardStartsEmpty() {
        Board board = createStandardBoard();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                assertNull(board.getValue(row, col));
            }
        }
    }

    @Test
    public void testPlaceMoveOnEmptySpot() {
        Board board = createStandardBoard();

        boolean movePlaced = board.placeMove(0, 0, "X");

        assertTrue(movePlaced);
        assertEquals("X", board.getValue(0, 0));
    }

    @Test
    public void testCannotPlaceMoveOnOccupiedSpot() {
        Board board = createStandardBoard();

        board.placeMove(1, 1, "X");
        boolean secondMovePlaced = board.placeMove(1, 1, "O");

        assertFalse(secondMovePlaced);
        assertEquals("X", board.getValue(1, 1));
    }

    @Test
    public void testBoardIsNotFullAtStart() {
        Board board = createStandardBoard();

        assertFalse(board.isFull());
    }

    @Test
    public void testBoardIsFullAfterAllSpacesFilled() {
        Board board = createStandardBoard();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board.placeMove(row, col, "X");
            }
        }

        assertTrue(board.isFull());
    }

    @Test
    public void testGetGridReturnsPlacedValues() {
        Board board = createStandardBoard();

        board.placeMove(2, 2, "O");

        BoardCell[][] grid = board.getGrid();

        assertEquals("O", grid[2][2].getCellValue());
    }

    @Test
    public void testCannotPlaceMoveOnInvalidCell() {
        BoardCell[][] grid = createStandardGrid();
        grid[0][0] = new BoardCell(false);

        Board board = new Board(grid);

        boolean movePlaced = board.placeMove(0, 0, "X");

        assertFalse(movePlaced);
        assertNull(board.getValue(0, 0));
    }

    private Board createStandardBoard() {
        return new Board(createStandardGrid());
    }

    private BoardCell[][] createStandardGrid() {
        BoardCell[][] grid = new BoardCell[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                grid[row][col] = new BoardCell(true);
            }
        }

        return grid;
    }
}