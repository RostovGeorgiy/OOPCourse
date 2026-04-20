package rostov.minesweeper.model;

public class Cell {
    private boolean isRevealed;
    private boolean isMine;
    private boolean isFlagged;
    private int nearbyMinesAmount;

    public Cell() {
    }

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

    public int getNearbyMinesAmount() {
        return nearbyMinesAmount;
    }

    public void setNearbyMinesAmount(int nearbyMinesAmount) {
        this.nearbyMinesAmount = nearbyMinesAmount;
    }
}