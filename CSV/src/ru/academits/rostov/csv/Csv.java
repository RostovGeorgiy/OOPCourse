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

        boolean isQuotesCell = false;

        String line;
        String doubleIndent = INDENT.repeat(2);

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                if (isQuotesCell) {
                    writer.print("<br>");
                }

                continue;
            }

            if (!isQuotesCell) {
                writer.print(INDENT);
                writer.println("<tr>");
                writer.print(doubleIndent);
                writer.print("<td>");
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
                    if (isQuotesCell) {
                        if (i != line.length() - 1 && line.charAt(i + 1) == '"') {
                            writer.print(currentCharacter);
                            ++i;
                        } else {
                            isQuotesCell = false;

                            if (i == line.length() - 1) {
                                writer.println("</td>");
                                writer.print(INDENT);
                                writer.println("</tr>");
                            }
                        }
                    } else {
                        isQuotesCell = true;
                    }
                } else if (currentCharacter == ',') {
                    if (isQuotesCell) {
                        writer.print(currentCharacter);
                    } else if (i == line.length() - 1) {
                        writer.println("</td>");
                        writer.print(doubleIndent);
                        writer.println("<td></td>");
                        writer.print(INDENT);
                        writer.println("</tr>");
                    } else {
                        writer.println("</td>");
                        writer.print(doubleIndent);
                        writer.print("<td>");
                    }
                } else if (i == line.length() - 1) {
                    writer.print(currentCharacter);

                    if (!isQuotesCell) {
                        writer.println("</td>");
                        writer.print(INDENT);
                        writer.println("</tr>");
                    } else {
                        writer.print("<br>");
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