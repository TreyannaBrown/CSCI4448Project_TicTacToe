package tictactoe;

import tictactoe.enums.GameType;
import tictactoe.winStrategies.ColumnLineWin;
import tictactoe.winStrategies.DiagonalWin;
import tictactoe.winStrategies.RowLineWin;
import tictactoe.winStrategies.WinStrategy;

import java.util.ArrayList;
import java.util.List;

public class WinStrategyFactory {

    public List<WinStrategy> createWinStrategies(GameType ruleset){
        List<WinStrategy> strategies = new ArrayList<>();
        if(ruleset.equals(GameType.STANDARD)){
            strategies.add(new ColumnLineWin());
            strategies.add(new DiagonalWin());
            strategies.add(new RowLineWin());
        }
        return strategies;
    }
}
