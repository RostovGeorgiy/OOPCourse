package ru.academits.rostov.csv;

import java.io.*;

public class Csv {
    private static final String INDENT = "    ";

    public static void convertCsvToHtml(BufferedReader reader, PrintWriter writer) throws IOException {
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
        String newLine = System.lineSeparator();

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                if (isLineBreak) {
                    writer.print("<br>");
                } else {
                    continue;
                }
            }

            int quotesAmount = 0;

            if (!isLineBreak) {
                writer.print(INDENT + "<tr>");
                writer.print(newLine + INDENT.repeat(2) + "<td>");
            }

            for (int i = 0; i < line.length(); ++i) {
                char currentCharacter = line.charAt(i);

                if (currentCharacter == '<') {
                    writer.print("&lt;");
                } else if (currentCharacter == '>') {
                    writer.print("&gt;");
                } else if (currentCharacter == '&') {
                    writer.print("&amp;");
                } else if (currentCharacter == '"') {
                    quotesAmount++;

                    if (i == line.length() - 1) {
                        if (quotesAmount % 2 == 0 && isLineBreak) {
                            writer.print("<br>");
                        } else {
                            writer.println("</td>");
                            writer.println(INDENT + "</tr>");
                            isLineBreak = false;
                        }

                        quotesAmount = 0;
                    } else if (line.charAt(i + 1) == '"') {
                        writer.print(currentCharacter);
                        ++i;
                        quotesAmount++;
                    }
                } else if (currentCharacter == ',') {
                    if (i == 0) {
                        writer.println("</td>");
                        writer.print(INDENT.repeat(2) + "<td>");
                    } else if (quotesAmount % 2 != 0) {
                        writer.print(currentCharacter);

                        if (i == line.length() - 1) {
                            writer.print("<br>");
                            isLineBreak = true;
                        }
                    } else if (i == line.length() - 1 && !isLineBreak) {
                        writer.println("</td>" + newLine + INDENT.repeat(2) + "<td></td>");
                        writer.println(INDENT + "</tr>");
                        quotesAmount = 0;
                    } else {
                        writer.println("</td>");
                        writer.print(INDENT.repeat(2) + "<td>");
                        quotesAmount = 0;
                    }
                } else if (i == line.length() - 1) {
                    writer.print(currentCharacter);

                    if (!isLineBreak && quotesAmount % 2 == 0) {
                        writer.println("</td>");
                        writer.println(INDENT + "</tr>");

                        quotesAmount = 0;
                    } else {
                        writer.print("<br>");
                        isLineBreak = true;
                    }
                } else {
                    writer.print(currentCharacter);
                }
            }
        }

        writer.print("""
                </table>
                </body>
                </html>""");
    }
}