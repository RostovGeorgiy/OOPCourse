package ru.academits.rostov.matrix;

import ru.academits.rostov.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsAmount, int columnsAmount) {
        if (rowsAmount <= 0) {
            throw new IllegalArgumentException("Amount of rows must be > 0. Current amount of rows: " + rowsAmount);
        }

        if (columnsAmount <= 0) {
            throw new IllegalArgumentException("Amount of columns must be > 0. Current amount of columns: " + columnsAmount);
        }

        rows = new Vector[rowsAmount];

        for (int i = 0; i < rowsAmount; ++i) {
            rows[i] = new Vector(columnsAmount);
        }
    }

    public Matrix(Matrix matrix) {
        int rowsAmount = matrix.getRowsAmount();

        rows = new Vector[rowsAmount];

        for (int i = 0; i < rowsAmount; ++i) {
            rows[i] = matrix.getRowByIndex(i);
        }
    }

    public Matrix(double[][] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Array length must be not be 0.");
        }

        int columnsAmount = 0;

        for (double[] row : array) {
            columnsAmount = Math.max(columnsAmount, row.length);
        }

        if (columnsAmount == 0) {
            throw new IllegalArgumentException("At least one sub array must have length > 0.");
        }

        rows = new Vector[array.length];

        for (int i = 0; i < array.length; ++i) {
            rows[i] = new Vector(columnsAmount, array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        int columnsAmount = 0;

        for (Vector vector : vectors) {
            columnsAmount = Math.max(columnsAmount, vector.getSize());
        }

        if (columnsAmount == 0) {
            throw new IllegalArgumentException("At least one vector in array must have length > 0.");
        }

        int rowsAmount = vectors.length;

        rows = new Vector[rowsAmount];

        for (int i = 0; i < rowsAmount; ++i) {
            int currentRowSize = vectors[i].getSize();

            if (currentRowSize < columnsAmount) {
                rows[i] = new Vector(columnsAmount);

                for (int j = 0; j < currentRowSize; ++j) {
                    rows[i].setComponentByIndex(j, vectors[i].getComponentByIndex(j));
                }
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
                    " and amount of columns is: " + getColumnsAmount());
        }

        rows[index] = new Vector(vector);
    }

    public Vector getColumnByIndex(int index) {
        if (index < 0 || index >= getColumnsAmount()) {
            throw new IndexOutOfBoundsException("Index must be > 0 and < " + getColumnsAmount() + ". Current index = " + index);
        }

        int rowsAmount = rows.length;

        Vector columnVector = new Vector(rowsAmount);

        for (int i = 0; i < rowsAmount; ++i) {
            columnVector.setComponentByIndex(i, rows[i].getComponentByIndex(index));
        }

        return columnVector;
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

        matrixStringBuilder.replace(matrixStringBuilder.length() - 2, matrixStringBuilder.length(), "}");

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
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() {
        if (rows.length != getColumnsAmount()) {
            throw new IllegalStateException("Matrix must be square. Current amount of rows = " + rows.length + " and columns = "
                    + getColumnsAmount());
        }

        return getDeterminantRecursive(this);
    }

    private static double getDeterminantRecursive(Matrix matrix) {
        int size = matrix.getRowsAmount();

        if (size == 1) {
            return matrix.rows[0].getComponentByIndex(0);
        }

        Matrix subMatrix = new Matrix(size - 1, size - 1);

        int sign = 1;

        double determinant = 0;

        for (int i = 0; i < size; ++i) {
            findSubMatrix(matrix, subMatrix, i);
            determinant += sign * matrix.rows[0].getComponentByIndex(i) * getDeterminantRecursive(subMatrix);

            sign = -sign;
        }

        return determinant;
    }

    private static void findSubMatrix(Matrix matrix, Matrix subMatrix, int columnIndex) {
        int size = matrix.rows.length;

        for (int i = 1; i < size; ++i) {
            int nextMatrixCurrentColumn = 0;

            for (int j = 0; j < size; ++j) {
                if (j != columnIndex) {
                    subMatrix.rows[i - 1].setComponentByIndex(nextMatrixCurrentColumn, matrix.rows[i].getComponentByIndex(j));
                    nextMatrixCurrentColumn++;
                }
            }
        }
    }

    public Vector getProductByVector(Vector vector) {
        int vectorSize = vector.getSize();

        if (vectorSize != getColumnsAmount()) {
            throw new IllegalArgumentException("Vector size must be = amount of columns. Current vector size: " + vectorSize +
                    " and amount of columns: " + getColumnsAmount());
        }

        int rowsAmount = rows.length;

        Vector productVector = new Vector(rowsAmount);

        for (int i = 0; i < rowsAmount; ++i) {
            productVector.setComponentByIndex(i, Vector.getScalarProduct(rows[i], vector));
        }

        return productVector;
    }

    private static void check2MatricesEqualDimensions(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length) {
            throw new IllegalArgumentException("Matrices must have equal amounts of rows. Current amounts of rows: "
                    + matrix1.rows.length + " and " + matrix2.rows.length + ".");
        }

        if (matrix1.getColumnsAmount() != matrix2.getColumnsAmount()) {
            throw new IllegalArgumentException("Matrices must have equal amounts of columns. Current amounts of columns: "
                    + matrix1.getColumnsAmount() + " and " + matrix2.getColumnsAmount());
        }
    }

    public void add(Matrix matrix) {
        int rowsAmount = matrix.rows.length;

        check2MatricesEqualDimensions(this, matrix);

        for (int i = 0; i < rowsAmount; ++i) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        int rowsAmount = matrix.rows.length;

        check2MatricesEqualDimensions(this, matrix);

        for (int i = 0; i < rowsAmount; ++i) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        check2MatricesEqualDimensions(matrix1, matrix2);

        Matrix sumMatrix = new Matrix(matrix1);

        sumMatrix.add(matrix2);

        return sumMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        check2MatricesEqualDimensions(matrix1, matrix2);

        Matrix differenceMatrix = new Matrix(matrix1);

        differenceMatrix.subtract(matrix2);

        return differenceMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsAmount() != matrix2.rows.length) {
            throw new IllegalArgumentException("Matrix 1 amount of columns must be equal matrix 2 amount of rows. Matrix 1 amount of columns: "
                    + matrix1.getColumnsAmount() + " and matrix 2 amount of rows: " + matrix2.rows.length);
        }

        Matrix productMatrix = new Matrix(matrix1.rows.length, matrix2.getColumnsAmount());

        for (int i = 0; i < matrix1.rows.length; ++i) {
            for (int j = 0; j < matrix2.getColumnsAmount(); ++j) {
                productMatrix.rows[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.rows[i], matrix2.getColumnByIndex(j)));
            }
        }

        return productMatrix;
    }
}