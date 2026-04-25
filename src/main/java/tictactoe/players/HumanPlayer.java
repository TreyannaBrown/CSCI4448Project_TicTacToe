package tictactoe.players;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, String symbol) {
        super(name, symbol);
    }

    @Override
    public boolean isComputer() {
        return false;
    }
}