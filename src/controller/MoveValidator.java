package controller;

import model.Figures;
import model.IField;
import model.IFigureMask;

import java.awt.*;

public class MoveValidator {

    public static final int NUMBER_OF_DIRECTIONS = 16;
    //- p - b -
    //n o a c d
    //- m x e -
    //l l i g f
    //- j - h -
    private static final Vector2D[] DIRECTIONS = {
     // denotes the 16 possible direction vectors
            new Vector2D(0, -1),
            // x -> a
            new Vector2D(1, -2),
             // x -> b
            new Vector2D(1, -1),
             // x -> c
            new Vector2D(2, -1),
            // x -> d
            new Vector2D(1, 0),
            // x -> e
            new Vector2D(2, 1),
            // x -> f
            new Vector2D(1, 1),
             // x -> g
            new Vector2D(1, 2),
             // x -> h
            new Vector2D(0, 1),
             // x -> i
            new Vector2D(-1, 2),
             // x -> j
            new Vector2D(-1, 1),
             // x -> k
            new Vector2D(-2, 1),
             // x -> l
            new Vector2D(-1, 0),
             // x -> m
            new Vector2D(-2, -1),
              // x -> n
            new Vector2D(-1, -1),
              // x -> o
            new Vector2D(-1, -2)
              // x -> p
    };

    // Moves the figure at p1 to p2 if the move is valid
    public boolean moveIfValid(Point p1, Point p2, IField field) {
        if (isValid(p1, p2, field)) {
        // if the move is valid
            field.move(p1, p2);
             // move p1 to p2
            field.toggleWhiteOrBlack();
            // change color for next move
            return true;
        }
        return false;
    }

    // returns the ID (0-15) if 'v' points in the same direction, or -1 if it is not identical with
    // any of the DIRECTIONS
    private int getDirectionID(Vector2D v) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (Vector2D.sameDirection(v, DIRECTIONS[i])) {
                return i;
            }
        }
        return -1;
    }

    // return s int with the nth bit set
    int setBit(int n) {
        return 1 << n;
    }

    // returns whether the path BETWEEN p1 and p2 is empty
    boolean isEmptyPath(Point p1, Point p2, int dirId, IField field) {
        int vX = (int) DIRECTIONS[dirId].getX();
        int vY = (int) DIRECTIONS[dirId].getY();

        int x = p1.x + vX;
        int y = p1.y + vY;

        while (x != p2.x || y != p2.y) {
            if (field.getCell(x, y) != Figures.Empty.id()) {
                return false;
            }
            x += vX;
            y += vY;
        }
        return true;
    }

    // returns the length of the path
    int getPathLength(Point p1, Point p2, int dirId) {
        int vX = (int) DIRECTIONS[dirId].getX();
        int vY = (int) DIRECTIONS[dirId].getY();

        int x = p1.x;
        int y = p1.y;

        int count = 0;
        while ((x != p2.x) || (y != p2.y)) {
            x += vX;
            y += vY;
            count++;
        }
        return count;
    }


    private static final int PAWN_STARTPOS = 6;
    // returns whether figure is a pawn and pos is a pawn start-position
    private boolean isPawnInStartPosition(IFigureMask figure, Point pos) {
        if ((figure.getId() == Figures.PawnBlack.id() && pos.y == 1)) {
            return false;
        } else if (figure.getId() == Figures.PawnWhite.id() && pos.y == PAWN_STARTPOS) {
            return false;
        }
        return true;
    }

    // Checks whether the movement of the current figure must be limited or not
    private boolean isMovementLimited(int pathLength, IFigureMask figure, Point pos) {
        // Limit movement to one field if applicable
        if (pathLength > 1 && figure.isLimited()) {
            // Rule Exception for first move of pawns
            return pathLength != 2 || isPawnInStartPosition(figure, pos);
        }
        return false;
    }




    // checks if the move is valid
    public boolean isValid(Point p1, Point p2, IField field) {
        IFigureMask figureToMove = Figures.lookUpID(field.getCell(p1.x, p1.y)).getMask();
        IFigureMask destinationFigure = Figures.lookUpID(field.getCell(p2.x, p2.y)).getMask();

        Vector2D v = new Vector2D(p1, p2);

        int dirId = this.getDirectionID(v);
        int moveMask = this.setBit(dirId);
        int captureMask = this.setBit(dirId + NUMBER_OF_DIRECTIONS);

        if (mayNotMove(p1, p2, field, figureToMove, dirId))
            return false;

        if ((figureToMove.getBitMask() & moveMask) == 0 && destinationFigure.getId() == Figures.Empty.id()) {
            return false;
            // player may not move in this direction and there is no enemy figure to capture
        } else if ((figureToMove.getBitMask() & moveMask) != 0 && destinationFigure.getId() == Figures.Empty.id() && isEmptyPath(p1,
                p2, dirId, field)) {
            return true;
            // f1 may move in this direction f2 is empty => f1 may move to f2
        }

        return (figureToMove.getBitMask() & captureMask) != 0 && (destinationFigure.getId() * figureToMove.getId() < 0) && isEmptyPath(p1,
                p2, dirId, field);
    }

    private boolean mayNotMove(Point p1, Point p2, IField field, IFigureMask figureToMove, int dirId) {

        if (!(figureToMove.getId() * field.getWhiteOrBlack() >= 0) || dirId < 0) {
            return true;
            // not the turn of the player or empty field    // wrong direction
        }

        // if the movement of the figure must be limited to one step, the move is not valid
        if (isMovementLimited(getPathLength(p1, p2, dirId), figureToMove, p1)) {
            return true;
        }
        return false;
    }
}
