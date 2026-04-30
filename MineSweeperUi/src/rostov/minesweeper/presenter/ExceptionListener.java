package rostov.minesweeper.presenter;

public interface ExceptionListener {
    void onSuccess(String data);

    void exceptionSent(String exceptionMessage);
}