package tictactoe.players;

public class ComputerPlayer extends Player {
    public ComputerPlayer(String name, String symbol){
        super(name, symbol);
    }

    @Override
    public boolean isComputer(){
        return true;
    };

    // do move. first available spot ? random ?
}
