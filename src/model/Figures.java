package model;

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
			"00000000100000000000001000100000",
			true,
            "\u265F"),

	rookBlack(
			(byte)-2,
            "10001000100010001000100010001000",
			false,
            "\u265C"),
	knightBlack(
			(byte)-3,
            "01010101010101010101010101010101",
			true,
            "\u265E"),
			
	bishopBlack(
			(byte)-4,
            "00100010001000100010001000100010",
			false,
            "\u265D"),
			
	queenBlack(
			(byte)-5,
            "10101010101010101010101010101010",
			false,
            "\u265B"),
			
	kingBlack(
			(byte)-6,
            "10101010101010101010101010101010",
			true,
            "\u265A"),
			
	empty(
			(byte)0,
            "00000000000000000000000000000000",
			true,
            ""),

	pawnWhite(
			(byte)1,
            "10000000000000000010000000000010" ,
			true,
            "\u265F"),
			
	rookWhite(
			(byte)2,
           "10001000100010001000100010001000",
			false,
            "\u265C"),
			
	knightWhite(
			(byte)3,
            "01010101010101010101010101010101",
			true,
            "\u265E"),
			
	bishopWhite(
			(byte)4,
           "00100010001000100010001000100010",
			false,
            "\u265D"),
			
	queenWhite(
			(byte)5,
            "10101010101010101010101010101010",
			false,
            "\u265B"),
			
	kingWhite(
			(byte)6,
            "10101010101010101010101010101010",
			true,
            "\u265A");


	private final FigureMask mask;
	
	Figures(byte id, String mask, boolean limited, String unicode){
		this.mask=new FigureMask(id,mask,limited,unicode);
	}

	public byte id(){
		return mask.id;
	}
	
	public FigureMask getMask(){
		return mask;
		}


    // for reverse lookup
    public static Figures lookUpID(byte id) {
        for(Figures s : values()) {
            if(s.id() == id)
                return s;
        }
        return null;
    }
	
}