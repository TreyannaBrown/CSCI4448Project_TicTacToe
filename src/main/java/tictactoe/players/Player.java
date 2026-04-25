package tictactoe.players;

public abstract class Player {
    protected String name;
    protected String symbol;

    public Player(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract boolean isComputer();
}