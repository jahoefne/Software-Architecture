package test;

import controller.GameController;
import org.junit.*;
import persistence.CouchGameDB;
import static org.junit.Assert.*;
import java.awt.*;


public class CouchDBTest{
        private CouchGameDB sut;

        @BeforeClass
        public static void beforeClass() {
            System.out.println("Testing the CouchDB..");
        }

        @Before
        public void setUp() throws Exception {
            sut = new CouchGameDB();
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
            System.out.println("\t\tDone!");

            assertTrue(load.getGameID().equals(save.getGameID())
                    && load.getField().getWhiteOrBlack() == save.getField().getWhiteOrBlack());

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