package model;

/**
 * User: jahoefne
 * Date: 10/01/14
 * Time: 12:29
 * Describes a figure, all figures are defined in the Figures enum
 */
public interface IFigureMask {
    /**
     * Gets the internal representation of the figure
     * @return the internal representation
     **/
    byte getId();

    /**
     * Returns the bitmask which describes the figures move and capture capabilities
     * @return the figure capabilities
     **/
    int getBitMask();

    /**
     * Returns wheter the figures movement is limite to one field of unlimited
     * E.g. Queens movement is unlimites, Kings movement limited
     * @return true if movement is limites, false if movement is unlimited
     **/
    boolean isLimited();

    /**
     * Returns the unicode representation of the figure
     * @return the unicode sign of the figure
     **/
    String getUnicode();
}
