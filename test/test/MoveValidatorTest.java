package test;

import model.Figures;
import static org.junit.Assert.*;

import java.awt.Point;

import controller.MoveValidator;
import model.Field;

import org.junit.Before;
import org.junit.Test;

public class MoveValidatorTest {

    private MoveValidator sut;
    private Field field;
    
    @Before
    public void setUp() throws Exception {
        sut = new MoveValidator();
        field = new Field();
    }
    @Test
    public void moveIfValid() {
    	//White Pawn 1,6 -> 1,5 -white turn
    	assertTrue(sut.moveIfValid(new Point(1,6), new Point(1,5), field) && (field.getCell(new Point(1,5)) == 1));
    	//White Pawn 0,6 -> 0,5 -blacks turn
    	assertFalse(sut.moveIfValid(new Point(0,6), new Point(1,5), field));
    	//Black Pawn 0,1 -> 0,3 -blacks turn
    	assertTrue(sut.moveIfValid(new Point(0,1), new Point(0,3), field) && (field.getCell(new Point(0,3)) == -1));
    	//Black Pawn 1,1 -> 1,2 -whites turn
    	assertFalse(sut.moveIfValid(new Point(1,1), new Point(1,2), field));
    	//White Queen 3,7 -> 3,4 -whites turn
    	assertFalse(sut.moveIfValid(new Point(3,7), new Point(3,4), field));
    	//White Pawn 4,6 ->  4,4 -whites turn
    	assertTrue(sut.moveIfValid(new Point(4,6), new Point(4,4), field) && (field.getCell(new Point(4,4)) == 1));
    	//Black knight 1,0 -> 2,2 -blacks turn
    	assertTrue(sut.moveIfValid(new Point(1,0), new Point(2,2), field) && (field.getCell(new Point(2,2)) == -3));
    	//White queen 3,7 -> 7,3 -whites turn
    	assertTrue(sut.moveIfValid(new Point(3,7), new Point(7,3), field) && (field.getCell(new Point(7,3)) == 5));
    	//Black pawn not in starting position 0,3 -> 0,4 -black turn
    	assertTrue(sut.moveIfValid(new Point(0,3), new Point(0,4), field) && (field.getCell(new Point(0,4)) == -1));
    }

    
    @Test
    public void isValid(){
    	//White Pawn 1,6 -> 1,5
    	assertTrue(sut.isValid(new Point(1,6), new Point(1,5), field));
    	//Black Pawn 0,1 -> 0,3
    	assertFalse(sut.isValid(new Point(0,1), new Point(0,3), field));
    	field.toggleWhiteOrBlack();
    	//Black Pawn 0,1 -> 0,3
    	assertTrue(sut.isValid(new Point(0,1), new Point(0,3), field));
    }
    
    
}
