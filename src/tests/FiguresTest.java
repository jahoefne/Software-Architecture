package tests;



import model.FigureMask;
import model.Figures;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FiguresTest {

	@BeforeClass
	public static void beforeClass(){
		System.out.println("Testing the Figures Class..");
	}

    @Test
	public void correctReturnValues() {
		assertTrue(
				"Empty field should be represented as 0! (Is "
						+ Figures.empty.id(), Figures.empty.id() == 0);
		assertTrue(
				"White pawn field should be represented as 1! (Is "
						+ Figures.pawnWhite.id(), Figures.pawnWhite.id() == 1);
		assertTrue(
				"White rook field should be represented as 2! (Is "
						+ Figures.rookWhite.id(), Figures.rookWhite.id() == 2);
		assertTrue(
				"White Knight should be represented as 3! (Is "
						+ Figures.knightWhite.id(), Figures.knightWhite.id() == 3);
		assertTrue(
				"White Bishop field should be represented as 4! (Is "
						+ Figures.bishopWhite.id(), Figures.bishopWhite.id() == 4);
		assertTrue(
				"White Queen field should be represented as 5! (Is "
						+ Figures.queenWhite.id(), Figures.queenWhite.id() == 5);
		assertTrue(
				"White King field should be represented as 6! (Is "
						+ Figures.kingWhite.id(), Figures.kingWhite.id() == 6);

		assertTrue(
				"Black pawn field should be represented as -1! (Is "
						+ Figures.pawnBlack.id(), Figures.pawnBlack.id() == -1);
		assertTrue(
				"Black rook field should be represented as -2! (Is "
						+ Figures.rookBlack.id(), Figures.rookBlack.id() == -2);
		assertTrue(
				"Black Knight should be represented as -3! (Is "
						+ Figures.knightBlack.id(), Figures.knightBlack.id() == -3);
		assertTrue(
				"Black Bishop field should be represented as -4! (Is "
						+ Figures.bishopBlack.id(), Figures.bishopBlack.id() == -4);
		assertTrue(
				"Black Queen field should be represented as -5! (Is "
						+ Figures.queenBlack.id(), Figures.queenBlack.id() == -5);
		assertTrue(
				"Black King field should be represented as -6! (Is "
						+ Figures.kingBlack.id(), Figures.kingBlack.id() == -6);
	}
	
    @Test
    public void lookUpID(){
    	assertTrue(Figures.lookUpID((byte)7) == null);
    	assertTrue(Figures.lookUpID((byte)-7) == null);
    	
    	assertTrue(Figures.lookUpID((byte)-1) == Figures.pawnBlack);
    	assertTrue(Figures.lookUpID((byte)-2) == Figures.rookBlack);
    	assertTrue(Figures.lookUpID((byte)-3) == Figures.knightBlack);
    	assertTrue(Figures.lookUpID((byte)-4) == Figures.bishopBlack);
    	assertTrue(Figures.lookUpID((byte)-5) == Figures.queenBlack);
    	assertTrue(Figures.lookUpID((byte)-6) == Figures.kingBlack);
    	
    	assertTrue(Figures.lookUpID((byte)1) == Figures.pawnWhite);
    	assertTrue(Figures.lookUpID((byte)2) == Figures.rookWhite);
    	assertTrue(Figures.lookUpID((byte)3) == Figures.knightWhite);
    	assertTrue(Figures.lookUpID((byte)4) == Figures.bishopWhite);
    	assertTrue(Figures.lookUpID((byte)5) == Figures.queenWhite);
    	assertTrue(Figures.lookUpID((byte)6) == Figures.kingWhite);
    	
    }
    
    @Test
    public void getMask(){
    	
    	// TODO: Test mask.bitMask!
    	
    	FigureMask mask;
    	
    	mask = Figures.pawnBlack.getMask();
    	assertTrue(mask.id == -1 && mask.limited== true);   
    	
    }
	@AfterClass
	public static void afterClass(){
		System.out.println("Successful!");
	}

}
