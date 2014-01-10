package model;

import java.awt.*;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:20
 */
public interface IField {
    public byte getCell(int x, int y);
    public byte getCell(Point p);
    public void move(Point pos1, Point pos2);
    public void set(Point p, byte value);
    public void toggleWhiteOrBlack();
    public byte getWhiteOrBlack();
}
