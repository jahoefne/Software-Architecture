package controller;

import java.awt.*;

public class Vector2D {
    double x;
    double y;

    public Vector2D(Point org, Point dst){
        this.x = dst.x-org.x;
        this.y = dst.y-org.y;
    }

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }


    public static boolean sameDirection(Vector2D a, Vector2D b){
        double lA = Math.sqrt((a.x*a.x)+(a.y*a.y));
        double xA=a.x/lA;
        double yA=a.y/lA;

        double lB = Math.sqrt((b.x*b.x)+(b.y*b.y));
        double xB=b.x/lB;
        double yB=b.y/lB;

        // System.out.println("A=("+xA+","+yA+") ");
        // System.out.println("B=("+xB+","+yB+") ");
        return (xA==yA&&xB==yB);
    }
}
