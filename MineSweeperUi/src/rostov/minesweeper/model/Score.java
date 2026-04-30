package rostov.minesweeper.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import rostov.minesweeper.presenter.ExceptionListener;

public class Score {
    public static void read(String scoresFilePath, ExceptionListener listener) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(scoresFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] recordParts = line.split(":");

                String name = recordParts[0].trim();
                int score = Integer.parseInt(recordParts[1].trim());

                ScoreRecord scoreRecord = new ScoreRecord(name, score);
                stringBuilder.append(scoreRecord).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            listener.exceptionSent("File not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            listener.exceptionSent("IO error: " + e.getMessage());
            return;
        } catch (Exception e) {
            listener.exceptionSent("Error: " + e.getMessage());
            return;
        }

        listener.onSuccess(stringBuilder.toString());
    }

    public static void write(String scoresFilePath, String playerName, int gameScore, String difficulty, ExceptionListener listener) {
        File file = new File(scoresFilePath);

        if (playerName.isEmpty()) {
            playerName = "Guest";
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println();
            writer.print(playerName + ":");
            writer.print(gameScore > 0 ? gameScore : 1);
        } catch (FileNotFoundException e) {
            listener.exceptionSent("File not found: " + e.getMessage());
        } catch (IOException e) {
            listener.exceptionSent("IO error: " + e.getMessage());
        } catch (Exception e) {
            listener.exceptionSent("Error: " + e.getMessage());
        }

        getTopTenScores(scoresFilePath, listener);
    }

    private static void getTopTenScores(String scoresFilePath, ExceptionListener listener) {

        List<ScoreRecord> topTenScores;

        try (BufferedReader reader = new BufferedReader(new FileReader(scoresFilePath))) {
            ArrayList<ScoreRecord> scores = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] recordParts = line.split(":");

                String name = recordParts[0].trim();
                int score = Integer.parseInt(recordParts[1].trim());

                scores.add(new ScoreRecord(name, score));
            }

            scores.sort(Comparator.comparingInt(ScoreRecord::getPlayerScore));

            topTenScores = scores.stream()
                    .limit(10)
                    .toList();

        } catch (FileNotFoundException e) {
            listener.exceptionSent("File not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            listener.exceptionSent("IO error: " + e.getMessage());
            return;
        } catch (Exception e) {
            listener.exceptionSent("Error: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoresFilePath))) {
            int scoreCounter = 0;

            for (ScoreRecord record : topTenScores) {
                scoreCounter++;

                StringBuilder stringBuilder = new StringBuilder();
                writer.write(stringBuilder.append(record.getPlayerName()).append(":").append(record.getPlayerScore()).toString());

                if (scoreCounter < 10) {
                    writer.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            listener.exceptionSent("File not found: " + e.getMessage());
        } catch (IOException e) {
            listener.exceptionSent("IO error: " + e.getMessage());
        } catch (Exception e) {
            listener.exceptionSent("Error: " + e.getMessage());
        }
    }
}