package ru.academits.rostov.vector_main;

import ru.academits.rostov.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector v = new Vector(4, new double[]{1, 2, 3});
        Vector v1 = new Vector(new double[]{3, 3, 3, 3, 3});

        System.out.println("Adding vectors.");
        v.addVectors(v1);

        System.out.println("Result vector v: " + v);
        System.out.println("v Size: " + v.getSize());

        System.out.println("Subtracting vectors.");
        v.subtractFromVector(v1);
        System.out.println("Result vector v: " + v);

        System.out.println("Multiplying vectors.");
        v.scalarMultiplication(2);
        System.out.println("v Contents after multiplication: " + v);

        System.out.println("Flipping vector.");
        v1.vectorFlip();
        System.out.println("Flipped vector v1. Contents: " + v1);

        System.out.println("Vector v length: " + v.getVectorLength());

        System.out.println("Vector contents in position 4: " + v.getContentsByPosition(4));

        v.setContentsByPosition(2.87, 1);
        System.out.println("Vector v with value 2.87 in position 1: " + v);

        System.out.println("Testing vectors equals method: " + v.equals(new Vector(new double[]{8.0, 2.87, 12.0, 6.0, 5.0})));

        System.out.println("Static sum method: " + Vector.twoVectorsSum(
                new Vector(new double[]{2.0, 2.0}),
                new Vector(new double[]{4.0, 4.0, 4.0, 4.0, 4.0})));

        System.out.println("Static difference method: " + Vector.twoVectorsDifference(
                new Vector(new double[]{4.0, 4.0, 4.0, 4.0, 4.0}),
                new Vector(new double[]{2.0, 2.0})));

        System.out.println("Static scalar multiplication method: " + Vector.twoVectorsScalarMultiplication(
                new Vector(new double[]{2.0, 2.0}),
                new Vector(new double[]{4.0, 4.0, 4.0, 4.0, 4.0})));
    }
}