package controller;

import java.awt.*;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:36
 *
 */
public interface IGameController {
    public void resetGame();

    public Point[] getPossibleMoves(Point p);
    public boolean move(Point x, Point y);

    public String getUnicode(Point x);

    public byte getID(Point x);

    public boolean whitesTurn();

    public boolean isCheckMate();
}
