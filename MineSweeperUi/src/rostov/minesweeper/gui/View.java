package rostov.minesweeper.gui;

import rostov.minesweeper.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface View {
    void start();

    void showInputIsNonNumericMessage();

    void showInputErrorMessage();

    void showBoardSizeErrorMessage();

    void setPresenter(Presenter presenter);

    void showToggledFlag(JButton cell);

    void showGameOverMessage(String s);

    void revealAllMines(ArrayList<Point> minesPositions);

    void resetBoard(int boardRowsAmount, int boardColumnsAmount);

    void updateCell(int row, int column, String s);

    void showAboutMessage();

    void showHighScoresTable(String scores);

    void exitGame();

    void showWinMessage(String s);
}