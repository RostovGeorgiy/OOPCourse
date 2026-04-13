package ru.academits.rostov.csv_main;

import ru.academits.rostov.csv.Csv;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect amount of arguments passed to the program. Program takes 2 String arguments: path to source CSV file, and path to destination HTML file.");
            return;
        }

        try {
            Csv.convertCsvToHtml(args[0], args[1]);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An IO exception has occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}