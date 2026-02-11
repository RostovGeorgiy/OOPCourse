package ru.academits.rostov.matrix;

import ru.academits.rostov.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] components;

    public Matrix(int n, int m) {
        components = new Vector[n];

        for (int i = 0; i < n; ++i) {
            components[i] = new Vector(m, new double[]{});
        }
    }

    public Matrix(Matrix matrix) {
        components = Arrays.copyOf(matrix.components, matrix.components.length);
    }

    public Matrix(double[][] inputArray) {
        int matrixHeight = inputArray.length;

        components = new Vector[matrixHeight];

        for (int i = 0; i < matrixHeight; ++i) {
            components[i] = new Vector(inputArray[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        int matrixHeight = vectors.length;

        components = new Vector[matrixHeight];

        System.arraycopy(vectors, 0, components, 0, matrixHeight);
    }

    public int getHeight() {
        return components.length;
    }

    public int getWidth() {
        return components[0].getSize();
    }

    public Vector getRowByIndex(int index) {
        if (index >= components.length) {
            throw new IndexOutOfBoundsException("Index must be < " + components.length + ". Current index = " + index);
        }

        return components[index];
    }

    public void setRowByIndex(Vector vector, int index) {
        if (index >= components.length) {
            throw new IndexOutOfBoundsException("Index must be < " + components.length + ". Current index = " + index);
        }

        int vectorSize = vector.getSize();

        if (vectorSize != components[0].getSize()) {
            throw new IllegalArgumentException("Size of argument vector must match matrix width: " +
                    components[0].getSize() + ". Current size is " + vectorSize);
        }

        components[index] = new Vector(vector);
        //System.arraycopy(vector.getContents(), 0, newMatrixVectorContents, 0, vector.getSize());

    }

    public Vector getColumnByIndex(int index) {
        if (index >= components[0].getSize()) {
            throw new IndexOutOfBoundsException("Index must be < " + components[0].getSize() + ". Current index = " + index);
        }

        double[] columnComponents = new double[components.length];

        for (int i = 0; i < components.length; ++i) {
            columnComponents[i] = components[i].getComponentByIndex(index);
        }

        return new Vector(columnComponents);
    }

    public Matrix transposeMatrix() {
        Vector[] transposedMatrixContents = new Vector[getWidth()];

        for (int i = 0; i < getWidth(); ++i) {
            transposedMatrixContents[i] = getColumnByIndex(i);
        }

        return new Matrix(transposedMatrixContents);
    }

    @Override
    public String toString() {
        StringBuilder matrixStringBuilder = new StringBuilder(components.length * components[0].getSize());

        for (Vector component : components) {
            matrixStringBuilder.append(component.toString()).append(", ");
        }

        matrixStringBuilder.insert(0, '{');
        matrixStringBuilder.replace(matrixStringBuilder.length() - 2, matrixStringBuilder.length() - 1, "}");

        return matrixStringBuilder.toString();
    }

    public void multiplyByScalar(double scalar) {
        for (Vector component : components) {
            component.multiplyByScalar(scalar);
        }
    }

    private void getCofactor(Matrix currentMatrix, Matrix tempMatrix, int columnNumber) {
        int size = tempMatrix.components.length;
        int tempMatrixRow = 0;
        int tempMatrixColumn = 0;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i != 0 && j != columnNumber) {
                    tempMatrix.components[tempMatrixRow].setComponentByIndex(tempMatrixColumn++, currentMatrix.components[i].getComponentByIndex(j));

                    if (tempMatrixColumn == size - 1) {
                        tempMatrixColumn = 0;
                        tempMatrixRow++;
                    }
                }
            }
        }
    }

    public double getDeterminant() {
        if (components.length != components[0].getSize()) {
            throw new UnsupportedOperationException("Matrix must be square. Current height = " + components.length + " and width = " + components[0].getSize());
        }

        return getDeterminantRecursive(this, components.length);
    }

    private double getDeterminantRecursive(Matrix matrix, int size) {
        double determinant = 0;

        if (size == 1) {
            return matrix.components[0].getComponentByIndex(0);
        }

        Matrix tempMatrix = new Matrix(size, size);

        int sign = 1;

        for (int i = 0; i < size; ++i) {
            getCofactor(matrix, tempMatrix, i);
            determinant += sign * matrix.components[0].getComponentByIndex(i) * getDeterminantRecursive(tempMatrix, size - 1);

            sign = -sign;
        }

        return determinant;
    }

    public Vector getProductByVector(Vector vector) {
        int vectorSize = vector.getSize();
        int matrixHeight = components.length;

        if (vectorSize != components[0].getSize()) {
            throw new IllegalArgumentException("Vector size must be = matrix width. Current vector size: " + vectorSize + " and matrix width: " + components[0].getSize());
        }

        double[] product = new double[matrixHeight];

        for (int i = 0; i < matrixHeight; ++i) {
            for (int j = 0; j < vectorSize; ++j) {
                product[i] += components[i].getComponentByIndex(j) * vector.getComponentByIndex(j);
            }
        }

        return new Vector(product);
    }

    public void add(Matrix matrix) {
        int matrixHeight = matrix.components.length;
        int matrixWidth = matrix.components[0].getSize();

        if (components.length != matrixHeight || components[0].getSize() != matrixWidth) {
            throw new IllegalArgumentException("Matrices must have equal heights and width. Current heights: "
                    + components.length + " and " + matrixHeight + ". Widths " + components[0].getSize() + " and " + matrixWidth);
        }

        for (int i = 0; i < matrixHeight; ++i) {
            for (int j = 0; j < matrixWidth; ++j) {
                components[i].setComponentByIndex(j, components[i].getComponentByIndex(j) + matrix.components[i].getComponentByIndex(j));
            }
        }
    }

    public void subtract(Matrix matrix) {
        int matrixHeight = matrix.components.length;
        int matrixWidth = matrix.components[0].getSize();

        if (components.length != matrixHeight || components[0].getSize() != matrixWidth) {
            throw new IllegalArgumentException("Matrices must have equal heights and width. Current heights: "
                    + components.length + " and " + matrixHeight + ". Widths " + components[0].getSize() + " and " + matrixWidth);
        }

        for (int i = 0; i < matrixHeight; ++i) {
            for (int j = 0; j < matrixWidth; ++j) {
                components[i].setComponentByIndex(j, components[i].getComponentByIndex(j) - matrix.components[i].getComponentByIndex(j));
            }
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        Matrix sumMatrix = new Matrix(matrix1);

        sumMatrix.add(matrix2);

        return sumMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        Matrix sumMatrix = new Matrix(matrix1);

        sumMatrix.subtract(matrix2);

        return sumMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.components[0].getSize() != matrix2.components.length) {
            throw new IllegalArgumentException("Matrix 1 width must be equal matrix 2 height. Matrix 1 width: " + matrix1.components[0].getSize() + " and matrix 2 height: " + matrix2.components.length);
        }

        Matrix productMatrix = new Matrix(matrix2.components.length, matrix2.components[0].getSize());

        for (int i = 0; i < matrix1.components.length; ++i) {
            for (int j = 0; j < matrix2.components[0].getSize(); ++j)
                productMatrix.components[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.components[i], matrix2.getColumnByIndex(j)));
        }

        return productMatrix;
    }
}