package rostov.minesweeper.gui;

import rostov.minesweeper.presenter.Presenter;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class DesktopView implements View {
    private Presenter presenter;
    private JFrame frame;

    private JButton[][] cells;

    private ImageIcon minesweeperIcon;

    private ImageIcon mineIcon;
    private ImageIcon highlightedMineIcon;
    private ImageIcon flagIcon;

    private ImageIcon oneIcon;
    private ImageIcon twoIcon;
    private ImageIcon threeIcon;
    private ImageIcon fourIcon;
    private ImageIcon fiveIcon;
    private ImageIcon sixIcon;
    private ImageIcon sevenIcon;
    private ImageIcon eightIcon;

    private int rowsAmount = 9;
    private int columnsAmount = 9;
    private int minesAmount = 10;

    private int remainingFlags;

    private String difficulty = "beginner";

    private Timer timer;
    private JLabel timerLabel;
    private int gameTime = 1;

    private final JLabel remainingFlagsLabel = new JLabel();
    private JPanel minesweeperBoard = new JPanel();
    private final GridBagConstraints gamePanelConstraints = new GridBagConstraints();

    private MouseListener cellClickedListener;
    private MouseListener cellFlaggedListener;

    private boolean isStarted;

    public void start() {
        Objects.requireNonNull(presenter, () -> {
            JOptionPane.showMessageDialog(
                    null,
                    "Presenter must not be null.",
                    "Presenter error",
                    JOptionPane.ERROR_MESSAGE
            );
            return "Presenter must not be null.";
        });

        if (isStarted) {
            showError("Start method was already called.");
        }

        isStarted = true;

        SwingUtilities.invokeLater(() -> {
            minesweeperIcon = setIconPath("/rostov/minesweeper/resources/minesweeper.png");
            mineIcon = setIconPath("/rostov/minesweeper/resources/mine.png");
            highlightedMineIcon = setIconPath("/rostov/minesweeper/resources/highlightedMine.png");
            flagIcon = setIconPath("/rostov/minesweeper/resources/flag.png");
            oneIcon = setIconPath("/rostov/minesweeper/resources/1.png");
            twoIcon = setIconPath("/rostov/minesweeper/resources/2.png");
            threeIcon = setIconPath("/rostov/minesweeper/resources/3.png");
            fourIcon = setIconPath("/rostov/minesweeper/resources/4.png");
            fiveIcon = setIconPath("/rostov/minesweeper/resources/5.png");
            sixIcon = setIconPath("/rostov/minesweeper/resources/6.png");
            sevenIcon = setIconPath("/rostov/minesweeper/resources/7.png");
            eightIcon = setIconPath("/rostov/minesweeper/resources/8.png");

            frame = new JFrame("Minesweeper");
            frame.setIconImage(minesweeperIcon.getImage());
            frame.setLocationRelativeTo(null);
            frame.setLayout(new GridBagLayout());
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            JLabel rowsLabel = new JLabel("Input amount of rows:");
            rowsLabel.setVisible(false);

            JTextField rowsTextField = new JTextField(2);
            rowsTextField.setMinimumSize(new Dimension(50, 20));
            rowsTextField.setText(String.valueOf(rowsAmount));
            rowsTextField.setVisible(false);

            ((AbstractDocument) rowsTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                        throws BadLocationException {
                    if (text.matches("\\d+")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });

            JLabel columnsLabel = new JLabel("Input amount of columns:");
            columnsLabel.setVisible(false);

            JTextField columnsTextField = new JTextField(2);
            columnsTextField.setText(String.valueOf(columnsAmount));
            columnsTextField.setMinimumSize(new Dimension(50, 20));
            columnsTextField.setVisible(false);

            ((AbstractDocument) columnsTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                        throws BadLocationException {
                    if (text.matches("\\d+")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });

            JLabel minesLabel = new JLabel("Input amount of mines:");
            minesLabel.setVisible(false);

            JTextField minesTextField = new JTextField(2);
            minesTextField.setMinimumSize(new Dimension(50, 20));
            minesTextField.setText(String.valueOf(minesAmount));
            minesTextField.setVisible(false);

            ((AbstractDocument) minesTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                        throws BadLocationException {
                    if (text.matches("\\d+")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });

            JLabel flagsCountInfoLabel = new JLabel("Flags remaining: ");

            remainingFlagsLabel.setPreferredSize(new Dimension(50, 50));

            GridBagConstraints rowLabelConstraints = new GridBagConstraints();
            rowLabelConstraints.weightx = 1.0;

            GridBagConstraints rowTextFieldConstraints = new GridBagConstraints();
            rowTextFieldConstraints.weightx = 1.0;

            GridBagConstraints columnLabelConstraints = new GridBagConstraints();
            columnLabelConstraints.weightx = 1.0;

            GridBagConstraints columnTextFieldConstraints = new GridBagConstraints();
            columnTextFieldConstraints.weightx = 1.0;

            GridBagConstraints minesLabelConstraints = new GridBagConstraints();
            minesLabelConstraints.weightx = 1.0;

            GridBagConstraints minesTextFieldConstraints = new GridBagConstraints();
            minesTextFieldConstraints.weightx = 1.0;
            minesTextFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;

            JPanel optionsPanel = new JPanel(new GridBagLayout());
            optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));

            optionsPanel.add(rowsLabel, rowLabelConstraints);
            optionsPanel.add(rowsTextField, rowTextFieldConstraints);
            optionsPanel.add(columnsLabel, columnLabelConstraints);
            optionsPanel.add(columnsTextField, columnTextFieldConstraints);
            optionsPanel.add(minesLabel, minesLabelConstraints);
            optionsPanel.add(minesTextField, minesTextFieldConstraints);

            optionsPanel.add(flagsCountInfoLabel);
            optionsPanel.add(remainingFlagsLabel);

            GridBagConstraints optionsPanelConstraints = new GridBagConstraints();

            optionsPanelConstraints.gridx = 0;
            optionsPanelConstraints.gridy = 0;
            optionsPanelConstraints.weightx = 0.0;
            optionsPanelConstraints.weighty = 0.0;
            optionsPanelConstraints.gridwidth = GridBagConstraints.REMAINDER;
            optionsPanelConstraints.fill = GridBagConstraints.NONE;
            optionsPanelConstraints.anchor = GridBagConstraints.NORTH;

            frame.add(optionsPanel, optionsPanelConstraints);

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

            GridBagConstraints separatorConstraints = new GridBagConstraints();
            separatorConstraints.gridx = 0;
            separatorConstraints.gridy = 1;
            separatorConstraints.gridwidth = GridBagConstraints.REMAINDER;
            separatorConstraints.fill = GridBagConstraints.HORIZONTAL;
            separatorConstraints.weightx = 1.0;
            separatorConstraints.insets = new Insets(10, 0, 10, 0);

            frame.add(separator, separatorConstraints);

            cellClickedListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (timerLabel.getText().equals("0")) {
                        timer.start();
                    }

                    JButton cell = (JButton) e.getSource();

                    String[] cellPosition = cell.getActionCommand().split(",");
                    int row = Integer.parseInt(cellPosition[0]);
                    int column = Integer.parseInt(cellPosition[1]);

                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (!cell.isEnabled()) {
                            return;
                        }

                        presenter.cellClicked(row, column);
                    }

                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        presenter.cellMiddleClicked(row, column);
                    }
                }
            };

            cellFlaggedListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        JButton flaggedCell = (JButton) e.getSource();

                        if (!flaggedCell.isEnabled()) {
                            return;
                        }

                        remainingFlags = Integer.parseInt(remainingFlagsLabel.getText());

                        String[] cellPosition = flaggedCell.getActionCommand().split(",");
                        int row = Integer.parseInt(cellPosition[0]);
                        int column = Integer.parseInt(cellPosition[1]);

                        presenter.toggleFlag(row, column, remainingFlags);
                    }
                }
            };

            JLabel difficultyLabel = new JLabel("Difficulty: Beginner");
            Dimension difficlutyLabelDimension = new Dimension(150, 10);
            difficultyLabel.setPreferredSize(difficlutyLabelDimension);
            difficultyLabel.setMinimumSize(difficlutyLabelDimension);

            difficultyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
            optionsPanel.add(difficultyLabel);

            JButton startGameButton = new JButton("Start Game");
            optionsPanel.add(startGameButton);

            ActionListener startGameListener = _ -> {
                try {
                    switch (difficulty) {
                        case "beginner" -> difficultyLabel.setText("Difficulty: Beginner");
                        case "intermediate" -> difficultyLabel.setText("Difficulty: Intermediate");
                        case "expert" -> difficultyLabel.setText("Difficulty: Expert");
                        case "custom" -> {
                            difficultyLabel.setText("Difficulty: Custom");

                            rowsAmount = Integer.parseInt(rowsTextField.getText());
                            columnsAmount = Integer.parseInt(columnsTextField.getText());
                            minesAmount = Integer.parseInt(minesTextField.getText());
                        }
                    }
                } catch (Exception e) {
                    return;
                }

                if ((presenter.isIncorrectBoardSize(rowsAmount, columnsAmount)) || (minesAmount >= rowsAmount * columnsAmount || minesAmount < 1)) {
                    showInputErrorMessage();

                    rowsTextField.setText("9");
                    columnsTextField.setText("9");
                    minesTextField.setText("10");
                    remainingFlagsLabel.setText("10");

                    rowsAmount = 9;
                    columnsAmount = 9;
                    minesAmount = 10;
                }

                remainingFlags = minesAmount;
                remainingFlagsLabel.setText("" + remainingFlags);

                minesweeperBoard.setLayout(new GridLayout(rowsAmount, columnsAmount));
                minesweeperBoard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                if (difficulty.equals("custom")) {
                    presenter.startCustomGame(rowsAmount, columnsAmount, minesAmount);

                    return;
                }

                presenter.startGame(difficulty);
            };

            startGameButton.addActionListener(startGameListener);

            timerLabel = new JLabel("0");
            timerLabel.setPreferredSize(new Dimension(100, 40));
            timerLabel.setForeground(Color.RED);

            timerLabel.setFont(new Font("Serif", Font.BOLD, 24));

            GridBagConstraints timerLabelConstraints = new GridBagConstraints();
            timerLabelConstraints.insets = new Insets(0, 80, 0, 0);

            optionsPanel.add(timerLabel, timerLabelConstraints);

            ActionListener updateTimer = _ -> {
                timerLabel.setText(String.valueOf(gameTime));
                gameTime += 1;
            };

            timer = new Timer(1000, updateTimer);

            JMenuBar gameMenuBar = new JMenuBar();

            JMenu gameMenu = new JMenu("Menu");

            gameMenuBar.add(gameMenu);

            JMenuItem startGameMenu = new JMenuItem("Start Game");
            startGameMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

            startGameMenu.addActionListener(startGameListener);

            startGameMenu.doClick();

            gameMenu.add(startGameMenu);
            gameMenu.addSeparator();

            JMenuItem aboutMenu = new JMenuItem("About");
            aboutMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
            aboutMenu.addActionListener(_ -> presenter.about());

            gameMenu.add(aboutMenu);
            gameMenu.addSeparator();

            JMenuItem highScoresMenu = new JMenuItem("HighScores");
            highScoresMenu.addActionListener(_ -> presenter.highScores());
            highScoresMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

            gameMenu.add(highScoresMenu);
            gameMenu.addSeparator();

            JMenuItem exitMenu = new JMenuItem("Exit");
            exitMenu.addActionListener(_ -> presenter.exitGame());
            exitMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

            gameMenu.add(exitMenu);
            gameMenu.addSeparator();

            JMenu optionsMenu = new JMenu("Difficulty");
            gameMenuBar.add(optionsMenu);

            JRadioButtonMenuItem beginnerDifficultyButton = new JRadioButtonMenuItem("Beginner", true);
            JRadioButtonMenuItem intermediateDifficultyButton = new JRadioButtonMenuItem("Intermediate");
            JRadioButtonMenuItem expertDifficultyButton = new JRadioButtonMenuItem("Expert");
            JRadioButtonMenuItem customDifficultyButton = new JRadioButtonMenuItem("Custom");

            ButtonGroup group = new ButtonGroup();
            group.add(beginnerDifficultyButton);
            group.add(intermediateDifficultyButton);
            group.add(expertDifficultyButton);
            group.add(customDifficultyButton);

            beginnerDifficultyButton.addActionListener(_ -> {
                difficulty = "beginner";

                rowsTextField.setVisible(false);
                rowsLabel.setVisible(false);

                columnsTextField.setVisible(false);
                columnsLabel.setVisible(false);

                minesTextField.setVisible(false);
                minesLabel.setVisible(false);

                remainingFlagsLabel.setText("10");
            });

            intermediateDifficultyButton.addActionListener(_ -> {
                difficulty = "intermediate";

                rowsTextField.setVisible(false);
                rowsLabel.setVisible(false);

                columnsTextField.setVisible(false);
                columnsLabel.setVisible(false);

                minesTextField.setVisible(false);
                minesLabel.setVisible(false);

                remainingFlagsLabel.setText("40");
            });

            expertDifficultyButton.addActionListener(_ -> {
                difficulty = "expert";

                rowsTextField.setVisible(false);
                rowsLabel.setVisible(false);

                columnsTextField.setVisible(false);
                columnsLabel.setVisible(false);

                minesTextField.setVisible(false);
                minesLabel.setVisible(false);

                remainingFlagsLabel.setText("99");
            });

            customDifficultyButton.addActionListener(_ -> {
                difficulty = "custom";

                rowsTextField.setVisible(true);
                rowsLabel.setVisible(true);

                columnsTextField.setVisible(true);
                columnsLabel.setVisible(true);

                minesTextField.setVisible(true);
                minesLabel.setVisible(true);

                frame.pack();
                frame.setLocationRelativeTo(null);
            });

            optionsMenu.add(beginnerDifficultyButton);
            optionsMenu.addSeparator();

            optionsMenu.add(intermediateDifficultyButton);
            optionsMenu.addSeparator();

            optionsMenu.add(expertDifficultyButton);
            optionsMenu.addSeparator();

            optionsMenu.add(customDifficultyButton);
            optionsMenu.addSeparator();

            frame.setJMenuBar(gameMenuBar);

            gamePanelConstraints.gridx = 0;
            gamePanelConstraints.gridy = 2;
            gamePanelConstraints.weightx = 1.0;
            gamePanelConstraints.weighty = 1.0;
            gamePanelConstraints.fill = GridBagConstraints.NONE;
            gamePanelConstraints.anchor = GridBagConstraints.CENTER;
            gamePanelConstraints.gridwidth = GridBagConstraints.NONE;

            frame.add(minesweeperBoard, gamePanelConstraints);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int confirmClosing = JOptionPane.showConfirmDialog(frame, "Exit the game?",
                            "Confirm Exit", JOptionPane.YES_NO_OPTION);

                    if (confirmClosing == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });

            frame.pack();
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        });

    }

    @Override
    public ImageIcon setIconPath(String iconPath) {
        return new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
    }

    @Override
    public void showInputErrorMessage() {
        JOptionPane.showMessageDialog(frame, "Incorrect input values: too few/too many mines or 0/negative values. Default values(beginner difficulty) are used.", "Input values error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showBoardSizeErrorMessage() {
        JOptionPane.showMessageDialog(frame, "Board should have up to 18 rows and 30 columns. Default values(beginner difficulty) are used.", "Input values error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = Objects.requireNonNull(presenter, () -> {
            JOptionPane.showMessageDialog(
                    null,
                    "Presenter must not be null.",
                    "Presenter error",
                    JOptionPane.ERROR_MESSAGE
            );
            return "Presenter must not be null.";
        });
    }

    @Override
    public void showToggledFlag(int row, int column) {
        JButton cell = cells[row][column];

        if (cell.getIcon() == null) {
            cell.setIcon(scaleIcon(flagIcon, cell.getWidth(), cell.getHeight()));

            SwingUtilities.invokeLater(() -> remainingFlagsLabel.setText(String.valueOf(remainingFlags - 1)));
        } else {
            cell.setIcon(null);

            SwingUtilities.invokeLater(() -> remainingFlagsLabel.setText(String.valueOf(remainingFlags + 1)));
        }
    }

    @Override
    public void revealAllMines(ArrayList<Point> minesPositions) {
        for (Point point : minesPositions) {
            SwingUtilities.invokeLater(() -> {
                JButton mineCell = cells[point.y][point.x];
                mineCell.setIcon(scaleIcon(mineIcon, mineCell.getWidth(), mineCell.getHeight()));
            });
        }
    }

    @Override
    public void resetBoard(int boardRowsAmount, int boardColumnsAmount) {
        SwingUtilities.invokeLater(() -> {
            cells = new JButton[boardRowsAmount][boardColumnsAmount];

            frame.remove(minesweeperBoard);

            minesweeperBoard = new JPanel();
            minesweeperBoard.setLayout(new GridLayout(boardRowsAmount, boardColumnsAmount));

            minesweeperBoard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            frame.add(minesweeperBoard, gamePanelConstraints);

            for (int row = 0; row < boardRowsAmount; ++row) {
                for (int column = 0; column < boardColumnsAmount; ++column) {
                    cells[row][column] = new JButton("");

                    if ((boardColumnsAmount >= 14) || (boardRowsAmount >= 14)) {
                        cells[row][column].setPreferredSize(new Dimension(30, 30));
                    } else {
                        cells[row][column].setPreferredSize(new Dimension(50, 50));
                    }

                    cells[row][column].setActionCommand(row + "," + column);
                    cells[row][column].setIcon(null);
                    cells[row][column].setEnabled(true);
                    cells[row][column].setBackground(null);

                    cells[row][column].addMouseListener(cellClickedListener);
                    cells[row][column].addMouseListener(cellFlaggedListener);

                    minesweeperBoard.add(cells[row][column]);
                }
            }

            frame.revalidate();
            frame.repaint();

            frame.pack();
            frame.setLocationRelativeTo(null);


            stopTimer();
            timerLabel.setText("0");
        });

        gameTime = 1;
    }

    @Override
    public void updateCell(int row, int column, String cellText) {
        SwingUtilities.invokeLater(() -> {
            int cellWidth = cells[0][0].getWidth();
            int cellHeight = cells[0][0].getHeight();

            switch (cellText) {
                case "0" -> {
                    cells[row][column].setBackground(Color.gray);
                    cells[row][column].setEnabled(false);
                }

                case "1" -> cells[row][column].setIcon(scaleIcon(oneIcon, cellWidth, cellHeight));
                case "2" -> cells[row][column].setIcon(scaleIcon(twoIcon, cellWidth, cellHeight));
                case "3" -> cells[row][column].setIcon(scaleIcon(threeIcon, cellWidth, cellHeight));
                case "4" -> cells[row][column].setIcon(scaleIcon(fourIcon, cellWidth, cellHeight));
                case "5" -> cells[row][column].setIcon(scaleIcon(fiveIcon, cellWidth, cellHeight));
                case "6" -> cells[row][column].setIcon(scaleIcon(sixIcon, cellWidth, cellHeight));
                case "7" -> cells[row][column].setIcon(scaleIcon(sevenIcon, cellWidth, cellHeight));
                case "8" -> cells[row][column].setIcon(scaleIcon(eightIcon, cellWidth, cellHeight));
            }
        });
    }

    public ImageIcon scaleIcon(ImageIcon sourceIcon, int cellWidth, int cellHeight) {
        return new ImageIcon(sourceIcon.getImage().getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH));
    }

    @Override
    public void showAboutMessage() {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, """
                 This is a classic minesweeper game.
                 Controls:
                     Select amount of rows and columns and amount of mines, then press 'Start Game' button.
                     Right click places/removes flag, left click reveals a cell.
                     To win, reveal all non-mined cells.
                """, "Game info", JOptionPane.INFORMATION_MESSAGE));
    }

    @Override
    public void showHighScoresTable(String scores) {
        SwingUtilities.invokeLater(() -> {
            JTextArea textArea = new JTextArea(scores);
            textArea.setRows(20);
            textArea.setColumns(50);
            textArea.setEditable(false);
            textArea.setCaretPosition(0);

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(null, scrollPane, "Game Scores", JOptionPane.PLAIN_MESSAGE);
        });
    }

    @Override
    public void exitGame() {
        SwingUtilities.invokeLater(() -> frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)));
    }

    @Override
    public void showWinMessage() {
        timer.stop();

        SwingUtilities.invokeLater(() -> {
            String playerName = JOptionPane.showInputDialog(frame, "Please enter your name:", "Victory!", JOptionPane.INFORMATION_MESSAGE);

            if (playerName != null) {
                presenter.writeScores(playerName, timerLabel.getText());
            }
        });
    }

    @Override
    public String showError(String exceptionMessage) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, exceptionMessage, "Error", JOptionPane.ERROR_MESSAGE));
        return exceptionMessage;
    }

    @Override
    public void showHighlightedMine(int row, int column) {
        SwingUtilities.invokeLater(() -> cells[row][column].setIcon(scaleIcon(highlightedMineIcon, cells[row][column].getWidth(), cells[row][column].getHeight())));
    }

    @Override
    public void stopTimer() {
        timer.stop();
    }

    @Override
    public boolean isIconNonNull(int row, int column) {
        return cells[row][column].getIcon() != null;
    }
}