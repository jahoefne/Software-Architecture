package model;

        import java.awt.*;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:20
 * The Field represents the current Field state and may be altered using the methods declared in
 * this interface
 */
public interface IField {

    int[][] getField();

    /**
     * Returns value of the cell at Point(x,y)
     * @param x the x coordinate of the cell
     * @param y  the x coordinate of the cell
     * @return the value of the cell at Point(x,y)
     **/
     int getCell(int x, int y);

    /**
     * Returns value of the cell at Point p
     * @param p the Point
     * @return the value of the cell at Point p
     **/
     int getCell(Point p);

    /**
     * Moves pos1, to pos2. The value at pos2 is replaces with the value of pos1
     * and pos1 set to 0
     * @param pos1 the source position
     * @param pos2 the destination position
     **/
     void move(Point pos1, Point pos2);

    /**
     * Sets the cell at point p to the value value
     * @param p the position cell to alter
     * @param value the new value for the cell at p
     **/
     void set(Point p, int value);

    /**
     * Toggles whether it is the white or the black players turn
     **/
     void toggleWhiteOrBlack();


    /**
     * Returns whether it's the white or the black players turn
     * @return <0 if black turn, >0 of whites turn
     */
     byte getWhiteOrBlack();
}
