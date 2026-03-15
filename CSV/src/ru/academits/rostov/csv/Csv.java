package ru.academits.rostov.csv;

import java.io.*;

public class Csv {
    private static final String INDENT = "    ";

    public static void convertCsvToHtml(String inputFilePath, String outputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             PrintWriter writer = new PrintWriter(outputFilePath)) {

            writer.println("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>CSV to HTML.</title>
                    </head>
                    <body>
                    <h1>HTML file containing table converted from CSV file.</h1>
                    <table border="1">""");

            boolean isLineBreak = false;

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                int quotesAmount = 0;

                String newLine = System.lineSeparator();

                if (!isLineBreak) {
                    writer.print(INDENT + "<tr>");
                    writer.print(newLine + INDENT.repeat(2) + "<td>");
                }

                for (int i = 0; i < line.length(); ++i) {
                    if (line.charAt(i) == '<') {
                        writer.print("&lt;");
                    } else if (line.charAt(i) == '>') {
                        writer.print("&gt;");
                    } else if (line.charAt(i) == '&') {
                        writer.print("&amp;");
                    } else if (line.charAt(i) == '"') {
                        quotesAmount++;

                        if (i == line.length() - 1) {
                            if (quotesAmount % 2 == 0 && isLineBreak) {
                                writer.print("<br>");
                            } else {
                                writer.print("</td>" + newLine);
                                writer.println(INDENT + "</tr>");
                                isLineBreak = false;
                            }

                            quotesAmount = 0;
                        } else if (line.charAt(i + 1) == '"') {
                            writer.print(line.charAt(i));
                            ++i;
                            quotesAmount++;
                        }
                    } else if (line.charAt(i) == ',') {
                        if (i == 0) {
                            writer.println("</td>");
                            writer.print(INDENT.repeat(2) + "<td>");
                        } else if (quotesAmount % 2 != 0) {
                            writer.print(line.charAt(i));

                            if (i == line.length() - 1) {
                                writer.print("<br>");
                                isLineBreak = true;
                            }
                        } else if (i == line.length() - 1 && !isLineBreak) {
                            writer.println("</td>" + newLine + INDENT.repeat(2) + "<td>" + "</td>");
                            writer.println(INDENT + "</tr>");
                            quotesAmount = 0;
                        } else {
                            writer.print("</td>" + newLine);
                            writer.print(INDENT.repeat(2) + "<td>");
                            quotesAmount = 0;
                        }
                    } else if (i == line.length() - 1) {
                        writer.print(line.charAt(i));

                        if (!isLineBreak && quotesAmount % 2 == 0) {
                            writer.print("</td>" + newLine);
                            writer.println(INDENT + "</tr>");

                            quotesAmount = 0;
                        } else {
                            writer.print("<br>");
                            isLineBreak = true;
                        }
                    } else {
                        writer.print(line.charAt(i));
                    }
                }
            }

            writer.print("""
                    </table>
                    </body>
                    </html>""");

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An exception has occurred: " + e.getMessage());
        }
    }
}