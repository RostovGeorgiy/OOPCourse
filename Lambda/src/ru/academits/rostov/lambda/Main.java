package ru.academits.rostov.lambda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main {
    public static void main(String[] args) {
        List<Person> personsList = Arrays.asList(
                new Person("Ivan", 16),
                new Person("Anastasiya", 18),
                new Person("Igor", 17),
                new Person("Evgeniy", 45),
                new Person("Olga", 30),
                new Person("Anastasiya", 19),
                new Person("Ivan", 48));

        List<String> distinctNamesList = personsList.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        String distictNamesString = distinctNamesList.stream()
                .collect(Collectors.joining(", ", "Names: ", "."));
        System.out.println(distictNamesString);

        System.out.print("Average age below 18: ");
        personsList.stream()
                .mapToInt(Person::getAge)
                .filter(age -> age < 18)
                .average()
                .ifPresentOrElse(System.out::println, () -> System.out.println("No people aged below 18 are in the list."));

        Map<String, Double> averageAgesByNamesMap = personsList.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        System.out.println("Contents of a map with names as keys and average ages as values:");
        averageAgesByNamesMap.forEach((n, a) -> System.out.printf("Name: %s, average age: %f%n", n, a));

        System.out.println("Names of people aged between 20 and 45 sorted by descending age:");

        personsList.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .map(Person::getName)
                .forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);

        System.out.println("How many square roots should be printed from stream? (>= 0)");
        int squareRootsAmount = scanner.nextInt();

        if (squareRootsAmount < 0) {
            return;
        }

        System.out.println("Square roots:");
        DoubleStream.iterate(0, x -> x + 1)
                .map(Math::sqrt)
                .limit(squareRootsAmount)
                .forEach(System.out::println);
    }
}