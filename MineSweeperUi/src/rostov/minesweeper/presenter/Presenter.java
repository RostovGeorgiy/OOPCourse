package rostov.minesweeper.presenter;

import rostov.minesweeper.gui.View;
import rostov.minesweeper.model.Model;

import javax.swing.*;

public class Presenter {
    public final Model model;
    public final View view;

    private int rowsAmount;
    private int columnsAmount;
    private int minesAmount;

    private boolean isFirstClick = true;

    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;

        view.setPresenter(this);
    }

    public void start() {
        view.start();
    }

    public void toggleFlag(JButton cell, int remainingFlags) {
        if (remainingFlags > 0 || cell.getIcon() != null) {
            if (model.toggleFlag(cell, remainingFlags)) {
                view.showToggledFlag(cell);
            }
        }
    }

    public void cellClicked(JButton cell) {
        String[] cellPosition = cell.getActionCommand().split(",");
        int row = Integer.parseInt(cellPosition[0]);
        int column = Integer.parseInt(cellPosition[1]);

        if (model.isRevealed(row, column) || model.isFlagged(row, column)) {
            return;
        }

        if (isFirstClick) {
            model.setMines(rowsAmount, columnsAmount, minesAmount, row, column);
            model.countAllNearbyMines();

            isFirstClick = false;
        }

        if (model.isMine(row, column)) {
            view.revealAllMines(model.getMines());
            view.showGameOverMessage("Game Over!");
        } else {
            revealCell(row, column);

            if (model.checkWin()) {
                view.showWinMessage("Victory!");
            }
        }
    }

    private void revealCell(int row, int column) {
        if (model.isRevealed(row, column)) return;

        model.setRevealed(row, column);

        int count = model.countNearbyMines(row, column);

        if (count > 0) {
            view.updateCell(row, column, String.valueOf(count));
        } else {
            view.updateCell(row, column, "E");
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

                        String displayValue = (count > 0) ? String.valueOf(count) : "E";
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
        int maxBoardDimension = model.getMaxBoardDimension();

        if (rowsAmount > maxBoardDimension || columnsAmount > maxBoardDimension) {
            view.showBoardSizeErrorMessage();

            return true;
        }

        return false;
    }

    public void startGame(int rowsAmount, int columnsAmount, int minesAmount) {
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
        view.showHighScoresTable(model.readScores());
    }

    public void exitGame() {
        view.exitGame();
    }

    public void writeScores(String playerName) {
        model.writeScore(playerName);
    }
}