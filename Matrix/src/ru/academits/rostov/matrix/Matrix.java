package ru.academits.rostov.matrix;

import ru.academits.rostov.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsAmount, int columnsAmount) {
        if (rowsAmount <= 0 || columnsAmount <= 0) {
            throw new IllegalArgumentException("Amount of rows and columns must be >= 0. Current amount of rows: " + rowsAmount +
                    " Current amount of columns: " + columnsAmount);
        }

        rows = new Vector[rowsAmount];

        for (int i = 0; i < rowsAmount; ++i) {
            rows[i] = new Vector(columnsAmount, new double[]{});
        }
    }

    public Matrix(Matrix matrix) {
        int rowsAmount = matrix.getRowsAmount();
        int columnsAmount = matrix.getColumnsAmount();

        if (rowsAmount <= 0 || columnsAmount <= 0) {
            throw new IllegalArgumentException("Amount of rows and columns must be > 0. Current amount of rows: " + rowsAmount +
                    " Current amount of columns: " + columnsAmount);
        }

        rows = new Vector[rowsAmount];

        for (int i = 0; i < rowsAmount; ++i) {
            rows[i] = new Vector(matrix.getRowByIndex(i));
        }
    }

    public Matrix(double[][] array) {
        if (array.length == 0 || array[0].length == 0) {
            throw new IllegalArgumentException("Array length must be not be 0.");
        }

        int rowsAmount = array.length;

        rows = new Vector[rowsAmount];

        int columnsAmount = 0;

        for (double[] e : array) {
            columnsAmount = Math.max(columnsAmount, e.length);
        }

        for (int i = 0; i < array.length; ++i) {
            rows[i] = new Vector(Arrays.copyOf(array[i], columnsAmount));
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Array length should not be 0.");
        }

        int rowsAmount = vectors.length;

        rows = new Vector[rowsAmount];

        int columnsAmount = 0;

        for (Vector vector : vectors) {
            columnsAmount = Math.max(columnsAmount, vector.getSize());
        }

        for (int i = 0; i < rowsAmount; ++i) {
            if (vectors[i].getSize() < columnsAmount) {
                double[] paddedVectorComponents = new double[columnsAmount];

                for (int j = 0; j < columnsAmount; ++j) {
                    paddedVectorComponents[j] = vectors[i].getComponentByIndex(j);
                }

                rows[i] = new Vector(paddedVectorComponents);
            } else {
                rows[i] = new Vector(vectors[i]);
            }
        }
    }

    public int getRowsAmount() {
        return rows.length;
    }

    public int getColumnsAmount() {
        return rows[0].getSize();
    }

    public Vector getRowByIndex(int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Index must be > 0 and < " + rows.length + ". Current index = " + index);
        }

        return new Vector(rows[index]);
    }

    public void setRowByIndex(Vector vector, int index) {
        if (index < 0 || index >= rows.length) {
            throw new IndexOutOfBoundsException("Index must be > 0 and < " + rows.length + ". Current index = " + index);
        }

        int vectorSize = vector.getSize();

        if (vectorSize != getColumnsAmount()) {
            throw new IllegalArgumentException("Size of argument vector must match amount of columns. Current size is " + vectorSize +
                    " And amount of columns is: " + getColumnsAmount());
        }

        rows[index] = new Vector(vector);
    }

    public Vector getColumnByIndex(int index) {
        if (index < 0 || index >= getColumnsAmount()) {
            throw new IndexOutOfBoundsException("Index must be > 0 and < " + getColumnsAmount() + ". Current index = " + index);
        }

        double[] column = new double[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            column[i] = rows[i].getComponentByIndex(index);
        }

        return new Vector(column);
    }

    public void transpose() {
        Vector[] transposedMatrixRows = new Vector[getColumnsAmount()];

        for (int i = 0; i < getColumnsAmount(); ++i) {
            transposedMatrixRows[i] = getColumnByIndex(i);
        }

        rows = transposedMatrixRows;
    }

    @Override
    public String toString() {
        StringBuilder matrixStringBuilder = new StringBuilder(rows.length * getColumnsAmount());

        matrixStringBuilder.append('{');

        for (Vector component : rows) {
            matrixStringBuilder.append(component).append(", ");
        }

        matrixStringBuilder.replace(matrixStringBuilder.length() - 2, matrixStringBuilder.length() - 1, "}");

        return matrixStringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Matrix matrix = (Matrix) o;

        return Arrays.equals(rows, matrix.rows);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        return prime * hash + Arrays.hashCode(rows);
    }

    public void multiplyByScalar(double scalar) {
        for (Vector v : rows) {
            v.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() {
        if (rows.length != getColumnsAmount()) {
            throw new IllegalStateException("Matrix must be square. Current amount of rows = " + rows.length + " and columns = " + getColumnsAmount());
        }

        return getDeterminantRecursive(this);
    }

    private double getDeterminantRecursive(Matrix matrix) {
        int size = matrix.getRowsAmount();

        if (size == 1) {
            return matrix.rows[0].getComponentByIndex(0);
        }

        Matrix tempMatrix = new Matrix(size - 1, size - 1);

        int sign = 1;

        double determinant = 0;

        for (int i = 0; i < size; ++i) {
            computeCofactor(matrix, tempMatrix, i);
            determinant += sign * matrix.rows[0].getComponentByIndex(i) * getDeterminantRecursive(tempMatrix);

            sign = -sign;
        }

        return determinant;
    }

    private void computeCofactor(Matrix currentMatrix, Matrix tempMatrix, int columnNumber) {
        int size = currentMatrix.rows.length;
        int tempMatrixRow = 0;
        int tempMatrixColumn = 0;

        for (int i = 1; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (j != columnNumber) {
                    tempMatrix.rows[tempMatrixRow].setComponentByIndex(tempMatrixColumn++, currentMatrix.rows[i].getComponentByIndex(j));
                }
            }

            tempMatrixColumn = 0;
            tempMatrixRow += 1;
        }
    }

    public Vector getProductByVector(Vector vector) {
        int vectorSize = vector.getSize();

        if (vectorSize != getColumnsAmount()) {
            throw new IllegalArgumentException("Vector size must be = amount of columns . Current vector size: " + vectorSize +
                    " and amount of columns: " + getColumnsAmount());
        }

        int rowsAmount = rows.length;

        double[] product = new double[rowsAmount];

        for (int i = 0; i < rowsAmount; ++i) {
            product[i] = Vector.getScalarProduct(rows[i], vector);
        }

        return new Vector(product);
    }

    public void add(Matrix matrix) {
        int rowsAmount = matrix.rows.length;
        int columnsAmount = matrix.getColumnsAmount();

        if (rows.length != rowsAmount || getColumnsAmount() != columnsAmount) {
            throw new IllegalArgumentException("Matrices must have equal amounts of rows and columns. Current amount of rows: "
                    + rows.length + " and " + rowsAmount + ". Amount of columns: " + getColumnsAmount() + " and " + columnsAmount);
        }

        for (int i = 0; i < rowsAmount; ++i) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        int rowsAmount = matrix.rows.length;
        int columnsAmount = matrix.getColumnsAmount();

        if (rows.length != rowsAmount || getColumnsAmount() != columnsAmount) {
            throw new IllegalArgumentException("Matrices must have equal amounts of rows and columns. Current amount of rows: "
                    + rows.length + " and " + rowsAmount + ". Amount of columns: " + getColumnsAmount() + " and " + columnsAmount);
        }

        for (int i = 0; i < rowsAmount; ++i) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length || matrix1.getColumnsAmount() != matrix2.getColumnsAmount()) {
            throw new IllegalArgumentException("Matrices must have equal amounts of rows and columns. Current amount of rows: "
                    + matrix1.rows.length + " and " + matrix2.rows.length + ". Amount of columns: " + matrix1.getColumnsAmount() + " and " + matrix2.getColumnsAmount());
        }

        Matrix sumMatrix = new Matrix(matrix1);

        sumMatrix.add(matrix2);

        return sumMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length || matrix1.getColumnsAmount() != matrix2.getColumnsAmount()) {
            throw new IllegalArgumentException("Matrices must have equal amounts of rows and columns. Current amount of rows: "
                    + matrix1.rows.length + " and " + matrix2.rows.length + ". Amount of columns: " + matrix1.getColumnsAmount() + " and " + matrix2.getColumnsAmount());
        }

        Matrix differenceMatrix = new Matrix(matrix1);

        differenceMatrix.subtract(matrix2);

        return differenceMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsAmount() != matrix2.rows.length) {
            throw new IllegalArgumentException("Matrix 1 amount of columns must be equal matrix 2 amount of rows. Matrix 1 amount of columns: " + matrix1.rows[0].getSize() + " and matrix 2 amount of rows: " + matrix2.rows.length);
        }

        Matrix productMatrix = new Matrix(matrix2.rows.length, matrix2.getColumnsAmount());

        for (int i = 0; i < matrix1.rows.length; ++i) {
            for (int j = 0; j < matrix2.getColumnsAmount(); ++j) {
                productMatrix.rows[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumnByIndex(j)));
            }
        }

        return productMatrix;
    }
}