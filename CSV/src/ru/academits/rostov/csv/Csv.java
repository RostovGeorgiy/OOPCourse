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
        String doubleIndent = INDENT.repeat(2);

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                if (isLineBreak) {
                    writer.print("<br>");
                }

                continue;
            }

            int quotesAmount = 0;

            if (!isLineBreak) {
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
                    quotesAmount++;

                    if (i == line.length() - 1) {
                        {
                            writer.println("</td>");
                            writer.print(INDENT);
                            writer.println("</tr>");

                            if (quotesAmount % 2 != 0) {
                                isLineBreak = false;
                            }
                        }

                        quotesAmount = 0;
                    } else if (line.charAt(i + 1) == '"') {
                        writer.print(currentCharacter);
                        ++i;
                        quotesAmount++;
                    }
                } else if (currentCharacter == ',') {
                    if (quotesAmount % 2 != 0) {
                        writer.print(currentCharacter);
                    } else if (i == line.length() - 1 && !isLineBreak) {
                        writer.println("</td>");
                        writer.print(doubleIndent);
                        writer.println("<td></td>");
                        writer.print(INDENT);
                        writer.println("</tr>");

                        quotesAmount = 0;
                    } else {
                        writer.println("</td>");
                        writer.print(doubleIndent);
                        writer.print("<td>");

                        quotesAmount = 0;
                    }
                } else if (i == line.length() - 1) {
                    writer.print(currentCharacter);

                    if (!isLineBreak && quotesAmount % 2 == 0) {
                        writer.println("</td>");
                        writer.print(INDENT);
                        writer.println("</tr>");

                        quotesAmount = 0;
                    }
                } else {
                    writer.print(currentCharacter);
                }
            }

            if (quotesAmount % 2 != 0 || (quotesAmount == 0 && isLineBreak)) {
                writer.print("<br>");
                isLineBreak = true;
            }
        }

        writer.print("""
                </table>
                </body>
                </html>""");
    }
}