package controller;

import java.awt.Point;

import model.*;

public class MoveValidator {

    //o p a b c
    //n - - - d
    //m - x - e
    //l - - - f
    //k j i h g
    public static Vector2D[] directions = {     // denotes the 16 possible direction vectors
            new Vector2D(0, 2),    // x -> a
            new Vector2D(1, 2),    // x -> b
            new Vector2D(2, 2),    // x -> c
            new Vector2D(2, 1),    // x -> d
            new Vector2D(2, 0),    // x -> e
            new Vector2D(2, -1),   // x -> f
            new Vector2D(2, -2),   // x -> g
            new Vector2D(1, -2),   // x -> h
            new Vector2D(0, -2),   // x -> i
            new Vector2D(-1, -2),  // x -> j
            new Vector2D(-2, -2),  // x -> k
            new Vector2D(-2, -1),  // x -> l
            new Vector2D(-2, 0),   // x -> m
            new Vector2D(-2, 1),   // x -> n
            new Vector2D(-2, 2),   // x -> o
            new Vector2D(-1, 2)    // x -> p
    };


    // Moves the figure at p1 to p2 if the move is valid
    public Field moveIfvalid(Point p1, Point p2, Field field) {
        System.out.println("Move:["+p1.x+","+p1.y+"] to ["+p2.x+","+p2.y+"]");
        if (isValid(p1, p2, field)) {        // if the move is valid
            System.out.println("Valid!");
            field.move(p1,p2);          // move p1 to p2
            field.toggleWhiteOrBlack();       // change color for next move
        }
        System.out.println("Not Valid!");
        return field;
    }

    // returns the ID (0-15) if 'v' points in the same direction, or -1 if it is not identical with
    // any of the directions
    private int getDirectionID(Vector2D v) {
        for (int i = 0; i < directions.length; i++) {
            if (Vector2D.sameDirection(v, directions[i]))
                return i;

        }
        return -1;
    }

    // sets the  nth bit in mask
    public int setBit(int mask, int n) {
        mask |= (1 << n);
        return mask;
    }


    // returns whether the path BETWEEN p1 and p2 is empty
    public boolean isEmptyPath(Point p1, Point p2, Field field) {
        Vector2D v = Vector2D.normalize(new Vector2D(p1, p2));      // TODO: not sure if the normalize is necessary!
        int x = p1.x;
        int y = p1.y;

        while (x != p2.x && y != p2.y) {
            x += (int) v.x;
            y += (int) v.y;
            if (field.getCell(x,y)!=Figures.empty.id())
                return false;
        }
        return true;
    }


    // checks if the move is valid
    public boolean isValid(Point p1, Point p2, Field field) {

        FigureMask f1 = Figures.lookUpID(field.getCell(p1.x,p1.y)).getMask();
        FigureMask f2 = Figures.lookUpID(field.getCell(p2.x,p2.y)).getMask();

        Vector2D v = new Vector2D(p1, p2);

        int bit = this.getDirectionID(v);
        int moveMask = this.setBit(0, bit);
        int captureMask = this.setBit(0, bit + 16);

        if (!(f1.id * field.getWhiteOrBlack() >= 0))
            return false;       // not the turn of the player or empty field

        if (bit < 0)
            return false;       // wrong direction

        if ((f1.bitMask & moveMask) == 0 && f2.id == Figures.empty.id())
            return false;   // player may not move in this direction and there is no enemy figure to capture

        else if ((f1.bitMask & moveMask) != 0 && f2.id == Figures.empty.id() && isEmptyPath(p1, p2, field))
            return true;                    // f1 may move in this direction f2 is empty => f1 may move to f2

        if ((f1.bitMask & captureMask) != 0 && (f2.id * f1.id < 0) && isEmptyPath(p1, p2, field))
            return true;                // f1 may move to, and capture f2

        return false;      // if something unexpected happens => fail
    }
}
