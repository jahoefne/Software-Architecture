package controller;

import java.awt.*;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:36
 *
 */
public interface IGameController {

    // restarts the game
    void resetGame();

    // get an array of all possible moves of the figure at point p
    Point[] getPossibleMoves(Point p);

    // moves figure at point x, to point y, returns if the move was successful
    boolean move(Point x, Point y);

    // returns the unicode representation of the figure at point x
    String getUnicode(Point x);

    // is used to determine the colour of a figure on field x
    // >0 white, <0 black
    byte getID(Point x);

    // obvious
    boolean whitesTurn();

    // obvious
    boolean isCheckMate();
}
