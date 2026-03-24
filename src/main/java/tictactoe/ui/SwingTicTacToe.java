    package tictactoe.ui;

import tictactoe.TicTacToe;
import javax.swing.*;
import java.awt.*;

public class SwingTicTacToe {

    private TicTacToe game;
    private JFrame frame;
    private JPanel boardPanel;
    private JButton[][] buttons;
    private JLabel statusLabel;

    public SwingTicTacToe(TicTacToe game) {
        this.game = game;
        this.buttons = new JButton[3][3];
        frame = new JFrame("Tic Tac Toe");
        boardPanel = new JPanel();
        statusLabel = new JLabel(game.getCurrentPlayer().getName() + "'s turn");

        buildUI();
    }

    private void buildUI() {
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        boardPanel.setLayout(new GridLayout(3, 3) );

        for (int row = 0; row < 3; row++ ) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 30));

                int currentRow = row;
                int currentCol = col;

                button.addActionListener(e -> handleMove(currentRow, currentCol));

                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        frame.add(statusLabel , BorderLayout.NORTH);
        frame.add( boardPanel,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void handleMove(int row, int col) {
    }
}