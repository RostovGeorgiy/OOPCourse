package ru.academits.rostov.main;

import ru.academits.rostov.person.Person;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>(Arrays.asList(
                new Person("Ivan", 16),
                new Person("Anastasiya", 46),
                new Person("Igor", 17),
                new Person("Evgeniy", 45),
                new Person("Olga", 30),
                new Person("Anastasiya", 19),
                new Person("Ivan", 26)));

        List<String> distinctNamesList = personList.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        String distictNamesString = distinctNamesList.stream()
                .collect(Collectors.joining(", ", "Names: ", "."));
        System.out.println(distictNamesString);

        System.out.print("Average age below 18: ");
        personList.stream().
                mapToInt(Person::getAge).
                filter(age -> age < 18).
                average().ifPresent(System.out::println);

        Map<String, Double> averageAgeMap = personList.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        System.out.println("Contents of a map with names as keys and average ages as values:");
        averageAgeMap.forEach((n, a) -> System.out.printf("Name: %s, average age: %f%n", n, a));

        Predicate<Person> agePredicate = p -> p.getAge() > 20;

        System.out.println("Names of people aged between 20 and 45 sorted by descending age:");

        personList.stream()
                .filter(agePredicate.and(p -> p.getAge() < 45))
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .map(Person::getName)
                .forEach(System.out::println);
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many numbers should be printed from stream?");
        int limit = scanner.nextInt();

        System.out.println("Square roots:");
        DoubleStream.iterate(0, x -> x + 1).map(Math::sqrt).limit(limit).forEach(System.out::println);
    }
}