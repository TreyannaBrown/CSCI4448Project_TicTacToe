package tictactoe.factories;

import tictactoe.TicTacToe;
import tictactoe.enums.GameType;
import tictactoe.players.ComputerPlayer;
import tictactoe.players.HumanPlayer;
import tictactoe.players.Player;
import tictactoe.strategies.MoveStrategy;
import tictactoe.strategies.HardMoveStrategy;

public class GameFactory {

    public static TicTacToe createHumanVsHuman(String player1Name, String player2Name, String icon1, String icon2, GameType type) {
        Player player1 = new HumanPlayer(player1Name, icon1);
        Player player2 = new HumanPlayer(player2Name, icon2);

        return new TicTacToe(player1, player2,type);
    }

    public static TicTacToe createHumanVsComputer(String playerName, String humanIcon, String computerIcon, MoveStrategy strategy, GameType type) {
        Player player1 = new HumanPlayer(playerName, humanIcon);
        Player player2 = new ComputerPlayer("Computer", computerIcon, strategy);

        return new TicTacToe(player1, player2, type);
    }

    public static TicTacToe createComputerVsComputer(String icon1, String icon2, GameType type) {
        Player player1 = new ComputerPlayer("Computer 1", icon1, new HardMoveStrategy());
        Player player2 = new ComputerPlayer("Computer 2", icon2, new HardMoveStrategy());

        return new TicTacToe(player1, player2, type);
    }
}