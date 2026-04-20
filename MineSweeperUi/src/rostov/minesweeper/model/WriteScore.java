package rostov.minesweeper.model;

import java.io.*;

public class WriteScore {
    public static void write(String scoresFilePath, String playerName, long gameScore) {

        File file = new File(String.valueOf(scoresFilePath));

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            if (file.length() == 0) {
                writer.println("Player name:  Score(seconds to beat the game):");
                writer.println("------------------------");
            }

            writer.println();
            writer.print(playerName + ":\t ");
            writer.print(gameScore > 0 ? gameScore : 1);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}