package test;

import controller.GameController;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import persistence.*;

import static org.junit.Assert.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@RunWith(Parameterized.class)
public class IGameDBTest {
    private IGameDB sut;

    public IGameDBTest(IGameDB db) {
        sut = db;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(
                new Object[]{new CouchGameDB()},
                new Object[]{new LightCouchGameDB()},
                new Object[]{new DB4OGameDB()}
                // Hibernate test is failing on jenkins because there doesn't seem to be a mysql server running
        );
    }

    @Before
    public void setUp() {
        System.out.println("Testing the " + sut.getClass().getName() + " GameDB implementation");
    }

    @Test
    public void addReceiveController() {
        GameController save = new GameController();
        System.out.println("\tTrying to save game with uuid " + save.get_id());
        save.move(new Point(1, 1), new Point(1, 2));
        sut.saveGame(save);
        System.out.println("\t\tDone!");

        System.out.println("\tTrying to load game with uuid " + save.get_id());
        GameController load = sut.loadGameWithUUID(save.get_id());
        System.out.println("\t\tDone!");
        assertTrue(load.get_id().equals(save.get_id())
                && load.getField().getWhiteOrBlack() == save.getField().getWhiteOrBlack());

    }

    @Test
    public void addDeleteTest() {
        GameController save = new GameController();
        System.out.println("\tTrying to save game with uuid " + save.get_id());
        save.move(new Point(1, 1), new Point(1, 2));
        sut.saveGame(save);
        sut.deleteGameWithUUID(save.get_id());
        assertFalse(sut.doeGameExistWithUUID(save.get_id()));
    }

    @Test
    public void loadNonExistingGame(){
        System.out.println("Loading Non existing Game");
        assertNull(sut.loadGameWithUUID(UUID.randomUUID().toString()));
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("Successful!");
    }

}