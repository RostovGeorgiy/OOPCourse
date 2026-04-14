package rostov.minesweeper;

import rostov.minesweeper.gui.DesktopView;
import rostov.minesweeper.gui.View;

public class Main {
    public static void main(String[] args) {
        Model model = new MinesweeperModel();
        View view = new DesktopView();
        Presenter presenter = new Presenter(model, view);
        presenter.start();
    }
}