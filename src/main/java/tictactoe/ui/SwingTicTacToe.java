package tictactoe.ui;

import tictactoe.TicTacToe;
import tictactoe.observers.GameObserver;
import tictactoe.players.ComputerPlayer;
import tictactoe.players.Player;

import javax.swing.*;
import java.awt.*;

public class SwingTicTacToe implements GameObserver {

    private TicTacToe game;
    private JFrame frame;
    private JPanel boardPanel;
    private JPanel topPanel;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private JLabel scoreLabel;

    private Color backgroundColor;
    private Color accentColor;
    private Color buttonColor;
    private Color textColor;

    private Runnable returnToMenuAction;

    public SwingTicTacToe(TicTacToe game, Color backgroundColor, Color accentColor,
                          Color buttonColor, Color textColor, Runnable returnToMenuAction) {
        this.game = game;
        this.buttons = new JButton[3][3];

        this.backgroundColor = backgroundColor;
        this.accentColor = accentColor;
        this.buttonColor = buttonColor;
        this.textColor = textColor;
        this.returnToMenuAction = returnToMenuAction;

        game.addObserver(this);

        frame = new JFrame("Tic Tac Toe");
        boardPanel = new JPanel();
        statusLabel = new JLabel(game.getCurrentPlayer().getName() + "'s turn", SwingConstants.CENTER);
        scoreLabel = new JLabel("", SwingConstants.CENTER);

        buildUI();
        handleComputerTurn();
    }

    private void buildUI() {
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        statusLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        statusLabel.setForeground(accentColor);
        statusLabel.setBackground(backgroundColor);
        statusLabel.setOpaque(true);

        scoreLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        scoreLabel.setForeground(textColor);
        scoreLabel.setBackground(backgroundColor);
        scoreLabel.setOpaque(true);
        updateScore();

        topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(backgroundColor);
        topPanel.add(statusLabel);
        topPanel.add(scoreLabel);

        boardPanel.setLayout(new GridLayout(3, 3, 8, 8));
        boardPanel.setBackground(backgroundColor);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton("");
                button.setFont(new Font("Dialog", Font.BOLD, 64));
                button.setForeground(accentColor);
                button.setBackground(buttonColor);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createLineBorder(accentColor, 3));

                int currentRow = row;
                int currentCol = col;

                button.addActionListener(e -> handleMove(currentRow, currentCol));

                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        frame.getContentPane().setBackground(backgroundColor);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        if (!game.isGameOver() && game.getCurrentPlayer().isComputer()) {
            updateStatus();

            Timer timer = new Timer(800, e -> {
                ComputerPlayer computer = (ComputerPlayer) game.getCurrentPlayer();
                int[] move = computer.getMove(game.getBoard().getGrid());

                if (move != null) {
                    game.makeMove(move[0], move[1]);
                }

                handleComputerTurn();
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    @Override
    public void update() {
        refreshBoard();
        updateStatus();
        updateScore();

        if (game.isGameOver()) {
            showGameOverScreen();
        }
    }

    private void refreshBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                String value = game.getBoard().getValue(row, col);
                buttons[row][col].setText(value == null ? "" : value);
            }
        }
    }

    private void updateStatus() {
        if (game.isGameOver()) {
            if (game.getWinner() != null) {
                statusLabel.setText(game.getWinner().getName() + " wins!");
            } else {
                statusLabel.setText("Tie game!");
            }
        } else {
            Player currentPlayer = game.getCurrentPlayer();
            statusLabel.setText(currentPlayer.getName() + "'s turn");
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

        for (int i = 1; i <= count; i++) {
            tallies.append("|");

            if (i % 5 == 0) {
                tallies.append(" ");
            }
        }

        return tallies.toString();
    }

    private void showGameOverScreen() {
        JPanel gameOverPanel = new JPanel(new BorderLayout(20, 20));
        gameOverPanel.setBackground(backgroundColor);
        gameOverPanel.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        String message;

        if (game.getWinner() != null) {
            message = "Game Over! " + game.getWinner().getName() + " wins!";
        } else {
            message = "Game Over! It's a tie!";
        }

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        messageLabel.setForeground(accentColor);

        JButton replayButton = createEndButton("Replay");
        replayButton.addActionListener(e -> {
            game.resetGame();

            frame.getContentPane().removeAll();
            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(boardPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            refreshBoard();
            updateStatus();
            updateScore();
            handleComputerTurn();
        });

        JButton mainMenuButton = createEndButton("Return to Main Menu");
        mainMenuButton.addActionListener(e -> {
            frame.dispose();

            if (returnToMenuAction != null) {
                returnToMenuAction.run();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(replayButton);
        buttonPanel.add(mainMenuButton);

        gameOverPanel.add(messageLabel, BorderLayout.CENTER);
        gameOverPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(gameOverPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private JButton createEndButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Dialog", Font.BOLD, 20));
        button.setForeground(textColor);
        button.setBackground(buttonColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(accentColor, 3));
        return button;
    }
}