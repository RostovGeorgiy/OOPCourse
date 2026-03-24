package ru.academits.rostov.csv_main;

import ru.academits.rostov.csv.Csv;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Program takes 2 arguments: path to source CSV file, and path to destination HTML file.");
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