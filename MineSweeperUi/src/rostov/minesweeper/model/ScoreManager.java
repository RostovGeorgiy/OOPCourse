package rostov.minesweeper.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreManager {
    public static final int TOP_SCORES_AMOUNT = 10;

    public static String read(String scoresFilePath) throws Exception {
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
        }

        return stringBuilder.toString();
    }

    public static void write(String scoresFilePath, String playerName, int gameScore) throws Exception {
        File file = new File(scoresFilePath);

        if (playerName.isEmpty()) {
            playerName = "Guest";
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (file.length() != 0) {
                writer.println();
            }

            writer.print(playerName + ":");
            writer.print(gameScore > 0 ? gameScore : 1);
        }

        sortTopScores(scoresFilePath);
    }

    private static void sortTopScores(String scoresFilePath) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(scoresFilePath))) {
            ArrayList<ScoreRecord> scores = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] recordParts = line.split(":");

                String name = recordParts[0].trim();
                int score = Integer.parseInt(recordParts[1].trim());

                scores.add(new ScoreRecord(name, score));

                System.out.println(scores);
            }

            if (scores.isEmpty()) {
                return;
            }

            scores.sort(Comparator.comparingInt(ScoreRecord::getPlayerScore));

            List<ScoreRecord> topScores = scores.stream()
                    .limit(TOP_SCORES_AMOUNT)
                    .toList();

            int scoresAmount = 0;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoresFilePath))) {
                for (ScoreRecord record : topScores) {
                    scoresAmount++;

                    writer.write(record.toString());

                    if (scoresAmount < TOP_SCORES_AMOUNT) {
                        writer.newLine();
                    }
                }
            }
        }
    }
}