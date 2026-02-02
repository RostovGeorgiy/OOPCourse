package ru.academits.rostov.shapes_main;

import ru.academits.rostov.shapes.Shape;

import java.util.Comparator;

public class ShapePerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare(Shape shape1, Shape shape2) {
        double perimeter1 = shape1.getPerimeter();
        double perimeter2 = shape2.getPerimeter();

        return Integer.compare(Double.compare(perimeter1, perimeter2), 0);
    }
}