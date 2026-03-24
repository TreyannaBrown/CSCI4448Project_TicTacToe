package tictactoe;

import tictactoe.enums.GameType;
import tictactoe.enums.GridValues;

public class BoardFactory {

    public Board createBoard(int gridSize, GameType type){

        GridValues[][] grid = new GridValues[gridSize][gridSize];
        WinStrategyFactory factory = new WinStrategyFactory();
        CheckWinCondition checkWin = new CheckWinCondition(factory.createWinStrategies(type));
        return new Board(grid, checkWin);
    }
}