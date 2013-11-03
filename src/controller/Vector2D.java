package controller;

import java.awt.*;

public class Vector2D {
    double x;
    double y;

    public Vector2D(Point org, Point dst){
        this.x = org.x-dst.x;
        this.y = org.y-dst.y;
    }

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double getLength(Vector2D v){
        return 0.0;
    }

    // compares 2 vectors
    public static boolean equals(Vector2D a, Vector2D b){
        return (a.x==b.x&&a.y==b.y);
    }

    // normalizes v and returns the normalize vectors
    public static Vector2D normalize(Vector2D v){
        double lA = Math.sqrt((v.x*v.x)+(v.y*v.y));
        double x=v.x/lA;
        double y=v.y/lA;
        return new Vector2D(x,y);
    }

    // checks if the vectors point in same direction
    // (first normalize the vectors and then compare them)
    public static boolean sameDirection(Vector2D a, Vector2D b){
        return equals(normalize(a),normalize(b));
    }
}
