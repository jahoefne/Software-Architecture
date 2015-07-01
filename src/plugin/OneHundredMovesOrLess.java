package plugin;

import controller.GameController;
import controller.IPlugin;

import java.awt.*;
import org.apache.log4j.Logger;

/**
 * Limits the number of moves to 100. If no one wins before, the player
 * which did the 100th move wins automatically.
 */

public class OneHundredMovesOrLess implements IPlugin {
    private final static int MAX_MOVES = 100;
    private static Logger logger = Logger.getLogger("OneHundredMovesOrLess.class");
    private int movesDone = 0;
    // set this only true, if the plugin wants to end the game
    private boolean gameOver = false;
    private int gControllerHash = 0;

    @Override
    public void gameCreatedPlugin(GameController controller) {
        logger.debug("loaded");
    }

    @Override
    public void moveCalledPlugin(GameController controller, Point src, Point tgt) {
        if (gControllerHash != controller.toString().hashCode()) {
            gControllerHash = controller.toString().hashCode();
            movesDone++;
            System.out.println("moves done: " + movesDone);
        }

        if (movesDone == MAX_MOVES) {
            gameOver = true;
        }
    }

    @Override
    public void gameOver(GameController controller) {
        movesDone = 0;
    }

    @Override
    public boolean getGameOver() {
        return gameOver;
    }
}
