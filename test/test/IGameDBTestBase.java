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
        public void addDeleteTest(){
            GameController save = new GameController();
            System.out.println("\tTrying to save game with uuid " + save.get_id());
            save.move(new Point(1, 1), new Point(1, 2));
            sut.saveGame(save);
            sut.deleteGameWithUUID(save.get_id());
            assertFalse(sut.doeGameExistWithUUID(save.get_id()));
        }

        @AfterClass
        public static void afterClass() {
            System.out.println("Successful!");
        }

}