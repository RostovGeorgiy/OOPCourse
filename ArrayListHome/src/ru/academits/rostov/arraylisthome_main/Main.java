package ru.academits.rostov.arraylisthome_main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileInputStream("input.txt"))) {
            List<String> fileLineList = new ArrayList<>();

            while (scanner.hasNext()) {
                fileLineList.add(scanner.next());
            }

            System.out.println(fileLineList);
        }

        List<Integer> integerList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));

        integerList.removeIf(item -> item % 2 == 0);

        System.out.println(integerList);

        List<Integer> duplicateItemsList = new ArrayList<>(Arrays.asList(1, 2, 2, 4, 9, 5, 5, 1));

        List<Integer> uniqueItemsList = new ArrayList<>();

        for (Integer item : duplicateItemsList) {
            if (!uniqueItemsList.contains(item)) {
                uniqueItemsList.add(item);
            }
        }

        System.out.println(uniqueItemsList);
    }
}