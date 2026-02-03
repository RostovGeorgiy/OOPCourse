package ru.academits.rostov.shapes_main;

import ru.academits.rostov.shapes_comparators.ShapeAreaComparator;
import ru.academits.rostov.shapes_comparators.ShapePerimeterComparator;
import ru.academits.rostov.shapes.Shape;
import ru.academits.rostov.shapes.Circle;
import ru.academits.rostov.shapes.Rectangle;
import ru.academits.rostov.shapes.Square;
import ru.academits.rostov.shapes.Triangle;

import java.util.Arrays;

public class Main {
    public static Shape getMaxAreaShape(Shape[] shapesArray) {
        Arrays.sort(shapesArray, new ShapeAreaComparator());

        return shapesArray[shapesArray.length - 1];
    }

    public static Shape getSecondMaxPerimeterShape(Shape[] shapesArray) {
        Arrays.sort(shapesArray, new ShapePerimeterComparator());

        return shapesArray[shapesArray.length - 2];
    }

    public static void main(String[] args) {
        Shape[] shapesArray = {
                new Square(10), new Triangle(3, 0, 6, 0, 4, 5),
                new Rectangle(6, 12), new Circle(8), new Square(30),
                new Triangle(0, 0, 10, 20, 0, 20), new Rectangle(13, 26)
        };

        Shape maxAreaShape = getMaxAreaShape(shapesArray);
        System.out.println("Shape with max area is: " + maxAreaShape);

        Shape secondMaxPerimeterShape = getSecondMaxPerimeterShape(shapesArray);
        System.out.println("Shape with second max perimeter is: " + secondMaxPerimeterShape);
    }
}