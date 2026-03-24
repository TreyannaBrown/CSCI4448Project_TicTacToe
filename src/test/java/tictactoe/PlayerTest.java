package tictactoe;

import org.junit.jupiter.api.Test;
import tictactoe.players.ComputerPlayer;
import tictactoe.players.HumanPlayer;
import tictactoe.players.Player;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    @Test
    void humanPlayerIsNotComputer() {
        Player human = new HumanPlayer("Kevin", "X");
        assertFalse(human.isComputer());
    }

    @Test
    void computerPlayerIsComputer() {
        Player cpu = new ComputerPlayer("CPU", "O");
        assertTrue(cpu.isComputer());
    }
}
