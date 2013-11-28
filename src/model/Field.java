package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;


// Denotes a Chess field.
// The field is realized as a 2D byte Array, with one byte for each cell.
// The state of the field is denoted by the enum defined in
// Figures.java
public class Field {

    private byte[][] field;
    private static final int Len = 8;

    private byte whiteOrBlack = 1;
    // Which player can move next ->
    //	    negative => Black
    //      positive => White

    // Resets the field
    final void reset() {
        whiteOrBlack = 1;
        field = new byte[Len][Len];
        for (int x = 0; x < 8; x++) {
            field[x][1] = Figures.PawnBlack.id();
            field[x][6] = Figures.PawnWhite.id();
        }

        field[0][0] = Figures.RookBlack.id();
        field[1][0] = Figures.KnightBlack.id();
        field[2][0] = Figures.BishopBlack.id();
        field[3][0] = Figures.QueenBlack.id();
        field[4][0] = Figures.KingBlack.id();
        field[5][0] = Figures.BishopBlack.id();
        field[6][0] = Figures.KnightBlack.id();
        field[7][0] = Figures.RookBlack.id();

        field[0][7] = Figures.RookWhite.id();
        field[1][7] = Figures.KnightWhite.id();
        field[2][7] = Figures.BishopWhite.id();
        field[3][7] = Figures.QueenWhite.id();
        field[4][7] = Figures.KingWhite.id();
        field[5][7] = Figures.BishopWhite.id();
        field[6][7] = Figures.KnightWhite.id();
        field[7][7] = Figures.RookWhite.id();
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

    public Point[] getKingsPositions(){
        Collection<Point> list = new ArrayList<Point>();
    	for(int x=0; x<8;x++){
    		for(int y=0; x<8; y++){
    			if(Math.abs(this.getCell(x,y)) == Math.abs(Figures.KingWhite.id())){
    					list.add(new Point(x,y));
    			}
    		}
    	}
    	return (Point[])list.toArray();
    }

    public void set(Point p, byte value) {
        field[p.x][p.y] = value;
    }

    @Override
    public String toString() {
        String rep = (whiteOrBlack > 0) ? "Whites " : "Blacks ";
        rep += "Turn!\n";
        for (int y = 0; y < Len; y++) {
            for (int x = 0; x < Len; x++) {
                rep += (field[x][y] >= 0) ? " " + field[x][y] + " |" :
                        field[x][y] + " |";
            }
            rep += "\n";
        }
        return rep;
    }

    public void toggleWhiteOrBlack() {
        whiteOrBlack *= -1;
    }

    public byte getWhiteOrBlack() {
        return whiteOrBlack;
    }
}
