package model;

        import java.awt.*;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:20
 */
public interface IField {

     byte getCell(int x, int y);

     byte getCell(Point p);

     void move(Point pos1, Point pos2);

     void set(Point p, byte value);

     void toggleWhiteOrBlack();

     byte getWhiteOrBlack();
}
