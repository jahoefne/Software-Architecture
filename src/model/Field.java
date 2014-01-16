package model;

import java.awt.*;


// Denotes a Chess field.
// The field is realized as a 2D byte Array, with one byte for each cell.
// The state of the field is denoted by the enum defined in
// Figures.java
public class Field implements IField{

    private byte[][] field;
    private static final int LEN = 8;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;

    private byte whiteOrBlack = ONE;
    // Which player can move next ->
    //	    negative => Black
    //      positive => White

    // Resets the field
    final void reset() {
        whiteOrBlack = ONE;
        field = new byte[LEN][LEN];
        for (int x = ZERO; x < LEN; x++) {
            field[x][ONE] = Figures.PawnBlack.id();
            field[x][SIX] = Figures.PawnWhite.id();
        }

        field[ZERO][ZERO] = Figures.RookBlack.id();
        field[ONE][ZERO] = Figures.KnightBlack.id();
        field[TWO][ZERO] = Figures.BishopBlack.id();
        field[THREE][ZERO] = Figures.QueenBlack.id();
        field[FOUR][ZERO] = Figures.KingBlack.id();
        field[FIVE][ZERO] = Figures.BishopBlack.id();
        field[SIX][ZERO] = Figures.KnightBlack.id();
        field[SEVEN][ZERO] = Figures.RookBlack.id();

        field[ZERO][SEVEN] = Figures.RookWhite.id();
        field[ONE][SEVEN] = Figures.KnightWhite.id();
        field[TWO][SEVEN] = Figures.BishopWhite.id();
        field[THREE][SEVEN] = Figures.QueenWhite.id();
        field[FOUR][SEVEN] = Figures.KingWhite.id();
        field[FIVE][SEVEN] = Figures.BishopWhite.id();
        field[SIX][SEVEN] = Figures.KnightWhite.id();
        field[SEVEN][SEVEN] = Figures.RookWhite.id();
    }

    public Field() {
        this.reset();
    }

    public byte getCell(int x, int y) {
        return this.getCell(new Point(x, y));
    }

    public byte getCell(Point p) {
        return field[p.x][p.y];
    }

    public void move(Point pos1, Point pos2) {
        field[pos2.x][pos2.y] = field[pos1.x][pos1.y];
        field[pos1.x][pos1.y] = Figures.Empty.id();
    }

    public void set(Point p, byte value) {
        field[p.x][p.y] = value;
    }

    @Override
    public String toString() {
        String rep = (whiteOrBlack > ZERO) ? "Whites " : "Blacks ";
        String line = "\n-----------------------------------------"+
        		"------------------------";
        rep += "Turn!\n";
        rep += line + "\n";
        for (int y = ZERO; y < LEN; y++) {
        	rep += (LEN-y)+"|";
            for (int x = ZERO; x < LEN; x++) {
                rep += (field[x][y] != ZERO) ? "   " + Figures.lookUpID(field[x][y]).getMask().getUnicode() + "\t|" :
                	 "\t|";
            }
            rep += line;
            rep += "\n";
        }
        rep += "     A      B       C       D"+
        		"       E       F       G       H";
        return rep;
    }

    public void toggleWhiteOrBlack() {
        whiteOrBlack *= -1;
    }

    public byte getWhiteOrBlack() {
        return whiteOrBlack;
    }
}
