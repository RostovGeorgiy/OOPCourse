package ru.academits.rostov.arraylisthome;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            List<String> fileLinesList = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                fileLinesList.add(line);
            }

            System.out.println("List containing file lines: " + fileLinesList);
        } catch (IOException e) {
            System.out.println("An exception has occurred: " + e.getMessage());
        }

        List<Integer> integersList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

        System.out.println("integersList: " + integersList);

        for (int i = integersList.size() - 1; i >= 0; --i) {
            if (integersList.get(i) % 2 == 0) {
                integersList.remove(i);
            }
        }

        System.out.println("integersList with even numbers removed: " + integersList);

        List<Integer> duplicateItemsList = new ArrayList<>(Arrays.asList(1, 2, 2, 4, 9, 5, 5, 1));
        List<Integer> uniqueItemsList = new ArrayList<>(duplicateItemsList.size());

        for (Integer item : duplicateItemsList) {
            if (!uniqueItemsList.contains(item)) {
                uniqueItemsList.add(item);
            }
        }

        System.out.println("List with duplicate items: " + duplicateItemsList);
        System.out.println("List with unique items: " + uniqueItemsList);
    }
}