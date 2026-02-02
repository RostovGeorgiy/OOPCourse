package ru.academits.rostov.range_main;

import ru.academits.rostov.range.Range;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(10, 15);

        System.out.printf("Длина диапазона: %f%n", range1.getLength());

        System.out.printf("Находится ли число 12.9 в диапазоне от %f до %f:%n", range1.getFrom(), range1.getTo());
        System.out.println(range1.isInside(12.9));

        range1.setFrom(6);
        range1.setTo(10);

        System.out.printf("Изменили диапазон. Текущие значения: %f - %f%n", range1.getTo(), range1.getFrom());
        System.out.println("Проверяем вхождение числа 12.9 в диапазон: " + range1.isInside(12.9));

        Range range2 = new Range(8, 11);

        System.out.println("Пересечение двух интервалов:");
        Range intersection = range1.getIntersection(range2);

        if (intersection != null) {
            System.out.printf("%f - %f%n", intersection.getFrom(), intersection.getTo());
        } else {
            System.out.println("Пересечения нет.");
        }

        System.out.println("Объединение двух интервалов:");
        Range[] union = range1.getUnion(range2);

        if (union.length == 2) {
            System.out.printf("%f - %f, %f - %f%n", union[0].getFrom(), union[0].getTo(),
                    union[1].getFrom(), union[1].getTo());
        } else if (union.length == 1) {
            System.out.printf("%f - %f%n", union[0].getFrom(), union[0].getTo());
        }

        System.out.println("Разность двух интервалов:");
        Range[] difference = range1.getDifference(range2);

        if (difference.length == 2) {
            System.out.printf("%f - %f, %f - %f%n", difference[0].getFrom(), difference[0].getTo(),
                    difference[1].getFrom(), difference[1].getTo());
        } else if (difference.length == 1) {
            System.out.printf("%f - %f%n", difference[0].getFrom(), difference[0].getTo());
        } else {
            System.out.println("Разность равна 0");
        }
    }
}