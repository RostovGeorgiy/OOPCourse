package ru.academits.rostov.csv_main;

import ru.academits.rostov.csv.Csv;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Program takes 2 String arguments: path to source CSV file, and path to destination HTML file.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]));
             PrintWriter writer = new PrintWriter(args[1])) {
            Csv.convertCsvToHtml(reader, writer);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An exception has occurred: " + e.getMessage());
        }
    }
}