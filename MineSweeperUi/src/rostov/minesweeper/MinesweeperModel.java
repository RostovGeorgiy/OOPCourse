package rostov.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MinesweeperModel implements Model {
    private ImageIcon isFlagSet;

    private final ImageIcon flagIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rostov/minesweeper/resources/flag.png")));

    private final InputStream scoresFileInputStream = getClass().getClassLoader().getResourceAsStream("/rostov/minesweeper/resources/highscores.txt");

    private static int[][] board;
    private static boolean[][] isRevealed;
    private static boolean[][] isFlaggedArray;
    private static int[][] minesCount;
    private int rowsAmount;
    private int columnsAmount;

    private Instant startTime;
    private long gameScore;

    public void setMines(int rowsAmount, int columnsAmount, int minesAmount) {
        this.rowsAmount = rowsAmount;
        this.columnsAmount = columnsAmount;

        board = new int[rowsAmount][columnsAmount];
        isRevealed = new boolean[rowsAmount][columnsAmount];
        isFlaggedArray = new boolean[rowsAmount][columnsAmount];

        Random random = new Random();

        while (minesAmount > 0) {
            int position = random.nextInt(rowsAmount * columnsAmount);
            int row = position % rowsAmount;
            int column = position / columnsAmount;

            if (board[row][column] == -1) {
                continue;
            } else {
                board[row][column] = -1;
            }

            minesAmount--;
        }
    }

    @Override
    public void toggleFlag(JButton flaggedCell, int remainingFlags) {
        String[] cellPosition = flaggedCell.getActionCommand().split(",");
        int row = Integer.parseInt(cellPosition[0]);
        int column = Integer.parseInt(cellPosition[1]);

        if (isRevealed(row, column)) {
            return;
        }

        if (flaggedCell.getIcon() == null && remainingFlags > 0) {
            isFlagSet = flagIcon;
            isFlaggedArray[row][column] = true;
        } else if (flaggedCell.getIcon() != null) {
            flaggedCell.setIcon(null);
            isFlagSet = null;
            isFlaggedArray[row][column] = false;
        }

    }

    @Override
    public ImageIcon getFlagIcon() {
        return isFlagSet;
    }

    @Override
    public boolean isRevealed(JButton cell) {
        String[] cellPosition = cell.getActionCommand().split(",");
        int row = Integer.parseInt(cellPosition[0]);
        int column = Integer.parseInt(cellPosition[1]);

        return isRevealed[row][column];
    }

    @Override
    public boolean isRevealed(int row, int column) {
        return isRevealed[row][column];
    }

    @Override
    public boolean isFlagged(JButton cell) {
        return Objects.equals(cell.getIcon(), flagIcon);
    }

    @Override
    public boolean isFlagged(int row, int column) {
        return isFlaggedArray[row][column];
    }

    @Override
    public boolean isMine(int row, int column) {
        return board[row][column] == -1;
    }

    @Override
    public ArrayList<Point> getMines() {
        ArrayList<Point> minesPositions = new ArrayList<>();

        for (int row = 0; row < board.length; ++row) {
            for (int column = 0; column < board[0].length; ++column) {
                if (board[row][column] == -1) {
                    minesPositions.add(new Point(row, column));
                }
            }
        }

        return minesPositions;
    }

    @Override
    public void resetBoard() {
        startTime = Instant.now();

        if (board == null) {
            return;
        }

        for (int row = 0; row < board.length; ++row) {
            for (int column = 0; column < board[0].length; ++column) {
                board[row][column] = 0;
                isRevealed[row][column] = false;
            }
        }
    }

    public int countNearbyMines(int row, int column) {
        int count = 0;

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (r >= 0 && r < rowsAmount && c >= 0 && c < columnsAmount) {
                    if (board[r][c] == -1) {
                        count++;
                    }
                }
            }
        }

        if (board[row][column] == -1) {
            count--;
        }

        return count;
    }

    @Override
    public void countAllNearbyMines() {
        minesCount = new int[rowsAmount][columnsAmount];

        for (int row = 0; row < rowsAmount; row++) {
            for (int column = 0; column < columnsAmount; column++) {
                if (board[row][column] != -1) {
                    minesCount[row][column] = countNearbyMines(row, column);
                } else {
                    minesCount[row][column] = -1;
                }
            }
        }
    }

    @Override
    public void revealNearbyCells(int row, int column) {
        if (row < 0 || row >= rowsAmount || column < 0 || column >= columnsAmount || isRevealed[row][column]) {
            return;
        }

        setRevealed(row, column);

        if (minesCount[row][column] == 0 && board[row][column] != -1) {
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = column - 1; c <= column + 1; c++) {
                    revealNearbyCells(r, c);
                }
            }
        }
    }

    @Override
    public void setRevealed(int row, int column) {
        isRevealed[row][column] = true;
    }

    @Override
    public boolean checkWin() {
        for (int row = 0; row < rowsAmount; row++) {
            for (int column = 0; column < columnsAmount; column++) {
                if (!isRevealed(row, column) && !isMine(row, column)) {
                    return false;
                }
            }
        }

        Instant winTime = Instant.now();

        gameScore = Duration.between(startTime, winTime).getSeconds();

        return true;
    }

    @Override
    public void writeScore(String playerName) {
        File file = new File(highScoreFilePath);

        try (PrintWriter writer = new PrintWriter(new FileWriter(String.valueOf(scoresFileInputStream), true))) {

            if (file.length() == 0) {
                writer.println("Player name:  Score(seconds to beat the game):");
                writer.println("------------------------");
            }

            writer.println();
            writer.print(playerName + ":\t ");
            writer.print(gameScore > 0 ? gameScore : '1');
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String readScores() {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(scoresFileInputStream)))) {
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return stringBuilder.toString();
    }
}