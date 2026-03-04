package ru.academits.rostov.csv_main;

import ru.academits.rostov.csv.Csv;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String inputFilePath = args[0];
        String outputFilPath = args[1];

        try (Scanner scanner = new Scanner(new FileInputStream(inputFilePath));
             PrintWriter writer = new PrintWriter(outputFilPath)) {

            writer.println("""
                    <!DOCTYPE html>
                    <html lang = "en">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>CSV to HTML.</title>
                    </head>
                    <body>
                    <h1>HTML file containing table converted from CSV file.</h1>
                    <table border="1">""");

            ArrayList<String> result = new ArrayList<>();

            boolean isLineBreak = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                result.addAll(Csv.convertFileLine(line, isLineBreak));

                if (result.getLast().equals("    </tr>")) {
                    writer.println(String.join("", result));

                    isLineBreak = false;

                    result.clear();
                } else {
                    isLineBreak = true;
                }
            }

            writer.print("""
                    </table>
                    </body>
                    </html>""");

        } catch (FileNotFoundException e) {
            System.out.println("An exception has occurred: " + e.getMessage());
        }
    }
}