package ru.academits.rostov.matrix_main;

import ru.academits.rostov.matrix.Matrix;
import ru.academits.rostov.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Matrix zerosMatrix = new Matrix(3, 3);
        Matrix matrix1 = new Matrix(zerosMatrix);
        Matrix matrix2 = new Matrix(new double[][]{new double[]{1, 2, 3}, new double[]{4, 5, 6}});
        Matrix matrix3 = new Matrix(new Vector[]{new Vector(new double[]{1, 2, -1}), new Vector(new double[]{0, 3, 4}), new Vector(new double[]{-2, 5, 6})});

        System.out.println(zerosMatrix);

        System.out.println(matrix1);

        System.out.println(matrix2);

        System.out.println(matrix3);

        System.out.println("Set matrix vector by index.");
        matrix2.setRowByIndex(new Vector(3), 1);

        System.out.println("Get matrix row vector by index: " + matrix2.getRowByIndex(1));

        System.out.println("Get matrix column vector by index: " + matrix2.getColumnByIndex(1));

        System.out.println(matrix3);

        matrix3.transpose();

        System.out.println("Transposed matrix 3:");

        System.out.println(matrix3);

        System.out.println("Testing equals(): " +
                new Matrix(new double[][]{new double[]{1, 2, 3}, new double[]{4, 5, 6}})
                        .equals(new Matrix(new double[][]{new double[]{1, 2, 3}, new double[]{4, 5, 6}})));

        matrix2.multiplyByScalar(4);
        System.out.println("Matrix 2 multiplied by 4: " + matrix2);

        System.out.println("Matrix 3 determinant: " + matrix3.getDeterminant());

        System.out.println("Matrix 2 multiplied by vector: " + matrix2.getProductByVector(new Vector(new double[]{5, 5, 5})));

        System.out.println("Sum of matrices:");
        matrix2.add(new Matrix(new double[][]{new double[]{5, 5, 5}, new double[]{5, 5, 5}}));
        System.out.println(matrix2);

        System.out.println("Static sum method: " + Matrix.getSum(
                new Matrix(new double[][]{new double[]{5, 5, 5}, new double[]{5, 5, 5}}),
                new Matrix(new double[][]{new double[]{5, 5, 5}, new double[]{5, 5, 5}}))
        );

        System.out.println("Static difference method: " + Matrix.getDifference(
                new Matrix(new double[][]{new double[]{5, 5, 5}, new double[]{5, 5, 5}}),
                new Matrix(new double[][]{new double[]{5, 5, 5}, new double[]{5, 5, 5}}))
        );

        System.out.println("Static product method: " + Matrix.getProduct(
                new Matrix(new double[][]{new double[]{2, -3, 1}, new double[]{5, 4, -2}}),
                new Matrix(new double[][]{new double[]{-7, 5}, new double[]{2, -1}, new double[]{4, 3}}))
        );
    }
}