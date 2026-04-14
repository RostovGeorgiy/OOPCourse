package rostov.minesweeper.gui;

import rostov.minesweeper.Presenter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface View {
    void start();

    void showInputErrorMessage();

    void showBoardSizeErrorMessage();

    void setController(Presenter presenter);

    void showToggledFlag(JButton cell, ImageIcon icon);

    void showGameOverMessage(String s);

    void revealAllMines(ArrayList<Point> minesPositions);

    void resetBoard();

    void updateCell(int row, int column, String s);

    void showAboutMessage();

    void showHighScoresTable(String scores);

    void exitGame();

    void showWinMessage(String s);
}