package model;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:29
 */
public interface IFigureMask {
     byte getId();
     int getBitMask();
     boolean isLimited();
     String getUnicode();
}
