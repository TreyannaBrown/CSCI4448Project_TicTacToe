package tictactoe;


import tictactoe.enums.GridValues;
import tictactoe.winStrategies.WinStrategy;

import java.util.List;

public class CheckWinCondition {
    private final List<WinStrategy> winStrategies;

    public CheckWinCondition(List<WinStrategy> winStrategies){
        this.winStrategies = winStrategies;
    }

    public boolean CheckIfWin(GridValues[][] grid){
        for(WinStrategy strategy : winStrategies){
            if(strategy.checkWin(grid)){
                return true;
            }
        }
        return false;
    }
}