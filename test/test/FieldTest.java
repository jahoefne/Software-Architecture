package test;

import model.Field;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

public class FieldTest {

    private Field sut;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Testing the Field Class..");
    }

    @Before
    public void setUp() throws Exception {
        sut = new Field();
    }

    @Test
    public void initialSetup() {
        for (int i = 0; i < 8; i++) {
            assertTrue(
                    "Black Pawn at [0," + i + "] should be -1! Is"
                            + sut.getCell(i, 1), sut.getCell(i, 1) == -1);
        }
        for (int i = 0; i < 8; i++) {
            assertTrue(
                    "White Pawn at [0," + i + "] should be 1! Is"
                            + sut.getCell(i, 6), sut.getCell(i, 6) == 1);
        }
    }

    @Test
    public void set() {
        sut.set(new Point(0, 1), (byte) -2);
        assertTrue(sut.getCell(0, 1) == -2);
    }

    @Test
    public void move() {
        sut.move(new Point(0, 1), new Point(0, 2));
        assertTrue(sut.getCell(0, 2) == -1);

    }

    @Test
    public void toggleWhiteOrBlack() {
        assertTrue(sut.getWhiteOrBlack() == 1);
        sut.toggleWhiteOrBlack();
        assertTrue(sut.getWhiteOrBlack() == -1);
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Successful!");
    }

}
