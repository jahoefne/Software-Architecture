package plugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoPawnsTest {
    private NoPawns clazz;

    @Before
    public void setUp() throws Exception {
        clazz = new NoPawns();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGameCreatedPlugin() throws Exception {

    }

    @Test
    public void testMoveCalledPlugin() throws Exception {
        clazz.moveCalledPlugin(null, null, null);
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