package ru.academits.rostov.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Vector size must be >= 0. Current size: " + size);
        }

        components = new double[size];
    }

    public Vector(Vector vector) {
        this.components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("Vector size must be >= 0. Current size: 0");
        }

        this.components = Arrays.copyOf(components, components.length);
    }

    public Vector(int size, double[] inputComponents) {
        if (size <= 0) {
            throw new IllegalArgumentException("Vector size must be >= 0. Current size: " + size);
        }

        components = Arrays.copyOf(inputComponents, size);
    }

    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        StringBuilder componentsStringBuilder = new StringBuilder(Arrays.toString(components));

        componentsStringBuilder.setCharAt(0, '{');
        componentsStringBuilder.setCharAt(componentsStringBuilder.length() - 1, '}');

        return componentsStringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Vector v = (Vector) o;

        return Arrays.equals(components, v.components);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Arrays.hashCode(components);

        return hash;
    }

    public void add(Vector vector) {
        int inputVectorSize = vector.components.length;

        if (components.length < inputVectorSize) {
            components = Arrays.copyOf(components, inputVectorSize);
        }

        for (int i = 0; i < inputVectorSize; ++i) {
            components[i] += vector.components[i];
        }
    }

    public void subtract(Vector vector) {
        int inputVectorSize = vector.components.length;

        if (components.length < inputVectorSize) {
            components = Arrays.copyOf(components, inputVectorSize);
        }

        for (int i = 0; i < inputVectorSize; ++i) {
            components[i] -= vector.components[i];
        }
    }

    public void multiplyByScalar(double number) {
        for (int i = 0; i < components.length; ++i) {
            components[i] *= number;
        }
    }

    public void flip() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double squaredComponentsSum = 0;

        for (double component : components) {
            squaredComponentsSum += component * component;
        }

        return Math.sqrt(squaredComponentsSum);
    }

    public double getComponentByIndex(int index) {
        int size = components.length;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < " + size + ". Current index is " + index);
        }

        return components[index];
    }

    public void setComponentByIndex(int index, double component) {
        int size = components.length;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be >= 0 and < " + size + ". Current index is " + index);
        }

        components[index] = component;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);

        resultVector.add(vector2);

        return resultVector;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);

        resultVector.subtract(vector2);

        return resultVector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        int minInputVectorsSize = Math.min(vector1.components.length, vector2.components.length);

        double scalarProduct = 0;

        for (int i = 0; i < minInputVectorsSize; ++i) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }
}