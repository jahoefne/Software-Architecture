package model;

import java.awt.Point;

// Denotes a Chess field.
// The field is realized as a 2D byte Array, with one byte for each cell.
// The state of the field is denoted by the enum defined in
// Figures.java
public class Field {

	private byte[][] field;

	private static int len = 8;
	
	// Resets the field
	public void reset(){
		field = new byte[len][len];
		for(int x=0;x<8;x++){
			field[1][x]=Figures.pawnBlack.id();
			field[6][x]=Figures.pawnWhite.id();			
		}
		
		for(int x=0;x<8;x+=7){
			field[x][0]= (x==0) ? Figures.rookBlack.id() : Figures.rookWhite.id();
			field[x][1]= (x==0) ? Figures.knightBlack.id() : Figures.knightWhite.id();
			field[x][2]= (x==0) ? Figures.bishopBlack.id(): Figures.bishopWhite.id();
			field[x][3]= (x==0) ? Figures.queenBlack.id() : Figures.queenWhite.id();
			field[x][4]= (x==0) ? Figures.kingBlack.id() : Figures.kingWhite.id();
			field[x][5]= (x==0) ? Figures.bishopBlack.id(): Figures.bishopWhite.id();
			field[x][6]= (x==0) ? Figures.knightBlack.id() : Figures.knightWhite.id();
			field[x][7]= (x==0) ? Figures.rookBlack.id() : Figures.rookWhite.id();
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
		field[pos1.x][pos1.y]=0;
	}

	@Override
	public String toString() {
		String rep = "";
		for (int x = 0; x < len; x++) {
			for (int y = 0; y < len; y++) {
				rep += (field[x][y]>=0) ? " "+field[x][y]+" |":
					field[x][y]+" |";
			}
			rep+="\n";
		}
		return rep;
	}
}
