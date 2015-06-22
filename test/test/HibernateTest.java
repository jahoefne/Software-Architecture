package test;

import controller.GameController;
import org.junit.*;
import persistence.CouchGameDB;
import persistence.DB4OGameDB;
import persistence.HibernateGameDB;

import static org.junit.Assert.*;
import java.awt.*;


public class HibernateTest{
    private HibernateGameDB sut;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Testing Hibernate with MySql");
    }

    @Before
    public void setUp() throws Exception {
        sut = new HibernateGameDB();
    }

    @Test
    public void addReceiveController() {
        GameController save = new GameController();
        System.out.println("\tTrying to save game with uuid " + save.getGameID());
        save.move(new Point(1, 1), new Point(1, 2));
        sut.saveGame(save);
        System.out.println("\t\tDone!");

        System.out.println("\tTrying to load game with uuid " + save.getGameID());
        GameController load = sut.loadGameWithUUID(save.getGameID());
        assertNotNull(load);
        assertTrue(load.getGameID().equals(save.getGameID())
                && load.getField().getWhiteOrBlack() == save.getField().getWhiteOrBlack());

        sut.deleteGameWithUUID(load.getGameID());
        System.out.println("\t\tDone!");

    }

    @Test
    public void addDeleteTest(){
        GameController save = new GameController();
        System.out.println("\tTrying to save game with uuid " + save.getGameID());
        save.move(new Point(1, 1), new Point(1, 2));
        sut.saveGame(save);
        sut.deleteGameWithUUID(save.getGameID());
        assertFalse(sut.doeGameExistWithUUID(save.getGameID()));
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Successful!");
    }

}