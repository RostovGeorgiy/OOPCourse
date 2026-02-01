package ru.academits.rostov.shapes;

public class Square implements Shape {

    double side;

    public Square(double side) {
        this.side = side;
    }

    @Override
    public double getWidth() {
        return side;
    }

    @Override
    public double getHeight() {
        return side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public double getPerimeter() {
        return side * 4;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Square square = (Square) o;

        return this.side == square.side;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Double.hashCode(side);

        return hash;
    }

    @Override
    public String toString() {
        return String.format("Square with height = %f, width = %f, area = %f, perimeter = %f%n",
                getHeight(), getWidth(), getArea(), getPerimeter());
    }
}