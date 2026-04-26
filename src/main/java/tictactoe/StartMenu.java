package tictactoe;

import tictactoe.factories.GameFactory;
import tictactoe.strategies.EasyMoveStrategy;
import tictactoe.strategies.HardMoveStrategy;
import tictactoe.strategies.MoveStrategy;
import tictactoe.ui.SwingTicTacToe;

import javax.swing.*;
import java.awt.*;

public class StartMenu {
    private static final int WINDOW_WIDTH = 560;
    private static final int WINDOW_HEIGHT = 680;
    private static final int NAME_DIALOG_WIDTH = 320;
    private static final int NAME_DIALOG_HEIGHT = 190;

    private static final int TITLE_FONT_SIZE = 46;
    private static final int PAGE_TITLE_FONT_SIZE = 34;
    private static final int SUBTITLE_FONT_SIZE = 18;
    private static final int BUTTON_FONT_SIZE = 22;
    private static final int NAME_LABEL_FONT_SIZE = 16;
    private static final int DEFAULT_PADDING = 45;
    private static final int NAME_DIALOG_PADDING = 20;

    private static final String GAME_TITLE = "Tic Tac Toe";
    private static final String TITLE_TEXT = "TIC TAC TOE";
    private static final String DEFAULT_PLAYER_ONE_NAME = "Player 1";
    private static final String DEFAULT_PLAYER_TWO_NAME = "Player 2";
    private static final String COMPUTER_NAME = "Computer";

    private final JFrame frame;
    private final Theme menuTheme;

    private Theme selectedTheme;
    private MoveStrategy selectedStrategy;
    private String player1Name;
    private String player2Name;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartMenu::new);
    }

    public StartMenu() {
        frame = new JFrame(GAME_TITLE);
        menuTheme = createMainMenuTheme();
        player1Name = DEFAULT_PLAYER_ONE_NAME;
        player2Name = DEFAULT_PLAYER_TWO_NAME;

        configureFrame();
        showThemeMenu();

        frame.setVisible(true);
    }

    private void configureFrame() {
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private void showThemeMenu() {
        JPanel panel = createBasePanel(menuTheme);
        JPanel titlePanel = createTitlePanel(TITLE_TEXT, "Choose your theme", menuTheme, TITLE_FONT_SIZE);
        JPanel buttonPanel = createButtonPanel(ThemeCatalog.values().length);

        for (Theme theme : ThemeCatalog.values()) {
            buttonPanel.add(createThemeButton(theme.name, theme));
        }

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        setPanel(panel);
    }

    private void showGameMenu() {
        JPanel panel = createBasePanel(selectedTheme);
        JPanel titlePanel = createTitlePanel("CHOOSE GAME MODE",
                "Selected Theme: " + selectedTheme.name, selectedTheme, PAGE_TITLE_FONT_SIZE);
        JPanel buttonPanel = createButtonPanel(4);

        buttonPanel.add(createMenuButton("Human vs Human", this::startHumanVsHumanGame));
        buttonPanel.add(createMenuButton("Human vs Computer", this::showHumanVsComputerSetup));
        buttonPanel.add(createMenuButton("Computer vs Computer", this::startComputerVsComputerGame));
        buttonPanel.add(createMenuButton("Back to Themes", this::showThemeMenu));

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        setPanel(panel);
    }

    private void showHumanVsComputerSetup() {
        player1Name = askForName("Enter your name:", DEFAULT_PLAYER_ONE_NAME);
        showDifficultyMenu();
    }

    private void showDifficultyMenu() {
        JPanel panel = createBasePanel(selectedTheme);
        JPanel titlePanel = createTitlePanel("CHOOSE DIFFICULTY",
                "Then choose your icon", selectedTheme, PAGE_TITLE_FONT_SIZE);
        JPanel buttonPanel = createButtonPanel(3);

        buttonPanel.add(createMenuButton("Easy", () -> chooseStrategyAndShowIcons(new EasyMoveStrategy())));
        buttonPanel.add(createMenuButton("Hard", () -> chooseStrategyAndShowIcons(new HardMoveStrategy())));
        buttonPanel.add(createMenuButton("Back to Game Modes", this::showGameMenu));

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        setPanel(panel);
    }

    private void chooseStrategyAndShowIcons(MoveStrategy strategy) {
        selectedStrategy = strategy;
        showIconMenu();
    }

    private void showIconMenu() {
        JPanel panel = createBasePanel(selectedTheme);
        JPanel titlePanel = createTitlePanel("CHOOSE YOUR ICON",
                COMPUTER_NAME + " gets the other icon", selectedTheme, PAGE_TITLE_FONT_SIZE);
        JPanel buttonPanel = createButtonPanel(3);

        String firstIcon = selectedTheme.icons[0];
        String secondIcon = selectedTheme.icons[1];

        buttonPanel.add(createMenuButton("Play as " + firstIcon,
                () -> startHumanVsComputerGame(firstIcon, secondIcon)));
        buttonPanel.add(createMenuButton("Play as " + secondIcon,
                () -> startHumanVsComputerGame(secondIcon, firstIcon)));
        buttonPanel.add(createMenuButton("Back to Difficulty", this::showDifficultyMenu));

        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        setPanel(panel);
    }

    private void startHumanVsHumanGame() {
        player1Name = askForName("Enter Player 1 name:", DEFAULT_PLAYER_ONE_NAME);
        player2Name = askForName("Enter Player 2 name:", DEFAULT_PLAYER_TWO_NAME);

        startGame(GameFactory.createHumanVsHuman(
                player1Name,
                player2Name,
                selectedTheme.icons[0],
                selectedTheme.icons[1]
        ));
    }

    private void startHumanVsComputerGame(String humanIcon, String computerIcon) {
        startGame(GameFactory.createHumanVsComputer(
                player1Name,
                humanIcon,
                computerIcon,
                selectedStrategy
        ));
    }

    private void startComputerVsComputerGame() {
        startGame(GameFactory.createComputerVsComputer(
                selectedTheme.icons[0],
                selectedTheme.icons[1]
        ));
    }

    private String askForName(String prompt, String defaultName) {
        JDialog dialog = createNameDialog();
        JPanel panel = createNameDialogPanel();

        JLabel label = createNamePromptLabel(prompt);
        JTextField textField = createNameTextField(defaultName);
        JButton okButton = createNameOkButton();

        final String[] result = {defaultName};

        okButton.addActionListener(event -> {
            result[0] = getEnteredName(textField, defaultName);
            dialog.dispose();
        });

        textField.addActionListener(event -> okButton.doClick());

        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);

        return result[0];
    }

    private JDialog createNameDialog() {
        JDialog dialog = new JDialog(frame, "Enter Name", true);
        dialog.setSize(NAME_DIALOG_WIDTH, NAME_DIALOG_HEIGHT);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setLocationRelativeTo(frame);
        return dialog;
    }

    private JPanel createNameDialogPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(selectedTheme.background);
        panel.setBorder(BorderFactory.createEmptyBorder(
                NAME_DIALOG_PADDING,
                NAME_DIALOG_PADDING,
                NAME_DIALOG_PADDING,
                NAME_DIALOG_PADDING
        ));
        return panel;
    }

    private JLabel createNamePromptLabel(String prompt) {
        JLabel label = new JLabel(prompt, SwingConstants.CENTER);
        label.setForeground(selectedTheme.accent);
        label.setFont(new Font("Dialog", Font.BOLD, NAME_LABEL_FONT_SIZE));
        return label;
    }

    private JTextField createNameTextField(String defaultName) {
        JTextField textField = new JTextField(defaultName);
        textField.setFont(new Font("Dialog", Font.PLAIN, NAME_LABEL_FONT_SIZE));
        return textField;
    }

    private JButton createNameOkButton() {
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Dialog", Font.BOLD, NAME_LABEL_FONT_SIZE));
        okButton.setBackground(selectedTheme.button);
        okButton.setForeground(selectedTheme.text);
        okButton.setFocusPainted(false);
        return okButton;
    }

    private String getEnteredName(JTextField textField, String defaultName) {
        String typedName = textField.getText().trim();

        if (typedName.isEmpty()) {
            return defaultName;
        }

        return typedName;
    }

    private JButton createThemeButton(String text, Theme theme) {
        JButton button = styledButton(text, menuTheme);

        button.addActionListener(event -> {
            selectedTheme = theme;
            showGameMenu();
        });

        return button;
    }

    private JButton createMenuButton(String text, Runnable action) {
        JButton button = styledButton(text, selectedTheme);
        button.addActionListener(event -> action.run());
        return button;
    }

    private void startGame(TicTacToe game) {
        new SwingTicTacToe(
                game,
                selectedTheme.background,
                selectedTheme.accent,
                selectedTheme.button,
                selectedTheme.text,
                StartMenu::new
        );

        frame.dispose();
    }

    private JPanel createBasePanel(Theme theme) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(theme.background);
        panel.setBorder(BorderFactory.createEmptyBorder(
                DEFAULT_PADDING,
                DEFAULT_PADDING,
                DEFAULT_PADDING,
                DEFAULT_PADDING
        ));
        return panel;
    }

    private JPanel createTitlePanel(String titleText, String subtitleText, Theme theme, int titleSize) {
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 8));
        titlePanel.setOpaque(false);
        titlePanel.add(createTitle(titleText, theme, titleSize));
        titlePanel.add(createSubtitle(subtitleText, theme));
        return titlePanel;
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
        label.setFont(new Font("Dialog", Font.PLAIN, SUBTITLE_FONT_SIZE));
        label.setForeground(theme.text);
        return label;
    }

    private JButton styledButton(String text, Theme theme) {
        JButton button = new JButton(text);

        button.setFont(new Font("Dialog", Font.BOLD, BUTTON_FONT_SIZE));
        button.setForeground(theme.text);
        button.setBackground(theme.button);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);

        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(theme.accent, 3));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addHoverEffect(button, theme);

        return button;
    }

    private void addHoverEffect(JButton button, Theme theme) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent event) {
                button.setBackground(theme.accent);
            }

            public void mouseExited(java.awt.event.MouseEvent event) {
                button.setBackground(theme.button);
            }
        });
    }

    private void setPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private static Theme createMainMenuTheme() {
        return new Theme(
                "Main Menu",
                new Color(20, 20, 28),
                new Color(230, 230, 240),
                new Color(70, 70, 90),
                Color.WHITE,
                new String[]{"X", "O"}
        );
    }

    private static class ThemeCatalog {
        private static final Theme PINK = new Theme(
                "Pink Mode",
                new Color(255, 220, 238),
                new Color(255, 80, 165),
                new Color(255, 140, 200),
                new Color(80, 20, 50),
                new String[]{"♡", "☆"}
        );

        private static final Theme DARK = new Theme(
                "Dark Mode",
                new Color(20, 20, 28),
                new Color(230, 230, 240),
                new Color(70, 70, 90),
                Color.WHITE,
                new String[]{"X", "O"}
        );

        private static final Theme NEON = new Theme(
                "Neon Mode",
                new Color(5, 5, 25),
                new Color(0, 255, 220),
                new Color(120, 40, 255),
                Color.WHITE,
                new String[]{"△", "◇"}
        );

        private static Theme[] values() {
            return new Theme[]{PINK, DARK, NEON};
        }
    }

    private static class Theme {
        private final String name;
        private final Color background;
        private final Color accent;
        private final Color button;
        private final Color text;
        private final String[] icons;

        private Theme(String name, Color background, Color accent, Color button, Color text, String[] icons) {
            this.name = name;
            this.background = background;
            this.accent = accent;
            this.button = button;
            this.text = text;
            this.icons = icons;
        }
    }
}