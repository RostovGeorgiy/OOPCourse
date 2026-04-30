package rostov.minesweeper.model;

import rostov.minesweeper.presenter.ExceptionListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface Model {

    boolean toggleFlag(JButton cell, int remainingFlags);

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

    void writeScore(String playerName, String timeElapsed, String difficulty, ExceptionListener listener);

    void readScores(String difficulty, ExceptionListener listener);

    int getMaxBoardDimension();

    void disableBoard();

    boolean isEnabled(int row, int column);
}