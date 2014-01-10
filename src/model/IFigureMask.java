package model;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:29
 */
public interface IFigureMask {
    public byte getId();
    public int getBitMask();
    public boolean isLimited();
    public String getUnicode();
}
