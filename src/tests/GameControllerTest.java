package tests;

import controller.GameController;
import org.junit.Test;

import java.awt.*;

/**
 * User: jahoefne
 * Creation Date: 03.11.13
 * Time: 11:56
 */
public class GameControllerTest {

    private final GameController sut = new GameController();

   /* @Test
    public void testReset() throws Exception {

    }*/

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
}
