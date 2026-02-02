package ru.academits.rostov.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return number >= from && number <= to;
    }

    public Range getIntersection(Range range) {
        double maxFrom = Math.max(from, range.from);
        double minTo = Math.min(to, range.to);

        if (range.to <= from || to <= range.from) {
            return null;
        }

        return new Range(maxFrom, minTo);
    }

    public Range[] getUnion(Range range) {
        double minFrom = Math.min(from, range.from);
        double minTo = Math.min(to, range.to);

        double maxFrom = Math.max(from, range.from);
        double maxTo = Math.max(to, range.to);

        if (to < range.from || range.to < from) {
            return new Range[]{
                    new Range(minFrom, minTo),
                    new Range(maxFrom, maxTo)};
        }

        return new Range[]{new Range(Math.min(from, range.from), Math.max(to, range.to))};
    }

    public Range[] getDifference(Range range) {
        if (range.from > from && range.to >= to) {
            return new Range[]{new Range(from, range.from)};
        }

        if (range.from <= from && to > range.to) {
            return new Range[]{new Range(range.to, to)};
        }

        if (range.from > from && range.to < to) {
            return new Range[]{new Range(from, range.from), new Range(range.to, to)};
        }

        if (from >= range.from && to <= range.to) {
            return new Range[]{};
        }

        return new Range[]{new Range(from, to)};
    }
}