package tictactoe.factories;

import tictactoe.WinConditions.FourInARowWin;
import tictactoe.WinConditions.ThreeInARowWin;
import tictactoe.WinConditions.WinCondition;
import tictactoe.enums.GameType;

public class WinConditionFactory {

    public WinCondition createWinCondition(GameType type){
        if((type == GameType.STANDARD) || (type == GameType.PYRAMID)){
            return new ThreeInARowWin();
        }
        else if(type == GameType.CONNECT_FOUR){
            return new FourInARowWin();
        }
        else{
            throw new IllegalArgumentException("Unknown win type: " + type);
        }
    }
}
