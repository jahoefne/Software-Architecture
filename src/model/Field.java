package model;

import java.awt.Point;

// Denotes a Chess field.
// The field is realized as a 2D byte Array, with one byte for each cell.
// The state of the field is denoted by the enum defined in
// Figures.java
public class Field {

	private byte[][] field;
	private static int len = 8;

    private byte whiteOrBlack = 1; // Which player can move next ->
    //						        negative => Black
    //
    //      positive => White



    // Resets the field
	public void reset(){
        whiteOrBlack=1;
		field = new byte[len][len];
		for(int x=0;x<8;x++){
			field[x][1]=Figures.pawnBlack.id();
			field[x][6]=Figures.pawnWhite.id();
		}
		
		for(int x=0;x<8;x+=7){
			field[0][x]= (x==0) ? Figures.rookBlack.id() : Figures.rookWhite.id();
			field[1][x]= (x==0) ? Figures.knightBlack.id() : Figures.knightWhite.id();
			field[2][x]= (x==0) ? Figures.bishopBlack.id(): Figures.bishopWhite.id();
			field[3][x]= (x==0) ? Figures.queenBlack.id() : Figures.queenWhite.id();
			field[4][x]= (x==0) ? Figures.kingBlack.id() : Figures.kingWhite.id();
			field[5][x]= (x==0) ? Figures.bishopBlack.id(): Figures.bishopWhite.id();
			field[6][x]= (x==0) ? Figures.knightBlack.id() : Figures.knightWhite.id();
			field[7][x]= (x==0) ? Figures.rookBlack.id() : Figures.rookWhite.id();
		}
	}
	
	public Field() {
		this.reset();
	}

	public byte getCell(int x, int y){
		return this.getCell(new Point(x,y));
	}

	public byte getCell(Point p){
		return field[p.x][p.y];
	}
	
	public void move(Point pos1, Point pos2) {
		field[pos2.x][pos2.y]=field[pos1.x][pos1.y];
		field[pos1.x][pos1.y]=Figures.empty.id();
	}

	@Override
	public String toString() {
		String rep = (whiteOrBlack>0) ? "Whites " : "Blacks ";
        rep+="Turn!\n";
		for (int y = 0; y < len; y++) {
			for (int x = 0; x < len; x++) {
				rep += (field[x][y]>=0) ? " "+field[x][y]+" |":
					field[x][y]+" |";
			}
			rep+="\n";
		}
		return rep;
	}

    public void toggleWhiteOrBlack(){
        whiteOrBlack*=-1;
    }

    public byte getWhiteOrBlack() {
        return whiteOrBlack;
    }
}
