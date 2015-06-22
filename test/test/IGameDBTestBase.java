package test;

import controller.GameController;
import org.junit.*;
import persistence.IGameDB;

import static org.junit.Assert.*;
import java.awt.*;


public abstract class IGameDBTestBase<T extends IGameDB> {
        private T sut;

        protected abstract T createSut();

        @Before
        public void setUp() {
            sut = createSut();
            System.out.println("Testing the "+sut.getClass().getName()+" GameDB implementation");
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