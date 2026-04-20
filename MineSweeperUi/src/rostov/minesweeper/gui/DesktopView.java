package rostov.minesweeper.gui;

import rostov.minesweeper.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class DesktopView implements View {
    private Presenter presenter;
    private JFrame frame;

    private JButton[][] cells;

    private final ImageIcon minesweeperIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/minesweeper.png")));

    private final ImageIcon mineIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/mine.png")));
    private final ImageIcon flagIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/flag.png")));

    private final ImageIcon oneIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/1.png")));
    private final ImageIcon twoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/2.png")));
    private final ImageIcon threeIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/3.png")));
    private final ImageIcon fourIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/4.png")));
    private final ImageIcon fiveIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/5.png")));
    private final ImageIcon sixIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/6.png")));
    private final ImageIcon sevenIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/7.png")));
    private final ImageIcon eightIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/8.png")));

    private int rowsAmount = 9;
    private int columnsAmount = 9;
    private int minesAmount = 10;

    private int remainingFlags;

    private final JLabel remainingFlagsLabel = new JLabel();
    private JPanel minesweeperBoard = new JPanel();
    private final GridBagConstraints gamePanelConstraints = new GridBagConstraints();

    private MouseListener cellClickedListener;
    private MouseListener cellFlaggedListener;

    public void start() {
        Objects.requireNonNull(presenter, "Presenter must not be null.");

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Minesweeper");
            frame.setIconImage(minesweeperIcon.getImage());
            frame.setSize(700, 800);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new GridBagLayout());
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setMinimumSize(new Dimension(600, 700));

            JLabel rowsLabel = new JLabel("Input amount of rows:");
            JTextField rowsTextField = new JTextField(2);
            rowsTextField.setText(String.valueOf(rowsAmount));

            JLabel columnsLabel = new JLabel("Input amount of columns:");
            JTextField columnsTextField = new JTextField(2);
            columnsTextField.setText(String.valueOf(columnsAmount));

            JLabel minesLabel = new JLabel("Input amount of mines:");
            JTextField minesTextField = new JTextField(2);
            minesTextField.setText(String.valueOf(minesAmount));

            JPanel optionsPanel = new JPanel();

            JLabel flagsCountInfoLabel = new JLabel("Flags remaining: ");

            remainingFlagsLabel.setPreferredSize(new Dimension(50, 50));

            optionsPanel.add(rowsLabel);
            optionsPanel.add(rowsTextField);
            optionsPanel.add(columnsLabel);
            optionsPanel.add(columnsTextField);
            optionsPanel.add(minesLabel);
            optionsPanel.add(minesTextField);
            optionsPanel.add(flagsCountInfoLabel);
            optionsPanel.add(remainingFlagsLabel);

            GridBagConstraints optionsPanelConstraints = new GridBagConstraints();

            optionsPanelConstraints.gridx = 0;
            optionsPanelConstraints.gridy = 1;
            optionsPanelConstraints.weightx = 0.0;
            optionsPanelConstraints.weighty = 0.0;
            optionsPanelConstraints.gridwidth = GridBagConstraints.REMAINDER;
            optionsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
            optionsPanelConstraints.anchor = GridBagConstraints.NORTH;

            frame.add(optionsPanel, optionsPanelConstraints);

            JButton startGameButton = new JButton("Start Game");

            startGameButton.addActionListener(_ -> {
                rowsAmount = Integer.parseInt(rowsTextField.getText());
                columnsAmount = Integer.parseInt(columnsTextField.getText());
                minesAmount = Integer.parseInt(minesTextField.getText());

                if (rowsAmount <= 0 || columnsAmount <= 0 || minesAmount <= 0 || minesAmount >= rowsAmount * columnsAmount) {
                    showInputErrorMessage();

                    rowsTextField.setText("");
                    columnsTextField.setText("");
                    minesTextField.setText("");

                    return;
                }

                if (presenter.isIncorrectBoardSize(rowsAmount, columnsAmount)) {
                    rowsAmount = 9;
                    columnsAmount = 9;
                    minesAmount = 10;
                }

                remainingFlags = minesAmount;
                remainingFlagsLabel.setText("" + remainingFlags);

                minesweeperBoard.setLayout(new GridLayout(rowsAmount, columnsAmount));

                presenter.startGame(rowsAmount, columnsAmount, minesAmount);
            });

            cellClickedListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isMiddleMouseButton(e)) {
                        JButton cell = (JButton) e.getSource();
                        presenter.cellClicked(cell);
                    }
                }
            };

            cellFlaggedListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        JButton flaggedCell = (JButton) e.getSource();

                        remainingFlags = Integer.parseInt(remainingFlagsLabel.getText());
                        presenter.toggleFlag(flaggedCell, remainingFlags);
                    }
                }
            };

            JMenuBar gameMenuBar = new JMenuBar();

            JMenu gameMenu = new JMenu("Menu");

            gameMenuBar.add(gameMenu);

            JMenuItem startGameMenu = new JMenuItem("StartGame");
            startGameMenu.addActionListener(_ -> {
                try {
                    rowsAmount = Integer.parseInt(rowsTextField.getText());
                    columnsAmount = Integer.parseInt(columnsTextField.getText());
                    minesAmount = Integer.parseInt(minesTextField.getText());
                } catch (Exception e) {
                    showInputIsNonNumericMessage();
                    return;
                }

                if (rowsAmount <= 0 || columnsAmount <= 0 || minesAmount <= 0 || minesAmount >= rowsAmount * columnsAmount) {
                    showInputErrorMessage();

                    rowsTextField.setText("");
                    columnsTextField.setText("");
                    minesTextField.setText("");

                    return;
                }

                if (presenter.isIncorrectBoardSize(rowsAmount, columnsAmount)) {
                    rowsAmount = 9;
                    columnsAmount = 9;
                    minesAmount = 10;
                }

                remainingFlags = minesAmount;
                remainingFlagsLabel.setText("" + remainingFlags);

                minesweeperBoard.setLayout(new GridLayout(rowsAmount, columnsAmount));

                presenter.startGame(rowsAmount, columnsAmount, minesAmount);
            });

            startGameMenu.doClick();

            gameMenu.add(startGameMenu);

            JMenuItem aboutMenu = new JMenuItem("About");
            aboutMenu.addActionListener(_ -> presenter.about());

            gameMenu.add(aboutMenu);

            JMenuItem highScoresMenu = new JMenuItem("HighScores");
            highScoresMenu.addActionListener(_ -> presenter.highScores());

            gameMenu.add(highScoresMenu);

            JMenuItem exitMenu = new JMenuItem("Exit");
            exitMenu.addActionListener(_ -> presenter.exitGame());

            gameMenu.add(exitMenu);

            frame.setJMenuBar(gameMenuBar);

            gamePanelConstraints.gridx = 0;
            gamePanelConstraints.gridy = 2;
            gamePanelConstraints.weightx = 1.0;
            gamePanelConstraints.weighty = 1.0;
            gamePanelConstraints.fill = GridBagConstraints.NONE;
            gamePanelConstraints.anchor = GridBagConstraints.CENTER;
            gamePanelConstraints.gridwidth = GridBagConstraints.REMAINDER;

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

            frame.setVisible(true);
        });

    }

    @Override
    public void showInputIsNonNumericMessage() {
        JOptionPane.showMessageDialog(frame, "Rows, columns, and mines amounts should be a valid number. ", "Input value is non numeric", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showInputErrorMessage() {
        JOptionPane.showMessageDialog(frame, "Rows, columns, and mines amounts should be > 0. " +
                "Mines amount should not be >= total cells amount.", "Input values error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showBoardSizeErrorMessage() {
        JOptionPane.showMessageDialog(frame, "Board should have up to 30 rows and columns. Default values used.", "Input values error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = Objects.requireNonNull(presenter, "Presenter must not be null.");
    }

    @Override
    public void showToggledFlag(JButton cell) {
        if (cell.getIcon() == null) {
            cell.setIcon(flagIcon);

            remainingFlagsLabel.setText(String.valueOf(remainingFlags - 1));
        } else {
            cell.setIcon(null);

            remainingFlagsLabel.setText(String.valueOf(remainingFlags + 1));
        }
    }

    @Override
    public void showGameOverMessage(String message) {
        setCellsDisabled();

        JOptionPane.showMessageDialog(frame, message, "You lost!", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void revealAllMines(ArrayList<Point> minesPositions) {
        for (Point point : minesPositions) {
            JButton mineCell = cells[point.y][point.x];
            mineCell.setIcon(mineIcon);
        }
    }

    @Override
    public void resetBoard(int boardRowsAmount, int boardColumnsAmount) {
        cells = new JButton[boardRowsAmount][boardColumnsAmount];

        frame.remove(minesweeperBoard);

        minesweeperBoard = new JPanel();
        minesweeperBoard.setLayout(new GridLayout(boardRowsAmount, boardColumnsAmount));
        minesweeperBoard.setMaximumSize(new Dimension(550, 550));

        frame.add(minesweeperBoard, gamePanelConstraints);

        for (int row = 0; row < boardRowsAmount; ++row) {
            for (int column = 0; column < boardColumnsAmount; ++column) {
                cells[row][column] = new JButton("");

                cells[row][column].setPreferredSize(new Dimension(20, 20));
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
    }

    @Override
    public void updateCell(int row, int column, String cellText) {
        switch (cellText) {
            case "E" -> {
                cells[row][column].setBackground(Color.gray);
                cells[row][column].setEnabled(false);
            }

            case "1" -> cells[row][column].setIcon(oneIcon);
            case "2" -> cells[row][column].setIcon(twoIcon);
            case "3" -> cells[row][column].setIcon(threeIcon);
            case "4" -> cells[row][column].setIcon(fourIcon);
            case "5" -> cells[row][column].setIcon(fiveIcon);
            case "6" -> cells[row][column].setIcon(sixIcon);
            case "7" -> cells[row][column].setIcon(sevenIcon);
            case "8" -> cells[row][column].setIcon(eightIcon);
        }
    }

    @Override
    public void showAboutMessage() {
        JOptionPane.showMessageDialog(frame, """
                 This is a classic minesweeper game.
                 Controls:
                     Select amount of rows and columns and amount of mines, then press 'Start Game' button.
                     Right click places/removes flag, left click reveals a cell.
                     To win, reveal all non-mined cells.
                """, "Game info", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showHighScoresTable(String scores) {
        JTextArea textArea = new JTextArea(scores);
        textArea.setRows(20);
        textArea.setColumns(50);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Game Scores", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void exitGame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void showWinMessage(String message) {
        String playerName = JOptionPane.showInputDialog(frame, "Please enter your name:", message, JOptionPane.INFORMATION_MESSAGE);

        setCellsDisabled();

        if (playerName != null) {
            presenter.writeScores(playerName);
        }
    }

    private void setCellsDisabled() {
        for (int row = 0; row < rowsAmount; ++row) {
            for (int column = 0; column < columnsAmount; ++column) {
                cells[row][column].setEnabled((false));
            }
        }
    }
}