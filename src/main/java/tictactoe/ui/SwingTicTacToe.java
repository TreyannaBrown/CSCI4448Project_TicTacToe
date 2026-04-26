package tictactoe.ui;

import tictactoe.BoardCell;
import tictactoe.TicTacToe;
import tictactoe.observers.GameObserver;
import tictactoe.players.ComputerPlayer;
import tictactoe.players.Player;

import javax.swing.*;
import java.awt.*;

public class SwingTicTacToe implements GameObserver {
    private static final int WINDOW_WIDTH = 560;
    private static final int WINDOW_HEIGHT = 620;
    private static final int GRID_GAP = 8;
    private static final int BOARD_PADDING = 15;
    private static final int BUTTON_FONT_SIZE = 48;
    private static final int BUTTON_BORDER_SIZE = 3;
    private static final int HIGHLIGHT_BORDER_SIZE = 8;
    private static final int COMPUTER_MOVE_DELAY = 800;
    private static final int GAME_OVER_DELAY = 1200;
    private static final int SCORE_TALLY_GROUP_SIZE = 5;

    private final TicTacToe game;
    private final JFrame frame;
    private final JPanel boardPanel;
    private final JPanel topPanel;
    private final JLabel statusLabel;
    private final JLabel scoreLabel;
    private final Color backgroundColor;
    private final Color accentColor;
    private final Color buttonColor;
    private final Color textColor;
    private final Runnable returnToMenuAction;

    private JButton[][] buttons;
    private boolean gameOverScreenShown;

    public SwingTicTacToe(TicTacToe game, Color backgroundColor, Color accentColor,
                          Color buttonColor, Color textColor, Runnable returnToMenuAction) {
        this.game = game;
        this.backgroundColor = backgroundColor;
        this.accentColor = accentColor;
        this.buttonColor = buttonColor;
        this.textColor = textColor;
        this.returnToMenuAction = returnToMenuAction;

        frame = new JFrame("Tic Tac Toe");
        boardPanel = new JPanel();
        topPanel = new JPanel(new GridLayout(2, 1));
        statusLabel = new JLabel("", SwingConstants.CENTER);
        scoreLabel = new JLabel("", SwingConstants.CENTER);
        gameOverScreenShown = false;

        game.addObserver(this);

        buildUI();
        update();
        handleComputerTurn();
    }

    private void buildUI() {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        configureTopPanel();
        buildBoardPanel();

        frame.getContentPane().setBackground(backgroundColor);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void configureTopPanel() {
        statusLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        statusLabel.setForeground(accentColor);
        statusLabel.setBackground(backgroundColor);
        statusLabel.setOpaque(true);

        scoreLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        scoreLabel.setForeground(textColor);
        scoreLabel.setBackground(backgroundColor);
        scoreLabel.setOpaque(true);

        topPanel.setBackground(backgroundColor);
        topPanel.add(statusLabel);
        topPanel.add(scoreLabel);
    }

    private void buildBoardPanel() {
        BoardCell[][] grid = game.getBoard().getGrid();
        int rowCount = grid.length;
        int columnCount = grid[0].length;

        buttons = new JButton[rowCount][columnCount];

        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(rowCount, columnCount, GRID_GAP, GRID_GAP));
        boardPanel.setBackground(backgroundColor);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(
                BOARD_PADDING,
                BOARD_PADDING,
                BOARD_PADDING,
                BOARD_PADDING
        ));

        for (int row = 0; row < rowCount; row++) {
            addButtonsForRow(grid, row);
        }
    }

    private void addButtonsForRow(BoardCell[][] grid, int row) {
        for (int col = 0; col < grid[row].length; col++) {
            JButton button = createBoardButton(row, col, grid[row][col]);
            buttons[row][col] = button;
            boardPanel.add(button);
        }
    }

    private JButton createBoardButton(int row, int col, BoardCell cell) {
        JButton button = new JButton("");

        configureBoardButton(button);

        if (!cell.isValid()) {
            configureInvalidButton(button);
            return button;
        }

        button.addActionListener(e -> handleMove(row, col));
        return button;
    }

    private void configureBoardButton(JButton button) {
        button.setFont(new Font("Dialog", Font.BOLD, BUTTON_FONT_SIZE));
        button.setForeground(accentColor);
        button.setBackground(buttonColor);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createLineBorder(accentColor, BUTTON_BORDER_SIZE));
    }

    private void configureInvalidButton(JButton button) {
        button.setEnabled(false);
        button.setVisible(false);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    private void handleMove(int row, int col) {
        if (game.getCurrentPlayer().isComputer()) {
            return;
        }

        boolean moveMade = game.makeMove(row, col);

        if (moveMade) {
            handleComputerTurn();
        }
    }

    private void handleComputerTurn() {
        if (game.isGameOver() || !game.getCurrentPlayer().isComputer()) {
            return;
        }

        updateStatus();

        Timer timer = new Timer(COMPUTER_MOVE_DELAY, e -> makeComputerMove());
        timer.setRepeats(false);
        timer.start();
    }

    private void makeComputerMove() {
        ComputerPlayer computer = (ComputerPlayer) game.getCurrentPlayer();
        Player opponent = getOpponentPlayer();

        int[] move = computer.getMove(
                game.getBoard().getGrid(),
                game.getCurrentPlayer().getSymbol(),
                opponent.getSymbol()
        );

        if (move != null) {
            game.makeMove(move[0], move[1]);
        }

        handleComputerTurn();
    }

    private Player getOpponentPlayer() {
        if (game.getCurrentPlayer() == game.getPlayer1()) {
            return game.getPlayer2();
        }

        return game.getPlayer1();
    }

    @Override
    public void update() {
        updateBoardDisplay();
        updateStatus();
        updateScore();

        if (game.isGameOver() && !gameOverScreenShown) {
            showGameOverAfterHighlight();
        }
    }

    private void updateBoardDisplay() {
        BoardCell[][] grid = game.getBoard().getGrid();

        for (int row = 0; row < grid.length; row++) {
            updateButtonRow(grid, row);
        }
    }

    private void updateButtonRow(BoardCell[][] grid, int row) {
        for (int col = 0; col < grid[row].length; col++) {
            if (grid[row][col].isValid()) {
                updateButton(row, col, grid[row][col]);
            }
        }
    }

    private void updateButton(int row, int col, BoardCell cell) {
        String value = cell.getCellValue();

        buttons[row][col].setText(value == null ? "" : value);
        buttons[row][col].setBackground(buttonColor);
        buttons[row][col].setForeground(accentColor);
        buttons[row][col].setBorder(BorderFactory.createLineBorder(accentColor, BUTTON_BORDER_SIZE));
    }

    private void updateStatus() {
        if (game.isGameOver()) {
            updateGameOverStatus();
            return;
        }

        statusLabel.setText(game.getCurrentPlayer().getName() + "'s turn");
    }

    private void updateGameOverStatus() {
        if (game.getWinner() != null) {
            statusLabel.setText(game.getWinner().getName() + " wins!");
        } else {
            statusLabel.setText("Tie game!");
        }
    }

    private void updateScore() {
        scoreLabel.setText(
                game.getPlayer1().getName() + ": " + makeTallies(game.getPlayer1Wins())
                        + "    " +
                        game.getPlayer2().getName() + ": " + makeTallies(game.getPlayer2Wins())
        );
    }

    private String makeTallies(int count) {
        StringBuilder tallies = new StringBuilder();

        for (int tallyNumber = 1; tallyNumber <= count; tallyNumber++) {
            tallies.append("|");

            if (tallyNumber % SCORE_TALLY_GROUP_SIZE == 0) {
                tallies.append(" ");
            }
        }

        return tallies.toString();
    }

    private void showGameOverAfterHighlight() {
        gameOverScreenShown = true;
        highlightWinningCells();

        frame.revalidate();
        frame.repaint();

        Timer timer = new Timer(GAME_OVER_DELAY, e -> showGameOverScreen());
        timer.setRepeats(false);
        timer.start();
    }

    private void highlightWinningCells() {
        int[][] winningCells = game.getBoard().getWinningCells();

        if (winningCells == null) {
            return;
        }

        for (int[] cell : winningCells) {
            highlightCell(cell);
        }
    }

    private boolean isButtonInBounds(int row, int col) {
        return row >= 0
                && row < buttons.length
                && col >= 0
                && col < buttons[row].length;
    }

    private void showGameOverScreen() {
        JPanel gameOverPanel = new JPanel(new BorderLayout(20, 20));
        gameOverPanel.setBackground(backgroundColor);
        gameOverPanel.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        gameOverPanel.add(createGameOverMessageLabel(), BorderLayout.CENTER);
        gameOverPanel.add(createGameOverButtonPanel(), BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(gameOverPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private JLabel createGameOverMessageLabel() {
        JLabel messageLabel = new JLabel(getGameOverMessage(), SwingConstants.CENTER);
        messageLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        messageLabel.setForeground(accentColor);
        return messageLabel;
    }

    private String getGameOverMessage() {
        if (game.getWinner() != null) {
            return "Game Over! " + game.getWinner().getName() + " wins!";
        }

        return "Game Over! It's a tie!";
    }

    private JPanel createGameOverButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(createReplayButton());
        buttonPanel.add(createMainMenuButton());
        return buttonPanel;
    }

    private JButton createReplayButton() {
        JButton replayButton = createEndButton("Replay");

        replayButton.addActionListener(e -> {
            gameOverScreenShown = false;
            game.resetGame();

            frame.getContentPane().removeAll();
            buildBoardPanel();

            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(boardPanel, BorderLayout.CENTER);

            updateBoardDisplay();
            updateStatus();
            updateScore();

            frame.revalidate();
            frame.repaint();

            handleComputerTurn();
        });

        return replayButton;
    }

    private JButton createMainMenuButton() {
        JButton mainMenuButton = createEndButton("Return to Main Menu");

        mainMenuButton.addActionListener(e -> {
            frame.dispose();

            if (returnToMenuAction != null) {
                returnToMenuAction.run();
            }
        });

        return mainMenuButton;
    }

    private JButton createEndButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Dialog", Font.BOLD, 20));
        button.setForeground(textColor);
        button.setBackground(buttonColor);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createLineBorder(accentColor, BUTTON_BORDER_SIZE));
        return button;
    }
    private void highlightCell(int[] cell) {
        int row = cell[0];
        int col = cell[1];

        if (!isButtonInBounds(row, col)) {
            return;
        }

        buttons[row][col].setBorder(
                BorderFactory.createLineBorder(Color.YELLOW, HIGHLIGHT_BORDER_SIZE)
        );
        buttons[row][col].setBackground(accentColor);
        buttons[row][col].setForeground(backgroundColor);
    }

}