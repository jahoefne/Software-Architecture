package plugin;

import controller.GameController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FrenchRevolutionPluginTest {
    private FrenchRevolutionPlugin clazz;
    GameController gc;

    @Before
    public void setUp() throws Exception {
        clazz = new FrenchRevolutionPlugin();
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

    }

    @Test
    public void testGameOver() throws Exception {

    }

    @Test
    public void testGetGameOver() throws Exception {

    }
}