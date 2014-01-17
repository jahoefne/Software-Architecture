package test;

import controller.GameController;
import controller.IGameController;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

/**
 * User: jahoefne
 * Creation Date: 03.11.13
 * Time: 11:56
 */
public class GameControllerTest {

    private IGameController sut;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Testing the GameController Class..");
    }

    @Before
    public void before() {
        sut = GameController.getInstance();
        sut.resetGame();
    }
    
    @Test
    public void isGameOverTest(){
        assertTrue("The should not be a check mate in the initial game position!",
                sut.isGameOver()==false);
    }

    @Test
    public void getIDTest(){
        assertTrue("Empty field should have the ID 0", sut.getID(new Point(3,3))==0);
        assertTrue("Black Pawn should have the ID -1", sut.getID(new Point(0,1))==-1);
        assertTrue("White Pawn should have the ID 1", sut.getID(new Point(0,6))==1);
        assertTrue("Black King should have the ID -6", sut.getID(new Point(4,0))==-6);
        assertTrue("White King should have the ID 6", sut.getID(new Point(4,7))==6);
    }

    @Test
    public void getPossibleMovesTest() throws Exception {
        // try to move pawn at 0:1
        // This is a black pawn -> no movements possible
        assertTrue("Black pawn should not be allowed to move in initial game position",
                sut.getPossibleMoves(new Point(0,1)).length==0);

        // Now try to move pawn at 0:6
        // This is a white pawn -> movements to possible 0:5 && 0:4 possible
        Point[] possible = sut.getPossibleMoves(new Point(0,6));
        assertTrue("There should be two possible fields to move: is: "+possible.length,
                possible.length==2);
    }

    @Test
    public void testMove() throws Exception {
        System.out.print(sut.toString());
        sut.move(new Point(1, 6), new Point(1, 5));
        System.out.print(sut.toString());
        sut.move(new Point(1, 5), new Point(1, 4));
        System.out.print(sut.toString());
        sut.move(new Point(1, 1), new Point(1, 2));
        System.out.print(sut.toString());
        sut.move(new Point(1, 5), new Point(1, 4));
        System.out.print(sut.toString());
    }

    @Test
    public void getUnicode() {
        for (int i = 0; i < 8; i++) {
            //testing pawn unicodes
            assertTrue(sut.getUnicode(new Point(i, 1)).compareTo("\u265F") == 0);
            assertTrue(sut.getUnicode(new Point(i, 6)).compareTo("\u2659") == 0);
        }
    }
   
    @Test
    public void whitesTurn() {
    	sut = new GameController();
    	sut.resetGame();
        assertTrue(sut.whitesTurn() == true);
        sut.move(new Point(1, 6), new Point(1, 5));
        assertTrue(sut.whitesTurn() == false);
        sut.move(new Point(1, 1), new Point(1, 3));
        assertTrue(sut.whitesTurn() == true);
    }
    @Test
    public void checkTest(){
    	sut.move(new Point(2, 6), new Point(2, 5));
    	sut.move(new Point(3, 1), new Point(3, 2));
    	sut.move(new Point(3, 7), new Point(0, 4));
    	assertTrue( 1 > 0 );
    }
    

    	
}




