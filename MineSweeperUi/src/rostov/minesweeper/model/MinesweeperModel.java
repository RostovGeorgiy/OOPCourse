package rostov.minesweeper.model;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

public class MinesweeperModel implements Model {
    private static final String scoresFilePath = "MineSweeperUi/src/rostov/minesweeper/highscores.txt";

    private static final int maxBoardDimension = 30;

    private Cell[][] board;

    private int rowsAmount = 9;
    private int columnsAmount = 9;

    private Instant startTime;
    private long gameScore;

    @Override
    public int getRowsAmount() {
        return rowsAmount;
    }

    @Override
    public int getColumnsAmount() {
        return columnsAmount;
    }

    @Override
    public void setMines(int rowsAmount, int columnsAmount, int minesAmount, int clickedCellRow, int clickedCellColumn) {
        Random random = new Random();

        int minesPlacedCounter = 0;

        while (minesPlacedCounter < minesAmount) {
            int position = random.nextInt(rowsAmount * columnsAmount);
            int row = position % rowsAmount;
            int column = position / rowsAmount;

            if (board[row][column].getMine() || (row == clickedCellRow && column == clickedCellColumn)) {
                continue;
            }

            board[row][column].setMine(true);

            minesPlacedCounter++;
        }
    }

    @Override
    public boolean toggleFlag(JButton flaggedCell, int remainingFlags) {
        String[] cellPosition = flaggedCell.getActionCommand().split(",");
        int row = Integer.parseInt(cellPosition[0]);
        int column = Integer.parseInt(cellPosition[1]);

        if (board[row][column].getRevealed()) {
            return false;
        }

        board[row][column].setFlagged(!board[row][column].getFlagged());

        return true;
    }

    @Override
    public boolean isRevealed(int row, int column) {
        return board[row][column].getRevealed();
    }

    @Override
    public boolean isFlagged(int row, int column) {
        return board[row][column].getFlagged();
    }

    @Override
    public boolean isMine(int row, int column) {
        return board[row][column].getMine();
    }

    @Override
    public ArrayList<Point> getMines() {
        ArrayList<Point> minesPositions = new ArrayList<>();

        for (int row = 0; row < board.length; ++row) {
            for (int column = 0; column < board[0].length; ++column) {
                if (board[row][column].getMine()) {
                    minesPositions.add(new Point(column, row));
                }
            }
        }

        return minesPositions;
    }

    @Override
    public void resetBoard(int rowsAmount, int columnsAmount) {
        this.rowsAmount = rowsAmount;
        this.columnsAmount = columnsAmount;

        startTime = Instant.now();

        board = new Cell[rowsAmount][columnsAmount];

        for (int row = 0; row < board.length; ++row) {
            for (int column = 0; column < board[0].length; ++column) {
                board[row][column] = new Cell();
            }
        }
    }

    public int countNearbyMines(int row, int column) {
        int count = 0;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (r >= 0 && r < rowsAmount && c >= 0 && c < columnsAmount) {
                    if (board[r][c].getMine()) {
                        count++;
                    }
                }
            }
        }

        if (board[row][column].getMine()) {
            count--;
        }

        return count;
    }

    @Override
    public void countAllNearbyMines() {
        for (int row = 0; row < rowsAmount; row++) {
            for (int column = 0; column < columnsAmount; column++) {
                if (!board[row][column].getMine()) {
                    board[row][column].setNearbyMinesAmount(countNearbyMines(row, column));
                } else {
                    board[row][column].setNearbyMinesAmount(0);
                }
            }
        }
    }

    @Override
    public void revealNearbyCells(int row, int column) {
        if (row < 0 || row >= rowsAmount || column < 0 || column >= columnsAmount || board[row][column].getRevealed()) {
            return;
        }

        setRevealed(row, column);

        if (board[row][column].getNearbyMinesAmount() == 0 && !board[row][column].getMine()) {
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = column - 1; c <= column + 1; c++) {
                    revealNearbyCells(r, c);
                }
            }
        }
    }

    @Override
    public void setRevealed(int row, int column) {
        board[row][column].setRevealed(true);
    }

    @Override
    public boolean checkWin() {
        for (int row = 0; row < rowsAmount; row++) {
            for (int column = 0; column < columnsAmount; column++) {
                if (!board[row][column].getRevealed() && !board[row][column].getMine()) {
                    return false;
                }
            }
        }

        Instant winTime = Instant.now();

        gameScore = Duration.between(startTime, winTime).getSeconds();

        return true;
    }

    @Override
    public void writeScore(String playerName) {
        WriteScore.write(scoresFilePath, playerName, gameScore);
    }

    @Override
    public String readScores() {
        return ReadScore.read(scoresFilePath);
    }

    @Override
    public int getMaxBoardDimension() {
        return maxBoardDimension;
    }
}