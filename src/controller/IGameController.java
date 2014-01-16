package controller;

import java.awt.*;
import util.IObservable;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:36
 *
 * Game controller Interface
 */
public interface IGameController extends IObservable {

    /**
     * Restarts the game
     *
     * <p>
     * Restarts the game, by resetting, the model and controller state,
     * the caller must assure to also reset the UI.
     * <p>
     */
    void resetGame();

    /**
     * Returns an Array of Points the Figure at Point p is able to move to
     *
     * @param p The source field
     * @return The possible fields p may be moved to
     */
    Point[] getPossibleMoves(Point p);

    /**
     * Tries to move the Figure at field x to field y
     *
     * @param x the source field
     * @param y the destination field
     * @return TRUE, if move successful. FALSE, if move not legal
     */
    boolean move(Point x, Point y);

    /**
     * Returns the unicode string representing the figure at field x
     * @param x The field
     */
    String getUnicode(Point x);


    /**
     * Returns the model representation of the field x
     * @param x The field
     * @return 0 if the field is empty. <0 if the field is occupied by a black figure,
     * >0 if it is occupied by a white figure
     */
    byte getID(Point x);

    /**
     * Returns if it's the white players, or black players turn
     * @return TRUE, if white players turn, FALSE if black players turn
     */
    boolean whitesTurn();

    /**
     * Returns whether the game is over.
     * this is the case if only one king is left on the board, call this function after every move
     * if the return value is 'true' the player that did the last move won the game.
     * @return true if the game is over
     */
    boolean isGameOver();
    
    /**
     *  Returns if a king is in check
     * @return True if a king in check, otherwise false
     */
    public boolean getCheck();
    
    /**
     * Used to set check variable to false
     */
    public void setCheck();
}
