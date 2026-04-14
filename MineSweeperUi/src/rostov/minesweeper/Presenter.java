package rostov.minesweeper;

import rostov.minesweeper.gui.View;

import javax.swing.*;

public class Presenter {
    public final Model model;
    public final View view;

    private int rowsAmount;
    private int columnsAmount;

    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;

        view.setController(this);
    }

    public void start() {
        view.start();
    }

    public void toggleFlag(JButton cell, int remainingFlags) {
        if (remainingFlags > 0 || cell.getIcon() != null) {
            model.toggleFlag(cell, remainingFlags);

            view.showToggledFlag(cell, model.getFlagIcon());
        }
    }

    public void cellClicked(JButton cell) {
        String[] cellPosition = cell.getActionCommand().split(",");
        int row = Integer.parseInt(cellPosition[0]);
        int column = Integer.parseInt(cellPosition[1]);

        if (model.isRevealed(cell) || model.isFlagged(cell)) {
            return;
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

    public void startGame(int rowsAmount, int columnsAmount, int minesAmount) {
        model.resetBoard();

        this.rowsAmount = rowsAmount;
        this.columnsAmount = columnsAmount;

        view.resetBoard();

        model.setMines(rowsAmount, columnsAmount, minesAmount);
        model.countAllNearbyMines();
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