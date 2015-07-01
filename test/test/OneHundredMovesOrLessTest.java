package test;

import com.sun.org.apache.xerces.internal.util.XMLSymbols;
import controller.GameController;
import model.Field;
import model.Figures;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class OneHundredMovesOrLessTest {
    GameController gc1, gc2;

    @Before
    public void setUp() throws Exception {
        gc1 = new GameController("0"," a");
        gc2 = new GameController("1", "b");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGameCreatedPlugin() throws Exception {

    }

    @Test
    public void testMoveCalledPlugin() throws Exception {
        Field f = new Field();
        f.set(new Point(0, 0), Figures.Empty.id());

        gc1.setField(f);
        gc2.setField(f);

        assertTrue(gc1.toString().hashCode() == gc2.toString().hashCode());
        f.set(new Point(0, 0), Figures.PawnBlack.id());
        assertTrue(gc1.toString().hashCode() != gc2.toString().hashCode());
    }

    @Test
    public void testGameOver() throws Exception {

    }

    @Test
    public void testGetGameOver() throws Exception {

    }
}