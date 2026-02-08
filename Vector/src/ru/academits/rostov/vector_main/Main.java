package ru.academits.rostov.vector_main;

import ru.academits.rostov.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(2, new double[]{1, 2, 3});
        Vector vector2 = new Vector(new double[]{3, 3, 3, 3, 3});

        System.out.println("Adding vectors.");
        vector1.add(vector2);

        System.out.println("Result vector: " + vector1);
        System.out.println("vector1 size: " + vector1.getSize());

        System.out.println("Subtracting vectors.");
        vector1.subtract(vector2);
        System.out.println("Result vector: " + vector1);

        System.out.println("Multiplying vectors.");
        vector1.multiplyByScalar(2);
        System.out.println("vector1 contents after multiplication: " + vector1);

        System.out.println("Flipping vector.");
        vector2.flip();
        System.out.println("Flipped vector2. Contents: " + vector2);

        System.out.println("Vector1 length: " + vector1.getLength());

        System.out.println("Vector1 contents in index 4: " + vector1.getComponentByIndex(4));

        vector1.setComponentByIndex(1, 2.87);
        System.out.println("Vector1 with value 2.87 in index 1: " + vector1);

        System.out.println("Testing vectors equals method: " + vector1.equals(new Vector(new double[]{8.0, 2.87, 12.0, 6.0, 5.0})));

        System.out.println("Static sum method: " + Vector.getSum(
                new Vector(new double[]{2.0, 2.0}),
                new Vector(new double[]{4.0, 4.0, 4.0, 4.0, 4.0})));

        System.out.println("Static difference method: " + Vector.getDifference(
                new Vector(new double[]{4.0, 4.0, 4.0, 4.0, 4.0}),
                new Vector(new double[]{2.0, 2.0})));

        System.out.println("Static scalar multiplication method: " + Vector.getScalarMultiplication(
                new Vector(new double[]{2.0, 2.0}),
                new Vector(new double[]{4.0, 4.0, 4.0, 4.0, 4.0})));
    }
}