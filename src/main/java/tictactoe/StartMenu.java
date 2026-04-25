package tictactoe;

import tictactoe.factories.GameFactory;
import tictactoe.strategies.EasyMoveStrategy;
import tictactoe.strategies.HardMoveStrategy;
import tictactoe.strategies.MoveStrategy;
import tictactoe.ui.SwingTicTacToe;

import javax.swing.*;
import java.awt.*;

public class StartMenu {
    private JFrame frame;
    private Theme selectedTheme;
    private MoveStrategy selectedStrategy;

    private String player1Name = "Player 1";
    private String player2Name = "Player 2";

    private final Theme menuTheme = new Theme(
            "Main Menu",
            new Color(20, 20, 28),
            new Color(230, 230, 240),
            new Color(70, 70, 90),
            Color.WHITE,
            new String[]{"X", "O"}
    );

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartMenu::new);
    }

    public StartMenu() {
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(560, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        showThemeMenu();

        frame.setVisible(true);
    }

    private void showThemeMenu() {
        Theme pinkTheme = new Theme(
                "Pink Mode",
                new Color(255, 220, 238),
                new Color(255, 80, 165),
                new Color(255, 140, 200),
                new Color(80, 20, 50),
                new String[]{"♡", "☆"}
        );

        Theme darkTheme = new Theme(
                "Dark Mode",
                new Color(20, 20, 28),
                new Color(230, 230, 240),
                new Color(70, 70, 90),
                Color.WHITE,
                new String[]{"X", "O"}
        );

        Theme neonTheme = new Theme(
                "Neon Mode",
                new Color(5, 5, 25),
                new Color(0, 255, 220),
                new Color(120, 40, 255),
                Color.WHITE,
                new String[]{"△", "◇"}
        );

        JPanel panel = createBasePanel(menuTheme);

        JLabel title = createTitle("TIC TAC TOE", menuTheme, 46);
        JLabel subtitle = createSubtitle("Choose your theme", menuTheme);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 8));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);

        JPanel buttonPanel = createButtonPanel(3);

        buttonPanel.add(createThemeButton("Pink Mode", pinkTheme));
        buttonPanel.add(createThemeButton("Dark Mode", darkTheme));
        buttonPanel.add(createThemeButton("Neon Mode", neonTheme));

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        setPanel(panel);
    }

    private void showGameMenu() {
        JPanel panel = createBasePanel(selectedTheme);

        JLabel title = createTitle("CHOOSE GAME MODE", selectedTheme, 34);
        JLabel subtitle = createSubtitle("Selected Theme: " + selectedTheme.name, selectedTheme);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 8));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);

        JPanel buttonPanel = createButtonPanel(4);

        buttonPanel.add(createMenuButton("Human vs Human", () -> {
            player1Name = askForName("Enter Player 1 name:", "Player 1");
            player2Name = askForName("Enter Player 2 name:", "Player 2");

            String icon1 = selectedTheme.icons[0];
            String icon2 = selectedTheme.icons[1];

            startGame(GameFactory.createHumanVsHuman(player1Name, player2Name, icon1, icon2));
        }));

        buttonPanel.add(createMenuButton("Human vs Computer", () -> {
            player1Name = askForName("Enter your name:", "Player 1");
            showDifficultyMenu();
        }));

        buttonPanel.add(createMenuButton("Computer vs Computer", () -> {
            String icon1 = selectedTheme.icons[0];
            String icon2 = selectedTheme.icons[1];

            startGame(GameFactory.createComputerVsComputer(icon1, icon2));
        }));

        buttonPanel.add(createMenuButton("Back to Themes", this::showThemeMenu));

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        setPanel(panel);
    }

    private void showDifficultyMenu() {
        JPanel panel = createBasePanel(selectedTheme);

        JLabel title = createTitle("CHOOSE DIFFICULTY", selectedTheme, 34);
        JLabel subtitle = createSubtitle("Then choose your icon", selectedTheme);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 8));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);

        JPanel buttonPanel = createButtonPanel(3);

        buttonPanel.add(createMenuButton("Easy", () -> {
            selectedStrategy = new EasyMoveStrategy();
            showIconMenu();
        }));

        buttonPanel.add(createMenuButton("Hard", () -> {
            selectedStrategy = new HardMoveStrategy();
            showIconMenu();
        }));

        buttonPanel.add(createMenuButton("Back to Game Modes", this::showGameMenu));

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        setPanel(panel);
    }

    private void showIconMenu() {
        JPanel panel = createBasePanel(selectedTheme);

        JLabel title = createTitle("CHOOSE YOUR ICON", selectedTheme, 34);
        JLabel subtitle = createSubtitle("Computer gets the other icon", selectedTheme);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 8));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);

        JPanel buttonPanel = createButtonPanel(3);

        String icon1 = selectedTheme.icons[0];
        String icon2 = selectedTheme.icons[1];

        buttonPanel.add(createMenuButton("Play as " + icon1, () -> {
            startGame(GameFactory.createHumanVsComputer(player1Name, icon1, icon2, selectedStrategy));
        }));

        buttonPanel.add(createMenuButton("Play as " + icon2, () -> {
            startGame(GameFactory.createHumanVsComputer(player1Name, icon2, icon1, selectedStrategy));
        }));

        buttonPanel.add(createMenuButton("Back to Difficulty", this::showDifficultyMenu));

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        setPanel(panel);
    }

    private String askForName(String prompt, String defaultName) {
        JDialog dialog = new JDialog(frame, "Enter Name", true);
        dialog.setSize(320, 190);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setLocationRelativeTo(frame);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(selectedTheme.background);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(prompt, SwingConstants.CENTER);
        label.setForeground(selectedTheme.accent);
        label.setFont(new Font("Dialog", Font.BOLD, 16));

        JTextField textField = new JTextField(defaultName);
        textField.setFont(new Font("Dialog", Font.PLAIN, 16));

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Dialog", Font.BOLD, 16));
        okButton.setBackground(selectedTheme.button);
        okButton.setForeground(selectedTheme.text);
        okButton.setFocusPainted(false);

        final String[] result = {defaultName};

        okButton.addActionListener(e -> {
            String typedName = textField.getText().trim();

            if (!typedName.isEmpty()) {
                result[0] = typedName;
            }

            dialog.dispose();
        });

        textField.addActionListener(e -> okButton.doClick());

        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);

        return result[0];
    }

    private JButton createThemeButton(String text, Theme theme) {
        JButton button = styledButton(text, menuTheme);

        button.addActionListener(e -> {
            selectedTheme = theme;
            showGameMenu();
        });

        return button;
    }

    private JButton createMenuButton(String text, Runnable action) {
        JButton button = styledButton(text, selectedTheme);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void startGame(TicTacToe game) {
        new SwingTicTacToe(
                game,
                selectedTheme.background,
                selectedTheme.accent,
                selectedTheme.button,
                selectedTheme.text,
                () -> new StartMenu()
        );

        frame.dispose();
    }

    private JPanel createBasePanel(Theme theme) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(theme.background);
        panel.setBorder(BorderFactory.createEmptyBorder(45, 50, 45, 50));
        return panel;
    }

    private JPanel createButtonPanel(int rows) {
        JPanel buttonPanel = new JPanel(new GridLayout(rows, 1, 0, 24));
        buttonPanel.setOpaque(false);
        return buttonPanel;
    }

    private JLabel createTitle(String text, Theme theme, int size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Dialog", Font.BOLD, size));
        label.setForeground(theme.accent);
        return label;
    }

    private JLabel createSubtitle(String text, Theme theme) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Dialog", Font.PLAIN, 18));
        label.setForeground(theme.text);
        return label;
    }

    private JButton styledButton(String text, Theme theme) {
        JButton button = new JButton(text);

        button.setFont(new Font("Dialog", Font.BOLD, 22));
        button.setForeground(theme.text);
        button.setBackground(theme.button);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(theme.accent);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(theme.button);
            }
        });

        return button;
    }

    private void setPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private static class Theme {
        String name;
        Color background;
        Color accent;
        Color button;
        Color text;
        String[] icons;

        Theme(String name, Color background, Color accent, Color button, Color text, String[] icons) {
            this.name = name;
            this.background = background;
            this.accent = accent;
            this.button = button;
            this.text = text;
            this.icons = icons;
        }
    }
}