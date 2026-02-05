package ru.academits.rostov.vector;

import java.util.Arrays;

public class Vector {
    private int n;
    private double[] vectorContents;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Vector size must be >= 0. Current size: " + n);
        }

        this.n = n;
        vectorContents = new double[n];
    }

    public Vector(Vector vector) {
        n = vector.getSize();

        vectorContents = Arrays.copyOf(vector.getContents(), vector.n);
    }

    public Vector(double[] vectorContents) {
        n = vectorContents.length;

        if (n == 0) {
            throw new IllegalArgumentException("Vector size must be >= 0. Current size: 0");
        }

        this.vectorContents = Arrays.copyOf(vectorContents, vectorContents.length);
    }

    public Vector(int n, double[] vectorContents) {
        this.n = n;

        if (n <= 0) {
            throw new IllegalArgumentException("Vector size must be >= 0. Current size: " + n);
        }

        int vectorContentsSize = vectorContents.length;

        if (n < vectorContentsSize) {
            this.vectorContents = Arrays.copyOfRange(vectorContents, 0, n);
        } else if (n > vectorContentsSize) {
            this.vectorContents = new double[n];

            System.arraycopy(vectorContents, 0, this.vectorContents, 0, vectorContentsSize);
        } else {
            this.vectorContents = Arrays.copyOf(vectorContents, n);
        }
    }

    public int getSize() {
        return n;
    }

    public void setSize(int n) {
        this.n = n;
    }

    public double[] getContents() {
        return vectorContents;
    }

    public void setContents(double[] vector_contents) {
        this.vectorContents = vector_contents;
    }

    @Override
    public String toString() {
        return Arrays.toString(vectorContents).
                replace('[', '{').
                replace(']', '}');
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

        if (n != v.n) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            if (vectorContents[i] != v.vectorContents[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + n;
        hash = prime * hash + Arrays.hashCode(vectorContents);

        return hash;
    }

    public void addVectors(Vector vector) {
        double[] resultContents;

        if (n >= vector.n) {
            for (int i = 0; i < vector.n; ++i) {
                vectorContents[i] += vector.vectorContents[i];
            }
        } else {
            resultContents = Arrays.copyOf(vector.vectorContents, vector.vectorContents.length);

            for (int i = 0; i < n; ++i) {
                resultContents[i] += vectorContents[i];
            }

            n = vector.n;
            vectorContents = Arrays.copyOf(resultContents, resultContents.length);
        }
    }

    public void subtractFromVector(Vector vector) {
        if (n >= vector.n) {
            for (int i = 0; i < vector.n; ++i) {
                vectorContents[i] -= vector.vectorContents[i];
            }
        } else {
            double[] resultContents = Arrays.copyOf(vectorContents, vector.n);

            for (int i = 0; i < vector.n; ++i) {
                resultContents[i] -= vector.vectorContents[i];
            }

            n = vector.n;

            vectorContents = Arrays.copyOf(resultContents, resultContents.length);
        }
    }

    public void scalarMultiplication(int number) {
        for (int i = 0; i < n; ++i) {
            vectorContents[i] *= number;
        }
    }

    public void vectorFlip() {
        for (int i = 0; i < n; ++i) {
            vectorContents[i] *= -1;
        }
    }

    public double getVectorLength() {
        double vectorLength = 0;

        for (int i = 0; i < n; ++i) {
            vectorLength += vectorContents[i] * vectorContents[i];
        }

        return Math.sqrt(vectorLength);
    }

    public double getContentsByPosition(int position) {
        if (position >= n) {
            throw new IllegalArgumentException("Position must be < " + n + ". Current value is " + position);
        }

        return vectorContents[position];
    }

    public void setContentsByPosition(double value, int position) {
        if (position >= n) {
            throw new IllegalArgumentException("Position must be < " + n + ". Current value is " + position);
        }

        vectorContents[position] = value;
    }

    public static Vector twoVectorsSum(Vector vector, Vector vector1) {
        int maxN;
        int minN;

        double[] addendumVectorContents;
        double[] addendumVectorContents1;

        if (vector.n >= vector1.n) {
            maxN = vector.n;
            minN = vector1.n;
            addendumVectorContents = Arrays.copyOf(vector.vectorContents, maxN);
            addendumVectorContents1 = Arrays.copyOf(vector1.vectorContents, minN);
        } else {
            maxN = vector1.n;
            minN = vector.n;
            addendumVectorContents = Arrays.copyOf(vector1.vectorContents, maxN);
            addendumVectorContents1 = Arrays.copyOf(vector.vectorContents, minN);
        }

        double[] resultVectorContents = addendumVectorContents;

        for (int i = 0; i < minN; ++i) {
            resultVectorContents[i] += addendumVectorContents1[i];
        }

        return new Vector(maxN, resultVectorContents);
    }

    public static Vector twoVectorsDifference(Vector vector, Vector vector1) {
        int maxN;

        double[] minuendVectorContents;
        double[] subtrahendVectorContents1;

        maxN = Math.max(vector.n, vector1.n);

        minuendVectorContents = Arrays.copyOf(vector.vectorContents, maxN);
        subtrahendVectorContents1 = Arrays.copyOf(vector1.vectorContents, maxN);

        double[] resultVectorContents = new double[maxN];

        for (int i = 0; i < maxN; ++i) {
            resultVectorContents[i] = minuendVectorContents[i] - subtrahendVectorContents1[i];
        }

        return new Vector(maxN, resultVectorContents);
    }

    public static double twoVectorsScalarMultiplication(Vector vector, Vector vector1) {
        int maxN;

        double[] multiplierVectorContents;
        double[] multiplierVectorContents1;

        if (vector.n >= vector1.n) {
            maxN = vector.n;
            multiplierVectorContents = Arrays.copyOf(vector.vectorContents, maxN);
            multiplierVectorContents1 = Arrays.copyOf(vector1.vectorContents, maxN);
        } else {
            maxN = vector1.n;
            multiplierVectorContents = Arrays.copyOf(vector1.vectorContents, maxN);
            multiplierVectorContents1 = Arrays.copyOf(vector.vectorContents, maxN);
        }

        double resultScalar = 0;

        for (int i = 0; i < maxN; ++i) {
            resultScalar += multiplierVectorContents[i] * multiplierVectorContents1[i];
        }

        return resultScalar;
    }
}