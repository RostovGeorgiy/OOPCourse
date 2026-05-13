package rostov.minesweeper.presenter;

import rostov.minesweeper.model.Difficulty;
import rostov.minesweeper.gui.View;
import rostov.minesweeper.model.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Presenter {
    public final Model model;
    public final View view;

    private int rowsAmount;
    private int columnsAmount;
    private int minesAmount;

    private boolean isFirstClick = true;

    private Difficulty difficulty;

    public Presenter(Model model, View view) {
        this.model = Objects.requireNonNull(model, "Model must not be null.");
        this.view = Objects.requireNonNull(view, "View must not be null.");

        view.setPresenter(this);
    }

    public void start() {
        model.addDifficulties();
        view.start();
    }

    public void toggleFlag(int row, int column, int remainingFlags) {
        if (!model.isEnabled(row, column)) {
            return;
        }

        if (remainingFlags > 0 || view.isIconNonNull(row, column)) {
            if (model.toggleFlag(row, column, remainingFlags)) {
                view.showToggledFlag(row, column);
            }
        }
    }

    public void cellClicked(int row, int column) {
        if (model.isRevealed(row, column) || model.isFlagged(row, column) || !model.isEnabled(row, column)) {
            return;
        }

        if (isFirstClick) {
            model.setMines(rowsAmount, columnsAmount, minesAmount, row, column);
            model.countAllNearbyMines();

            isFirstClick = false;
        }

        if (model.isMine(row, column)) {
            model.disableBoard();

            view.revealAllMines(model.getMines());
            view.showHighlightedMine(row, column);

            view.stopTimer();
        } else {
            revealCell(row, column);

            if (model.checkWin()) {
                view.showWinMessage();
            }
        }
    }

    public void cellMiddleClicked(int row, int column) {
        if (model.isFlagged(row, column) || !model.isEnabled(row, column)) {
            return;
        }

        if (!model.isRevealed(row, column)) {
            cellClicked(row, column);
        }

        int nearbyMines = model.countNearbyMines(row, column);
        int nearbyFlags = model.countNearbyFlags(row, column);

        if (nearbyFlags == nearbyMines) {
            revealNearbyCells(row, column);
        }
    }

    private void revealCell(int row, int column) {
        if (model.isRevealed(row, column)) return;

        model.setRevealed(row, column);

        int count = model.countNearbyMines(row, column);

        view.updateCell(row, column, String.valueOf(count));

        if (count == 0) {
            revealNearbyCells(row, column);
        }
    }

    private void revealNearbyCells(int row, int column) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newColumn = column + j;

                if (newRow >= 0 && newRow < rowsAmount && newColumn >= 0 && newColumn < columnsAmount) {
                    if (!model.isRevealed(newRow, newColumn) && !model.isFlagged(newRow, newColumn)) {
                        model.setRevealed(newRow, newColumn);

                        int count = model.countNearbyMines(newRow, newColumn);

                        String displayValue = (count > 0) ? String.valueOf(count) : "0";
                        view.updateCell(newRow, newColumn, displayValue);

                        if (count == 0) {
                            revealNearbyCells(newRow, newColumn);
                        }
                    }
                }
            }
        }
    }

    public boolean isIncorrectBoardSize(int rowsAmount, int columnsAmount) {
        int maxRowsAmount = model.getMaxRowsAmount();
        int maxColumnsAmount = model.getMaxColumnsAmount();

        if (rowsAmount > maxRowsAmount || columnsAmount > maxColumnsAmount) {
            view.showBoardSizeErrorMessage();

            return true;
        }

        return false;
    }

    public void startGame(String difficultyName) {
        difficulty = model.getDifficulty(difficultyName);

        this.rowsAmount = difficulty.getRowsAmount();
        this.columnsAmount = difficulty.getColumnsAmount();
        this.minesAmount = difficulty.getMinesAmount();

        model.resetBoard(rowsAmount, columnsAmount);

        isFirstClick = true;

        view.resetBoard(model.getRowsAmount(), model.getColumnsAmount());
    }

    public void startCustomGame(int rowsAmount, int columnsAmount, int minesAmount) {
        this.rowsAmount = rowsAmount;
        this.columnsAmount = columnsAmount;
        this.minesAmount = minesAmount;

        model.resetBoard(rowsAmount, columnsAmount);

        isFirstClick = true;

        view.resetBoard(model.getRowsAmount(), model.getColumnsAmount());
    }

    public void about() {
        view.showAboutMessage();
    }

    public void highScores() {
        try {
            view.showHighScoresTable(model.readScores(difficulty));
        } catch (FileNotFoundException e) {
            view.showError("File not found: " + e.getMessage());
        } catch (IOException e) {
            view.showError("An IO exception has occurred: " + e.getMessage());
        } catch (Exception e) {
            view.showError("Error: " + e.getMessage());
        }
    }

    public void exitGame() {
        view.exitGame();
    }

    public void writeScores(String playerName, String timeElapsed) {
        try {
            model.writeScore(playerName, timeElapsed, difficulty);
        } catch (FileNotFoundException e) {
            view.showError("File not found: " + e.getMessage());
        } catch (IOException e) {
            view.showError("An IO exception has occurred: " + e.getMessage());
        } catch (Exception e) {
            view.showError("Error: " + e.getMessage());
        }
    }
}