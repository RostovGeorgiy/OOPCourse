package ru.academits.rostov.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getFrom() {
        return from;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getTo() {
        return to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return number >= from && number <= to;
    }

    public Range getIntersection(Range range2) {
        double range2From = range2.getFrom();
        double range2To = range2.getTo();

        if (range2To <= from || to <= range2From || (to == range2From || range2To == from)) {
            return null;
        }

        return new Range(Math.max(from, range2From), Math.min(to, range2To));
    }

    public Range[] getUnion(Range range2) {
        double range2From = range2.getFrom();
        double range2To = range2.getTo();

        if (to < range2From || range2To < from) {
            return new Range[]{new Range(Math.min(from, range2From), Math.min(to, range2To)),
                    new Range(Math.max(from, range2From), Math.max(to, range2To))};
        }

        return new Range[]{new Range(Math.min(from, range2From), Math.max(to, range2To))};
    }

    public Range[] getDifference(Range range2) {
        double range2From = range2.getFrom();
        double range2To = range2.getTo();

        if (to < range2From || range2To < from) {
            return new Range[]{new Range(from, to)};
        } else if (from < range2From && to >= range2From) {
            return new Range[]{new Range(from, range2From)};
        } else if (range2From <= from && to > range2To) {
            return new Range[]{new Range(range2To, to)};
        } else if (range2From > from && range2To < to) {
            return new Range[]{new Range(from, range2From), new Range(range2To, to)};
        } else if (from > range2From && to < range2To) {
            return new Range[]{};
        }

        return new Range[]{new Range(from, to)};
    }
}