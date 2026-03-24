package tictactoe;

import org.junit.jupiter.api.Test;
import tictactoe.enums.GameType;
import tictactoe.enums.GridValues;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void check10X10BoardCreation(){
        BoardFactory factory = new BoardFactory();
        Board board = factory.createBoard(10, GameType.STANDARD);
        board.EnterCellValue(9,9,GridValues.X);
        assertNotNull(board.getGrid()[9][9]);
    }

    @Test
    void checkBoardUpdate(){
        BoardFactory factory = new BoardFactory();
        Board board = factory.createBoard(3, GameType.STANDARD);
        board.EnterCellValue(1,1, GridValues.X);
        assertEquals(board.getGrid()[1][1], GridValues.X);
    }
}
