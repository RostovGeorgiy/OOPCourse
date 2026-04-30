package rostov.minesweeper.model;

public class ScoreRecord {
    private final String playerName;
    private final int playerScore;

    public ScoreRecord(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public String toString() {
        return "Name: " + playerName + ", Score: " + playerScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        ScoreRecord s = (ScoreRecord) o;

        return playerScore == s.playerScore;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + playerScore;
        hash = prime * hash + playerName.hashCode();

        return hash;
    }
}