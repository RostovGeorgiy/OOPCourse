package rostov.minesweeper.model;

public class Cell {
    private boolean isRevealed;
    private boolean isMine;
    private boolean isFlagged;
    private boolean isEnabled;
    private int nearbyMinesAmount;

    public boolean getRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }

    public boolean getMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean getFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    public boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getNearbyMinesAmount() {
        return nearbyMinesAmount;
    }

    public void setNearbyMinesAmount(int nearbyMinesAmount) {
        this.nearbyMinesAmount = nearbyMinesAmount;
    }
}