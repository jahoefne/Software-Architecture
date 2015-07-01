package plugin;

import com.sun.org.apache.xerces.internal.util.XMLSymbols;
import controller.GameController;
import model.Field;
import model.Figures;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import plugin.OneHundredMovesOrLess;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class OneHundredMovesOrLessTest {
    GameController gc1, gc2;
    OneHundredMovesOrLess clazz;

    @Before
    public void setUp() throws Exception {
        gc1 = new GameController("0"," a");
        gc2 = new GameController("1", "b");
        clazz = new OneHundredMovesOrLess();
    }

    @Test
    public void testgameCreatedPlugin() throws Exception {
        clazz.gameCreatedPlugin(gc1);
        assertFalse(clazz.getGameOver());
    }

    @Test
    public void testMoveCalledPlugin() throws Exception {
        Field f1 = new Field(), f2 = new Field();
        f1.set(new Point(0, 0), Figures.Empty.id());
        f2.set(new Point(0, 0), Figures.Empty.id());

        gc1.setField(f1);
        gc2.setField(f2);

        clazz.moveCalledPlugin(gc1, new Point(0, 0), new Point(1, 0));
        clazz.moveCalledPlugin(gc2, new Point(0, 0), new Point(1, 0));

        assertTrue(gc1.toString().hashCode() == gc2.toString().hashCode());
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