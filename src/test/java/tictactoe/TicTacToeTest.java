package tictactoe;

import org.junit.jupiter.api.Test;
import tictactoe.players.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicTacToeTest {
    private TicTacToe game;
    private Player playerOne;
    private Player playerTwo;

    @Test
    void moveSwitchesTurnToOtherPlayer() {
        game.makeMove(0, 0);

        assertEquals(playerTwo, game.getCurrentPlayer());
    }
}
