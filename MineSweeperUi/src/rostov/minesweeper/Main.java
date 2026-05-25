package rostov.minesweeper;

import javax.swing.*;

import rostov.minesweeper.gui.DesktopView;
import rostov.minesweeper.gui.View;
import rostov.minesweeper.model.Difficulty;
import rostov.minesweeper.model.MinesweeperModel;
import rostov.minesweeper.model.Model;
import rostov.minesweeper.presenter.Presenter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Difficulty> difficultiesList = List.of(
                new Difficulty("beginner", 9, 9, 10,
                        "MineSweeperUi/src/rostov/minesweeper/beginnerHighscores.txt"),
                new Difficulty("intermediate", 16, 16, 40,
                        "MineSweeperUi/src/rostov/minesweeper/intermediateHighscores.txt"),
                new Difficulty("expert", 16, 30, 99,
                        "MineSweeperUi/src/rostov/minesweeper/expertHighscores.txt"));

        SwingUtilities.invokeLater(() -> {
            try {
                Model model = new MinesweeperModel(difficultiesList);
                View view = new DesktopView();
                Presenter presenter = new Presenter(model, view);
                presenter.start();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error on launch: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                System.exit(1);
            }
        });
    }
}