package tests;

import controller.GameController;
import model.Figures;
import org.junit.Test;

import java.awt.*;

/**
 * User: jahoefne
 * Creation Date: 03.11.13
 * Time: 11:56
 */
public class GameControllerTest {

    GameController sut = new GameController();

   /* @Test
    public void testReset() throws Exception {

    }*/

    @Test
    public void testMove() throws Exception {

     System.out.print(sut.toString());
     sut.move(new Point(2,1),new Point(2,2));
     System.out.print(sut.toString());
    }
}
