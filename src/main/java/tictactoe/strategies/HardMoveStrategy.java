package tictactoe.strategies;

import tictactoe.BoardCell;

public class HardMoveStrategy implements MoveStrategy {

    @Override
    public int[] chooseMove(BoardCell[][] board, String computerSymbol, String opponentSymbol) {
        int[] winningMove = findWinningMove(board, computerSymbol);

        if (winningMove != null) {
            return winningMove;
        }

        int[] blockingMove = findWinningMove(board, opponentSymbol);

        if (blockingMove != null) {
            return blockingMove;
        }

        return new EasyMoveStrategy().chooseMove(board, computerSymbol, opponentSymbol);
    }

    private int[] findWinningMove(BoardCell[][] board, String symbol) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (isOpen(board, row, col) && wouldWin(board, row, col, symbol)) {
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    private boolean wouldWin(BoardCell[][] board, int row, int col, String symbol) {
        BoardCell testCell = board[row][col];

        board[row][col] = new BoardCell(true);
        board[row][col].setCellValue(symbol);

        boolean wins = hasWin(board, symbol);

        board[row][col] = testCell;

        return wins;
    }

    private boolean hasWin(BoardCell[][] board, String symbol) {
        return hasRowWin(board, symbol)
                || hasColumnWin(board, symbol)
                || hasDiagonalWin(board, symbol);
    }

    private boolean hasRowWin(BoardCell[][] board, String symbol) {
        for (BoardCell[] row : board) {
            if (allCellsMatch(row, symbol)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasColumnWin(BoardCell[][] board, String symbol) {
        int columnCount = board[0].length;

        for (int col = 0; col < columnCount; col++) {
            if (columnMatches(board, col, symbol)) {
                return true;
            }
        }

        return false;
    }

    private boolean columnMatches(BoardCell[][] board, int col, String symbol) {
        for (BoardCell[] row : board) {
            if (!isValidMatch(row[col], symbol)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasDiagonalWin(BoardCell[][] board, String symbol) {
        if (board.length != board[0].length) {
            return false;
        }

        return leftDiagonalMatches(board, symbol) || rightDiagonalMatches(board, symbol);
    }

    private boolean leftDiagonalMatches(BoardCell[][] board, String symbol) {
        for (int index = 0; index < board.length; index++) {
            if (!isValidMatch(board[index][index], symbol)) {
                return false;
            }
        }

        return true;
    }

    private boolean rightDiagonalMatches(BoardCell[][] board, String symbol) {
        int lastCol = board.length - 1;

        for (int row = 0; row < board.length; row++) {
            if (!isValidMatch(board[row][lastCol - row], symbol)) {
                return false;
            }
        }

        return true;
    }

    private boolean allCellsMatch(BoardCell[] row, String symbol) {
        for (BoardCell cell : row) {
            if (!isValidMatch(cell, symbol)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidMatch(BoardCell cell, String symbol) {
        return cell.isValid() && symbol.equals(cell.getCellValue());
    }

    private boolean isOpen(BoardCell[][] board, int row, int col) {
        return board[row][col].isValid() && board[row][col].isEmpty();
    }
}