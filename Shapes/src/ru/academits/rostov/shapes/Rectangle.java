package ru.academits.rostov.shapes;

public class Rectangle implements Shape {

    double width;
    double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return (width + height) * 2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Rectangle rectangle = (Rectangle) o;

        return this.width == rectangle.width && this.height == rectangle.height;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Double.hashCode(width);
        hash = prime * hash + Double.hashCode(height);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("Rectangle with height = %f, width = %f, area = %f, perimeter = %f%n",
                getHeight(), getWidth(), getArea(), getPerimeter());
    }
}
