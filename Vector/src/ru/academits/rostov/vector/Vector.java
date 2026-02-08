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

    public Vector(int size, double[] components) {
        if (size <= 0) {
            throw new IllegalArgumentException("Vector size must be >= 0. Current size: " + size);
        }

        this.components = Arrays.copyOf(components, size);
    }

    public int getSize() {
        return components.length;
    }

    @Override
    public String toString() {
        String[] vectorStringArray = new String[getSize()];

        for (int i = 0; i < getSize(); ++i) {
            vectorStringArray[i] = components[i] + "";
        }

        return "{" + String.join(", ", vectorStringArray) + "}";
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

        int size = components.length;

        if (size != v.components.length) {
            return false;
        }

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
        int size = components.length;
        double[] resultContents;

        if (size >= vector.components.length) {
            resultContents = Arrays.copyOf(vector.components, size);
        } else {
            resultContents = Arrays.copyOf(components, vector.components.length);
            components = vector.components;
        }

        for (int i = 0; i < vector.components.length; ++i) {
            components[i] += resultContents[i];
        }
    }

    public void subtract(Vector vector) {
        int size = components.length;
        int inputVectorSize = vector.components.length;

        if (size < inputVectorSize) {
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

        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index must be < " + size + ". Current value is " + index);
        }
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index must be >= 0. Current value is " + index);
        }

        return components[index];
    }

    public void setComponentByIndex(int index, double value) {
        int size = components.length;

        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index must be < " + size + ". Current value is " + index);
        }
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index must be >= 0. Current value is " + index);
        }

        components[index] = value;
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

    public static double getScalarMultiplication(Vector vector1, Vector vector2) {
        int size = Math.min(vector1.components.length, vector2.components.length);

        double resultValue = 0;

        for (int i = 0; i < size; ++i) {
            resultValue += vector1.components[i] * vector2.components[i];
        }

        return resultValue;
    }
}