package model;

// describes the FigureMask Bitmasks
public class FigureMask {
	public byte id;
	public int bitMask;
	public boolean limited;
	
	public FigureMask(byte id, int bitMask, boolean limited){
		this.bitMask=bitMask;
		this.limited=limited;
		this.id=id;
	}
}
