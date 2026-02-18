package ru.academits.rostov.csv_main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    private static void replaceCharacters(StringBuilder htmlStringBuilder, String target, String replacementString) {
        int targetIndex = htmlStringBuilder.indexOf(target);

        while (targetIndex != -1) {
            htmlStringBuilder.replace(targetIndex, targetIndex + target.length(), replacementString);
            targetIndex = htmlStringBuilder.indexOf(target, targetIndex + replacementString.length());
        }
    }

    private static String convertStringHTML(String htmlString) {
        StringBuilder htmlStringBuilder = new StringBuilder(htmlString);

        replaceCharacters(htmlStringBuilder, "&", "&amp;");

        replaceCharacters(htmlStringBuilder, "<", "&lt;");

        replaceCharacters(htmlStringBuilder, ">", "&gt;");

        return htmlStringBuilder.toString();
    }

    private static String replaceTwinQuotes(String tableCellString) {
        StringBuilder tableCellStringBuilder = new StringBuilder(tableCellString.substring(1, tableCellString.length() - 1));

        replaceCharacters(tableCellStringBuilder, "\"\"", "\"");
        return tableCellStringBuilder.toString();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new FileInputStream("input.txt"));
             PrintWriter writer = new PrintWriter("output.txt")) {

            writer.write("<table>\n");

            boolean isLineBreak = false;

            StringBuilder fileStringBuilder = new StringBuilder();

            int quotesAmount = 0;

            int tableCellStartPosition = 0;

            StringBuilder lineBreakStringBuilder = new StringBuilder();

            while (scanner.hasNextLine()) {

                String fileLine = convertStringHTML(scanner.nextLine());

                if (!isLineBreak) {
                    fileStringBuilder = new StringBuilder(fileLine.length());

                    fileStringBuilder.append("<tr>");

                    tableCellStartPosition = 0;

                    quotesAmount = 0;
                }

                for (int i = 0; i < fileLine.length(); ++i) {
                    if (fileLine.charAt(i) == '"') {
                        quotesAmount += 1;
                    }

                    if (i == fileLine.length() - 1) {
                        if (isLineBreak) {
                            lineBreakStringBuilder.append(fileLine, tableCellStartPosition, i + 1);

                            if (quotesAmount % 2 == 0) {
                                lineBreakStringBuilder.append("<br/>");
                            } else {
                                fileStringBuilder.append(replaceTwinQuotes(lineBreakStringBuilder.toString()));
                                writer.write(fileStringBuilder.toString());

                                quotesAmount = 0;
                                tableCellStartPosition = i + 1;

                                isLineBreak = false;

                                fileStringBuilder.setLength(0);
                                lineBreakStringBuilder.setLength(0);
                            }
                        } else if (quotesAmount > 0 && quotesAmount % 2 == 0) {
                            fileStringBuilder.append("<td>").append(replaceTwinQuotes(fileLine.substring(tableCellStartPosition, i + 1))).append("</td>");
                        } else if (quotesAmount == 0) {
                            fileStringBuilder.append("<td>").append(fileLine, tableCellStartPosition, i + 1).append("</td>");
                        } else {
                            isLineBreak = true;

                            fileStringBuilder.append("<td>");

                            lineBreakStringBuilder.append(fileLine, tableCellStartPosition, i + 1).append("<br/>");

                            tableCellStartPosition = 0;

                            quotesAmount = 0;
                        }
                    } else if (fileLine.charAt(i) == ',' && quotesAmount % 2 != 0 && isLineBreak) {
                        fileStringBuilder.append(replaceTwinQuotes(lineBreakStringBuilder.append(fileLine, tableCellStartPosition, i).toString())).append("</td>");

                        quotesAmount = 0;
                        tableCellStartPosition = i + 1;

                        isLineBreak = false;

                        fileStringBuilder.setLength(0);
                        lineBreakStringBuilder.setLength(0);
                    } else if (fileLine.charAt(i) == ',' && quotesAmount % 2 == 0) {
                        if (quotesAmount > 0) {
                            quotesAmount = 0;

                            fileStringBuilder.append("<td>").append(replaceTwinQuotes(fileLine.substring(tableCellStartPosition, i))).append("</td>");

                            tableCellStartPosition = i + 1;
                        } else if (quotesAmount == 0) {
                            fileStringBuilder.append("<td>").append(fileLine, tableCellStartPosition, i).append("</td>");
                            tableCellStartPosition = i + 1;
                        }
                    }
                }

                if (!isLineBreak) {
                    fileStringBuilder.append("</tr>\n");

                    writer.write(fileStringBuilder.toString());
                }
            }

            writer.write("</table>");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}