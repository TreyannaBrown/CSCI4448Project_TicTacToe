package tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tictactoe.enums.GameType;
import tictactoe.players.HumanPlayer;
import tictactoe.players.Player;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {

    private TicTacToe game;
    private Player playerOne;
    private Player playerTwo;

    @BeforeEach
    void setUp() {
        playerOne = new HumanPlayer("Player 1", "X");
        playerTwo = new HumanPlayer("Player 2", "O");
        game = new TicTacToe(playerOne, playerTwo, GameType.STANDARD);
    }


    @Test
    void testGetCurrentPlayer() {
        assertEquals(playerOne, game.getCurrentPlayer());
    }

    @Test
    void testGameSwitchesPlayersAfterTurn() {
        assertTrue(game.makeMove(0, 0));
        assertEquals(playerTwo, game.getCurrentPlayer());
    }

    @Test
    void testPlaceSymbol() {
        game.makeMove(0, 0);

        assertEquals("X", game.getBoard().getValue(0, 0));
    }

    @Test
    void TestInvalidMoveDoesntSwitchTurn() {
        game.makeMove(0, 0);
        Player current = game.getCurrentPlayer();

        assertFalse(game.makeMove(0, 0));
        assertEquals(current, game.getCurrentPlayer());
    }

    @Test
    void testGameEndsAfterWin() {
        game.makeMove(0, 0);
        game.makeMove(1, 0);
        game.makeMove(0, 1);
        game.makeMove(1, 1);
        game.makeMove(0, 2);

        assertTrue(game.isGameOver());
        assertEquals(playerOne, game.getWinner());
        assertEquals(1, game.getPlayer1Wins());
    }

    @Test
    void testGameEndsInDraw() {
        game.makeMove(0, 0);
        game.makeMove(0, 1);
        game.makeMove(0, 2);
        game.makeMove(1, 1);
        game.makeMove(1, 0);
        game.makeMove(1, 2);
        game.makeMove(2, 1);
        game.makeMove(2, 0);
        game.makeMove(2, 2);

        assertTrue(game.isGameOver());
        assertNull(game.getWinner());
    }


    @Test
    void testResetGame() {
        game.makeMove(0, 0);
        game.resetGame();

        assertNull(game.getBoard().getValue(0, 0));
        assertEquals(playerTwo, game.getCurrentPlayer());
        assertFalse(game.isGameOver());
        assertNull(game.getWinner());
    }

    @Test
    void testGetOtherPlayer() {
        assertEquals(playerTwo, game.getOtherPlayer(playerOne));
        assertEquals(playerOne, game.getOtherPlayer(playerTwo));
    }
}