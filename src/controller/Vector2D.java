package controller;

import java.awt.*;

public class Vector2D {
    private final double x;
    private final double y;

    public Vector2D(Point org, Point dst) {
        this.x = dst.x - org.x;
        this.y = dst.y - org.y;
    }

    private Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // constructs vector out of 2 integers
    public Vector2D(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
    }

    // compares 2 vectors
    private static final double THRESHOLD = 0.000000001;

    private static boolean isEqual(Vector2D a, Vector2D b) {
        // THRESHOLD necessary because floats
        return Math.abs(a.getX() - b.getX()) < THRESHOLD && Math.abs(a.getY() - b.getY()) < THRESHOLD;
    }

    // normalizes v and returns the normalized vectors
    private static Vector2D normalize(Vector2D v) {
        double lA = Math.sqrt((v.getX() * v.getX()) + (v.getY() * v.getY()));
        double x = v.getX() / lA;
        double y = v.getY() / lA;
        return new Vector2D(x, y);
    }

    // checks if the vectors point in same direction
    // (first normalize the vectors and then compare them)
    public static boolean sameDirection(Vector2D a, Vector2D b) {

        return isEqual(normalize(a), normalize(b));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
