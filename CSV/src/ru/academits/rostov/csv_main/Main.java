package ru.academits.rostov.csv_main;

import ru.academits.rostov.csv.Csv;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Program takes 2 arguments: path to source CSV file, and path to destination HTML file.");
        }

        Csv.convertCsvToHtml(args[0], args[1]);
    }
}