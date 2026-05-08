package rostov.minesweeper;

import java.util.Objects;

public class Difficulty {
    private int rowsAmount;
    private int columnsAmount;
    private int minesAmount;
    private String scoresFilePath;

    public Difficulty(int rowsAmount, int columnsAmount, int minesAmount, String scoresFilePath) {
        if (rowsAmount < 0 || columnsAmount < 0 || minesAmount < 0) {
            throw new IllegalArgumentException("Rows, columns and mines amounts must be > 0.");
        }

        if (minesAmount >= rowsAmount * columnsAmount) {
            throw new IllegalArgumentException("Mines amount too high.");
        }

        this.rowsAmount = rowsAmount;
        this.columnsAmount = columnsAmount;
        this.minesAmount = minesAmount;
        this.scoresFilePath = Objects.requireNonNull(scoresFilePath, "Scores file path must not be null");
    }

    public int getRowsAmount() {
        return rowsAmount;
    }

    public void setRowsAmount(int rowsAmount) {
        this.rowsAmount = rowsAmount;
    }

    public int getColumnsAmount() {
        return columnsAmount;
    }

    public void setColumnsAmount(int columnsAmount) {
        this.columnsAmount = columnsAmount;
    }

    public int getMinesAmount() {
        return minesAmount;
    }

    public void setMinesAmount(int minesAmount) {
        this.minesAmount = minesAmount;
    }

    public String getScoresFilePath() {
        return scoresFilePath;
    }

    public void setScoresFilePath(String scoresFilePath) {
        this.scoresFilePath = scoresFilePath;
    }
}