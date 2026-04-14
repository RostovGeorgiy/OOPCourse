package rostov.minesweeper.gui;

import rostov.minesweeper.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class DesktopView implements View {
    private Presenter presenter;
    private JFrame frame;

    private JButton[][] cells;

    private final ImageIcon mineIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/mine.png")));

    private int rowsAmount = 9;
    private int columnsAmount = 9;
    private int minesAmount = 10;

    private int remainingFlags;

    private final JLabel remainingFlagsLabel = new JLabel();
    private JPanel minesweeperBoard = new JPanel();
    private final GridBagConstraints gamePanelConstraints = new GridBagConstraints();

    private ActionListener cellClickedListener;
    private MouseListener cellFlaggedListener;

    public void start() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame();
            frame.setSize(1500, 1500);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new GridBagLayout());
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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

            startGameButton.addActionListener(a -> {
                rowsAmount = Integer.parseInt(rowsTextField.getText());
                columnsAmount = Integer.parseInt(columnsTextField.getText());
                minesAmount = Integer.parseInt(minesTextField.getText());

                if (rowsAmount > 30 || columnsAmount > 30) {
                    showBoardSizeErrorMessage();
                    rowsAmount = 9;
                    columnsAmount = 9;
                    minesAmount = 10;
                }

                if (rowsAmount <= 0 || columnsAmount <= 0 || minesAmount <= 0 || minesAmount >= rowsAmount * columnsAmount) {
                    showInputErrorMessage();

                    rowsTextField.setText("");
                    columnsTextField.setText("");
                    minesTextField.setText("");

                    return;
                }

                remainingFlags = minesAmount;
                remainingFlagsLabel.setText("" + remainingFlags);

                minesweeperBoard.setLayout(new GridLayout(rowsAmount, columnsAmount));

                presenter.startGame(rowsAmount, columnsAmount, minesAmount);
            });

            cellClickedListener = e -> {
                JButton cell = (JButton) e.getSource();
                presenter.cellClicked(cell);
            };

            cellFlaggedListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        JButton flaggedCell = (JButton) e.getSource();

                        remainingFlags = Integer.parseInt(remainingFlagsLabel.getText());
                        presenter.toggleFlag(flaggedCell, remainingFlags);
                    }
                }
            };

            JButton aboutGameButton = new JButton("About");
            aboutGameButton.addActionListener(a -> {
                presenter.about();
            });

            JButton highScoresButton = new JButton("High Scores");
            highScoresButton.addActionListener(a -> {
                presenter.highScores();
            });

            JButton exitButton = new JButton("Exit");
            exitButton.addActionListener(a -> {
                presenter.exitGame();
            });

            JPanel gameButtonsPanel = new JPanel();
            gameButtonsPanel.setLayout(new BoxLayout(gameButtonsPanel, BoxLayout.Y_AXIS));

            gameButtonsPanel.setLayout(new FlowLayout());

            gameButtonsPanel.add(exitButton);
            gameButtonsPanel.add(startGameButton);
            gameButtonsPanel.add(aboutGameButton);
            gameButtonsPanel.add(highScoresButton);

            GridBagConstraints gameButtonsPanelConstraints = new GridBagConstraints();

            gameButtonsPanelConstraints.gridx = 0;
            gameButtonsPanelConstraints.gridy = 0;
            gameButtonsPanelConstraints.weightx = 0.0;
            gameButtonsPanelConstraints.weighty = 0.0;
            gameButtonsPanelConstraints.gridwidth = GridBagConstraints.REMAINDER;
            gameButtonsPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
            gameButtonsPanelConstraints.anchor = GridBagConstraints.NORTH;

            frame.add(gameButtonsPanel, gameButtonsPanelConstraints);

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
    public void showInputErrorMessage() {
        JOptionPane.showMessageDialog(frame, "Rows, columns, and mines amounts should be > 0. " +
                "Mines amount should not be >= total cells amount.", "Input values error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showBoardSizeErrorMessage() {
        JOptionPane.showMessageDialog(frame, "Board should have up to 30 rows and columns. Default values used.", "Input values error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setController(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToggledFlag(JButton cell, ImageIcon icon) {
        cell.setIcon(icon);

        if (icon != null) {
            remainingFlagsLabel.setText(String.valueOf(remainingFlags - 1));
        } else {
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
            JButton mineCell = cells[point.x][point.y];
            mineCell.setIcon(mineIcon);
        }
    }

    @Override
    public void resetBoard() {
        cells = new JButton[rowsAmount][columnsAmount];
        frame.remove(minesweeperBoard);
        minesweeperBoard = new JPanel();
        minesweeperBoard.setLayout(new GridLayout(rowsAmount, columnsAmount));
        minesweeperBoard.setMaximumSize(new Dimension(900, 900));
        frame.add(minesweeperBoard, gamePanelConstraints);

        for (int row = 0; row < rowsAmount; ++row) {
            for (int column = 0; column < columnsAmount; ++column) {
                cells[row][column] = new JButton("");

                cells[row][column].setPreferredSize(new Dimension(50, 50));
                cells[row][column].setActionCommand(row + "," + column);
                minesweeperBoard.add(cells[row][column]);
                cells[row][column].setText("");
                cells[row][column].setIcon(null);
                cells[row][column].setEnabled(true);
                cells[row][column].setBackground(null);

                cells[row][column].addActionListener(cellClickedListener);
                cells[row][column].addMouseListener(cellFlaggedListener);
            }
        }

        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void updateCell(int row, int column, String cellText) {
        if (cellText.equals("E")) {
            cells[row][column].setBackground(Color.gray);
            cells[row][column].setEnabled(false);
        } else {
            cells[row][column].setText(cellText);
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