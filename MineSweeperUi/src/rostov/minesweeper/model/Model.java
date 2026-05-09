package rostov.minesweeper.model;

import rostov.minesweeper.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface Model {

    boolean toggleFlag(int row, int column, int remainingFlags);

    boolean isRevealed(int row, int column);

    boolean isFlagged(int newRow, int newColumn);

    boolean isMine(int row, int column);

    ArrayList<Point> getMines();

    void resetBoard(int rowsAmount, int columnsAmount);

    int getRowsAmount();

    int getColumnsAmount();

    void setMines(int rowsAmount, int columnsAmount, int minesAmount, int clickedCellRow, int clickedCellColumn);

    int countNearbyMines(int row, int column);

    int countNearbyFlags(int row, int column);

    void countAllNearbyMines();

    void revealNearbyCells(int row, int column);

    void setRevealed(int row, int column);

    boolean checkWin();

    void writeScore(String playerName, String timeElapsed, Difficulty difficulty) throws Exception;

    String readScores(Difficulty difficulty) throws Exception;

    int getMaxBoardDimension();

    void disableBoard();

    boolean isEnabled(int row, int column);

    ImageIcon getIcon(int row, int column);

    Difficulty getDifficulty(String difficultyName);
}