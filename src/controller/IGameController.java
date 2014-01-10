package controller;

import java.awt.*;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:36
 *
 */
public interface IGameController {
     void resetGame();

     Point[] getPossibleMoves(Point p);
     boolean move(Point x, Point y);

     String getUnicode(Point x);

     byte getID(Point x);

     boolean whitesTurn();

     boolean isCheckMate();
}
