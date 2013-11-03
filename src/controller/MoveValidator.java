package controller;

import java.awt.Point;

import model.*;

public class MoveValidator {

    //- p - b -
    //n o a c d
    //- m x e -
    //l l i g f
    //- j - h -
    public static Vector2D[] directions = {     // denotes the 16 possible direction vectors
            new Vector2D(0, -1),    // x -> a
            new Vector2D(1, -2),    // x -> b
            new Vector2D(1, -1),    // x -> c
            new Vector2D(2, -1),    // x -> d
            new Vector2D(1, 0),    // x -> e
            new Vector2D(2, 1),   // x -> f
            new Vector2D(1, 1),   // x -> g
            new Vector2D(1, 2),   // x -> h
            new Vector2D(0, 1),   // x -> i
            new Vector2D(-1, 2),  // x -> j
            new Vector2D(-1, 1),  // x -> k
            new Vector2D(-2, 1),  // x -> l
            new Vector2D(-1, 0),   // x -> m
            new Vector2D(-2, -1),   // x -> n
            new Vector2D(-1, -1),   // x -> o
            new Vector2D(-1, -2)    // x -> p
    };


    // Moves the figure at p1 to p2 if the move is valid
    public boolean moveIfValid(Point p1, Point p2, Field field) {
        if (isValid(p1, p2, field)) {        // if the move is valid
            field.move(p1,p2);                // move p1 to p2
            field.toggleWhiteOrBlack();       // change color for next move
            return true;
        }
        return false;
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
    public boolean isEmptyPath(Point p1, Point p2, int dirId, Field field) {
        int vX = (int)directions[dirId].x;
        int vY = (int)directions[dirId].y;

        int x = p1.x + vX;
        int y = p1.y + vY;

        while (x != p2.x || y != p2.y) {
            if (field.getCell(x,y)!=Figures.empty.id()){
                return false;
            }
            x += vX;
            y += vY;
        }
        return true;
    }

    // returns the length of the path
    public int getPathLength(Point p1, Point p2, int dirId) {
        int vX = (int)directions[dirId].x;
        int vY = (int)directions[dirId].y;

        int x = p1.x;
        int y = p1.y;

        int count = 0;
        while ((x!=p2.x) || (y!=p2.y)) {
            x += vX;
            y += vY;
            count ++;
        }
        return count;
    }


    // checks if the move is valid
    public boolean isValid(Point p1, Point p2, Field field) {
        FigureMask figureToMove= Figures.lookUpID(field.getCell(p1.x,p1.y)).getMask();
        FigureMask destinationFigure = Figures.lookUpID(field.getCell(p2.x,p2.y)).getMask();

        Vector2D v = new Vector2D(p1, p2);

        int dirId = this.getDirectionID(v);
        int moveMask = this.setBit(0, dirId);
        int captureMask = this.setBit(0, dirId + 16);


        if (!(figureToMove.id * field.getWhiteOrBlack() >= 0))
            return false;       // not the turn of the player or empty field

        if (dirId < 0)
            return false;       // wrong direction

        // Limit movement to one field if applicable
        if(getPathLength(p1,p2,dirId)>1 && figureToMove.limited){

            if((figureToMove.bitMask & moveMask)!=0)  {
            // Rule Exception for first move of pawns
            if(getPathLength(p1,p2,dirId)==2 &&
                    figureToMove.id==Figures.pawnBlack.id() &&
                    p1.y==1)
                return true;

            // Rule Exception for first move of pawns
            if(getPathLength(p1,p2,dirId)==2 &&
                    figureToMove.id==Figures.pawnWhite.id() &&
                    p1.y==6)
                return true;
            }
            return false;
        }

        if ((figureToMove.bitMask & moveMask) == 0 && destinationFigure.id == Figures.empty.id()){
            return false;   // player may not move in this direction and there is no enemy figure to capture
        }else if ((figureToMove.bitMask & moveMask) != 0 && destinationFigure.id == Figures.empty.id() && isEmptyPath(p1, p2, dirId, field))
            return true;                    // f1 may move in this direction f2 is empty => f1 may move to f2
        if ((figureToMove.bitMask & captureMask) != 0 && (destinationFigure.id * figureToMove.id < 0) && isEmptyPath(p1, p2, dirId, field))
            return true;                // f1 may move to, and capture f2
        return false;      // if something unexpected happens => fail
    }
}
