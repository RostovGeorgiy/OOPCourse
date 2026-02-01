package ru.academits.rostov.shapes_main;

import ru.academits.rostov.shapes.Shape;
import ru.academits.rostov.shapes.Circle;
import ru.academits.rostov.shapes.Rectangle;
import ru.academits.rostov.shapes.Square;
import ru.academits.rostov.shapes.Triangle;

import java.util.Arrays;
import java.util.Comparator;

class ShapeAreaComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {

        double area1 = shape1.getArea();
        double area2 = shape2.getArea();

        if (area1 - area2 > 0)
            return -1;
        else if (area1 - area2 < 0) {
            return 1;
        }

        return 0;
    }
}

class ShapePerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        double perimeter1 = shape1.getPerimeter();
        double perimeter2 = shape2.getPerimeter();

        if (perimeter1 - perimeter2 > 0)
            return -1;
        else if (perimeter1 - perimeter2 < 0) {
            return 1;
        }

        return 0;
    }
}

public class Main {
    public static void getMaxAreaFigure(Shape[] shapesArray) {
        Arrays.sort(shapesArray, new ShapeAreaComparator());
        System.out.print("Figure with max shape is a ");

        switch (shapesArray[0]) {
            case Triangle triangle -> System.out.println(triangle);

            case Square square -> System.out.println(square);

            case Rectangle rectangle -> System.out.println(rectangle);

            case Circle circle -> System.out.println(circle);

            default -> throw new IllegalStateException("Unexpected class: " + shapesArray[0].getClass());
        }
    }

    public static void getSecondMaxPerimeterFigure(Shape[] shapesArray) {
        Arrays.sort(shapesArray, new ShapePerimeterComparator());

        System.out.print("Figure with second max perimeter is a ");

        switch (shapesArray[1]) {
            case Triangle triangle -> System.out.println(triangle);

            case Square square -> System.out.println(square);

            case Rectangle rectangle -> System.out.println(rectangle);

            case Circle circle -> System.out.println(circle);

            default -> throw new IllegalStateException("Unexpected class: " + shapesArray[0].getClass());
        }
    }

    public static void main(String[] args) {
        Shape[] shapesArray = {new Square(10), new Triangle(3, 0, 6, 0, 4, 5),
                new Rectangle(6, 12), new Circle(8), new Square(30),
                new Triangle(0, 0, 10, 20, 0, 20), new Rectangle(13, 26)};

        getMaxAreaFigure(shapesArray);

        getSecondMaxPerimeterFigure(shapesArray);
    }
}
