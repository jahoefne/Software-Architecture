package model;

import java.util.HashMap;

// Associates the bytes, which are internally used for the representation 
// of the different cell states, with human readable ids.
// -> Improves readability of the Model & Controller code
// And also:
//Contains a bitmask used for validating moves and captures of enemy figures.
//There are 16 relevant directions a figure might be moved or might capture:
//o p a b c
//n - - - d
//m - x - e
//l - - - f
//k j i h g  
//With these 16 values the movement direction of any type of figure may be
//described.
//Because movement and and capturing is not always identical (see pawn)
//another 16 bit mask is needed for describing the capture capabilities.
//
//Further one more bit is needed to describe if the figure may be moved an 
//unlimited number of steps in this direction or just one step
//
//This implementation uses a 32-bit int for representing the capture and the 
//movement mask and a boolean to define the movement limitation.
//This is combined in the class FigureMask.

public enum Figures {
	pawnBlack(
			(byte)-1,
			//0b00000000100000000000001000100000,
            8389152,
			true),	
	rookBlack(
			(byte)-2,
           // 0b10001000100010001000100010001000,
            -2004318072,
			false),
			
	knightBlack(
			(byte)-3,
          //  0b01010101010101010101010101010101,
            1431655765,
			true),
			
	bishopBlack(
			(byte)-4,
           // 0b00100010001000100010001000100010,
            572662306,
			false),
			
	queenBlack(
			(byte)-5,
           // 0b10101010101010101010101010101010,
            -1431655766,
			false),
			
	kingBlack(
			(byte)-6,
          //  0b10101010101010101010101010101010,
            -1431655766,
			true),
			
	empty(
			(byte)0,
           // 0b00000000000000000000000000000000,
            0,
			true),
			
	pawnWhite(
			(byte)1,
          //  0b10000000000000000010000000000010,
            -2147475454,
			true),
			
	rookWhite(
			(byte)2,
           // 0b10001000100010001000100010001000,
            -2004318072,
			false),
			
	knightWhite(
			(byte)3,
        //    0b01010101010101010101010101010101,
            1431655765,
			true),
			
	bishopWhite(
			(byte)4,
           // 0b00100010001000100010001000100010,
            572662306,
			false),
			
	queenWhite(
			(byte)5,
            //0b10101010101010101010101010101010,
            -1431655766,
			false),
			
	kingWhite(
			(byte)6,
           // 0b10101010101010101010101010101010,
            -1431655766,
			true);
	
	
	
	FigureMask mask;
	
	Figures(byte id, int mask, boolean limited){
		this.mask=new FigureMask(id,mask,limited);
	}
	
	public byte id(){
		return mask.id;
	}
	
	public FigureMask getMask(){
		return mask;
		}
	
	// used for reverse lookup -> maps Id to FigrueMasks
	public static HashMap<Byte,FigureMask> getMap(){
		HashMap<Byte, FigureMask> map = new HashMap<Byte, FigureMask>();
		map.put(pawnBlack.id(),pawnBlack.getMask());
		map.put(rookBlack.id(),rookBlack.getMask());
		map.put(knightBlack.id(),knightBlack.getMask());
		map.put(bishopBlack.id(),bishopBlack.getMask());
		map.put(queenBlack.id(),queenBlack.getMask());
		map.put(kingBlack.id(),kingBlack.getMask());
		map.put(empty.id(),empty.getMask());
		map.put(pawnWhite.id(),pawnWhite.getMask());
		map.put(rookWhite.id(),rookWhite.getMask());
		map.put(knightWhite.id(),knightWhite.getMask());
		map.put(bishopWhite.id(),bishopWhite.getMask());
		map.put(queenWhite.id(),queenWhite.getMask());
		map.put(kingWhite.id(),kingWhite.getMask());
		return map;
	}
	
}