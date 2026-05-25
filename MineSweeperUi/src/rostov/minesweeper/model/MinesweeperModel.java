package rostov.minesweeper.model;

import java.awt.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MinesweeperModel implements Model {
    private static final int MAX_COLUMNS_AMOUNT = 30;
    private static final int MAX_ROWS_AMOUNT = 18;

    private static List<Difficulty> difficulties = new ArrayList<>();

    private Cell[][] board;

    private int rowsAmount = 9;
    private int columnsAmount = 9;

    public MinesweeperModel(List<Difficulty> difficulties) {
        MinesweeperModel.difficulties = Objects.requireNonNull(difficulties, "Difficulties list must not be null.");
    }

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
    public boolean toggleFlag(int row, int column, int remainingFlags) {
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

        board = new Cell[rowsAmount][columnsAmount];

        for (int row = 0; row < board.length; ++row) {
            for (int column = 0; column < board[0].length; ++column) {
                board[row][column] = new Cell();
                board[row][column].setEnabled(true);
            }
        }
    }

    @Override
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
    public int countNearbyFlags(int row, int column) {
        int count = 0;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (r >= 0 && r < rowsAmount && c >= 0 && c < columnsAmount) {
                    if (board[r][c].getFlagged()) {
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

        return true;
    }

    @Override
    public void writeScore(String playerName, String timeElapsed, Difficulty difficulty) throws Exception {
        ScoreManager.write(difficulty.getScoresFilePath(), playerName, Integer.parseInt(timeElapsed));
    }

    @Override
    public String readScores(Difficulty difficulty) throws Exception {
        return ScoreManager.read(difficulty.getScoresFilePath());
    }

    @Override
    public int getMaxRowsAmount() {
        return MAX_ROWS_AMOUNT;
    }

    @Override
    public int getMaxColumnsAmount() {
        return MAX_COLUMNS_AMOUNT;
    }

    @Override
    public void disableBoard() {
        for (int row = 0; row < rowsAmount; ++row) {
            for (int column = 0; column < columnsAmount; ++column) {
                board[row][column].setEnabled(false);
            }
        }
    }

    @Override
    public boolean isEnabled(int row, int column) {
        return board[row][column].getEnabled();
    }

    @Override
    public Difficulty getDifficulty(String difficultyName) {
        for (Difficulty difficulty : difficulties) {
            if (difficulty.getName().equals(difficultyName)) {
                return difficulty;
            }
        }

        return null;
    }
}