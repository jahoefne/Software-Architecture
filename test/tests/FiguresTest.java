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
						+ Figures.Empty.id(), Figures.Empty.id() == 0);
		assertTrue(
				"White pawn field should be represented as 1! (Is "
						+ Figures.PawnWhite.id(), Figures.PawnWhite.id() == 1);
		assertTrue(
				"White rook field should be represented as 2! (Is "
						+ Figures.RookWhite.id(), Figures.RookWhite.id() == 2);
		assertTrue(
				"White Knight should be represented as 3! (Is "
						+ Figures.KnightWhite.id(), Figures.KnightWhite.id() == 3);
		assertTrue(
				"White Bishop field should be represented as 4! (Is "
						+ Figures.BishopWhite.id(), Figures.BishopWhite.id() == 4);
		assertTrue(
				"White Queen field should be represented as 5! (Is "
						+ Figures.QueenWhite.id(), Figures.QueenWhite.id() == 5);
		assertTrue(
				"White King field should be represented as 6! (Is "
						+ Figures.KingWhite.id(), Figures.KingWhite.id() == 6);

		assertTrue(
				"Black pawn field should be represented as -1! (Is "
						+ Figures.PawnBlack.id(), Figures.PawnBlack.id() == -1);
		assertTrue(
				"Black rook field should be represented as -2! (Is "
						+ Figures.RookBlack.id(), Figures.RookBlack.id() == -2);
		assertTrue(
				"Black Knight should be represented as -3! (Is "
						+ Figures.KnightBlack.id(), Figures.KnightBlack.id() == -3);
		assertTrue(
				"Black Bishop field should be represented as -4! (Is "
						+ Figures.BishopBlack.id(), Figures.BishopBlack.id() == -4);
		assertTrue(
				"Black Queen field should be represented as -5! (Is "
						+ Figures.QueenBlack.id(), Figures.QueenBlack.id() == -5);
		assertTrue(
				"Black King field should be represented as -6! (Is "
						+ Figures.KingBlack.id(), Figures.KingBlack.id() == -6);
	}
	
    @Test
    public void lookUpID(){
    	assertTrue(Figures.lookUpID((byte)7) == null);
    	assertTrue(Figures.lookUpID((byte)-7) == null);
    	
    	assertTrue(Figures.lookUpID((byte)-1) == Figures.PawnBlack);
    	assertTrue(Figures.lookUpID((byte)-2) == Figures.RookBlack);
    	assertTrue(Figures.lookUpID((byte)-3) == Figures.KnightBlack);
    	assertTrue(Figures.lookUpID((byte)-4) == Figures.BishopBlack);
    	assertTrue(Figures.lookUpID((byte)-5) == Figures.QueenBlack);
    	assertTrue(Figures.lookUpID((byte)-6) == Figures.KingBlack);
    	
    	assertTrue(Figures.lookUpID((byte)1) == Figures.PawnWhite);
    	assertTrue(Figures.lookUpID((byte)2) == Figures.RookWhite);
    	assertTrue(Figures.lookUpID((byte)3) == Figures.KnightWhite);
    	assertTrue(Figures.lookUpID((byte)4) == Figures.BishopWhite);
    	assertTrue(Figures.lookUpID((byte)5) == Figures.QueenWhite);
    	assertTrue(Figures.lookUpID((byte)6) == Figures.KingWhite);
    	
    }
    
    @Test
    public void getMask(){
    	
    	// TODO: Test mask.bitMask!
    	
    	FigureMask mask;
    	
    	mask = Figures.PawnBlack.getMask();
    	assertTrue(mask.id == -1 && mask.limited);
    	
    }

	@AfterClass
	public static void afterClass(){
		System.out.println("Successful!");
	}

}
