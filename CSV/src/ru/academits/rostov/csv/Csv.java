package ru.academits.rostov.csv;

import java.util.ArrayList;

public class Csv {
    public static String convertHtmlEscapeCharacters(String line) {
        StringBuilder stringbuilder = new StringBuilder(line.length());

        for (int i = 0; i < line.length(); ++i) {
            if (line.charAt(i) == '<') {
                stringbuilder.append("&lt;");
            } else if (line.charAt(i) == '>') {
                stringbuilder.append("&gt;");
            } else if (line.charAt(i) == '&') {
                stringbuilder.append("&amp;");
            } else {
                stringbuilder.append(line.charAt(i));
            }
        }

        return stringbuilder.toString();
    }

    public static ArrayList<String> convertFileLine(String line, boolean isLineBreak) {
        line = convertHtmlEscapeCharacters(line);

        ArrayList<String> result = new ArrayList<>();

        int quotesAmount = 0;

        String newLine = System.lineSeparator();

        if (!isLineBreak) {
            result.add("    <tr>");
            result.add(newLine + "        <td>");
        }

        for (int i = 0; i < line.length(); ++i) {
            if (line.charAt(i) == '"') {
                quotesAmount++;

                if (i == line.length() - 1) {
                    result.add("</td>" + newLine);
                    result.add("    </tr>");
                    quotesAmount = 0;
                } else if (line.charAt(i + 1) == '"') {
                    result.add(String.valueOf(line.charAt(i)));
                    ++i;
                    quotesAmount++;
                }
            } else if (line.charAt(i) == ',') {
                if (quotesAmount % 2 == 0) {
                    result.add("</td>" + newLine);
                    result.add("        <td>");
                    quotesAmount = 0;
                } else {
                    result.add(String.valueOf(line.charAt(i)));
                }
            } else if (i == line.length() - 1) {
                result.add(String.valueOf(line.charAt(i)));

                if (!isLineBreak && quotesAmount % 2 == 0) {
                    result.add("</td>" + newLine);
                    result.add("    </tr>");

                    quotesAmount = 0;
                } else {
                    result.add("<br>");
                    isLineBreak = false;
                }
            } else {
                result.add(String.valueOf(line.charAt(i)));
            }
        }

        return result;
    }
}