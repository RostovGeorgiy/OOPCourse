package rostov.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface Model {

    void toggleFlag(JButton cell, int remainingFlags);

    ImageIcon getFlagIcon();

    boolean isRevealed(JButton cell);

    boolean isRevealed(int row, int column);

    boolean isFlagged(JButton cell);

    boolean isFlagged(int newRow, int newColumn);

    boolean isMine(int row, int column);

    ArrayList<Point> getMines();

    void resetBoard();

    void setMines(int rowsAmount, int columnsAmount, int minesAmount);

    default int countNearbyMines(int row, int column) {
        return 0;
    }

    void countAllNearbyMines();

    void revealNearbyCells(int row, int column);


    void setRevealed(int row, int column);

    boolean checkWin();

    void writeScore(String playerName);

    String readScores();
}