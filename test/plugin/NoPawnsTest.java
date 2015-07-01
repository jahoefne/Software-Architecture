package plugin;

import controller.GameController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class NoPawnsTest {
    private NoPawns clazz;
    GameController gc;

    @Before
    public void setUp() throws Exception {
        clazz = new NoPawns();
        gc = new GameController("0"," a");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGameCreatedPlugin() throws Exception {
        clazz.gameCreatedPlugin(gc);
        assertFalse(clazz.getGameOver());
    }

    @Test
    public void testMoveCalledPlugin() throws Exception {
        clazz.moveCalledPlugin(gc, new Point(0, 0), new Point(1, 0));
        assertFalse(clazz.getGameOver());
    }

    @Test
    public void testGameOver() throws Exception {
        clazz.gameOver(null);
        assertFalse(clazz.getGameOver());
    }

    @Test
    public void testGetGameOver() throws Exception {
        assertFalse(clazz.getGameOver());
    }
}