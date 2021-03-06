package model;


import javax.persistence.*;
import java.awt.*;
import java.util.Arrays;


// Denotes a Chess field.
// The field is realized as a 2D byte Array, with one byte for each cell.
// The state of the field is denoted by the enum defined in
// Figures.java
@Entity
public class Field implements IField{

    @Column( length = 100000 )
    private int[][] field;

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


    public Field(int[][] field, byte whiteOrBlack){
        this.field=copy2dArray(field);
        this.whiteOrBlack = whiteOrBlack;
    }

    // Resets the field
    final void reset() {
        whiteOrBlack = ONE;
        field = new int[LEN][LEN];
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

    @Override
    public int[][] getField() {
        return field;
    }

    /** To Make Sonar Happy */
    public final int[][] copy2dArray(int[][] src) {
        int[][] dst = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            dst[i] = Arrays.copyOf(src[i], src[i].length);
        }
        return dst;
    }

    public void setField(int[][] field) {
        this.field = copy2dArray(field);
    }

    public int getCell(int x, int y) {
        return this.getCell(new Point(x, y));
    }

    public int getCell(Point p) {
        return field[p.x][p.y];
    }

    public void move(Point pos1, Point pos2) {
        field[pos2.x][pos2.y] = field[pos1.x][pos1.y];
        field[pos1.x][pos1.y] = Figures.Empty.id();
    }

    public void set(Point p, int value) {
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

    public void setWhiteOrBlack(byte whiteOrBlack) {
        this.whiteOrBlack = whiteOrBlack;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
