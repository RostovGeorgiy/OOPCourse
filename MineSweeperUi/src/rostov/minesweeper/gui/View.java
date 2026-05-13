package rostov.minesweeper.gui;

import rostov.minesweeper.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface View {
    void start();

    ImageIcon setIconPath(String iconPath);

    void showInputErrorMessage();

    void showBoardSizeErrorMessage();

    void setPresenter(Presenter presenter);

    void showToggledFlag(int row, int column);

    void revealAllMines(ArrayList<Point> minesPositions);

    void resetBoard(int boardRowsAmount, int boardColumnsAmount);

    void updateCell(int row, int column, String s);

    void showAboutMessage();

    void showHighScoresTable(String scores);

    void exitGame();

    void showWinMessage();

    void showError(String exceptionMessage);

    void showHighlightedMine(int row, int column);

    void stopTimer();

    boolean isIconNonNull(int row, int column);
}