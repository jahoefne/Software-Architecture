package plugin;

import controller.GameController;
import controller.IPlugin;
import model.Field;
import model.Figures;

import java.awt.*;
import org.apache.log4j.Logger;

/**
 * Deactivates all pawns.
 */

public class NoPawns implements IPlugin {
    private static Logger logger = Logger.getLogger("NoPawns.class");

    @Override
    public void gameCreatedPlugin(GameController controller) {
        logger.debug("loaded");

        Field f = new Field();
        int[][] fArr = f.getField();
        for(int i=0;i<8;i++){
            fArr[i][1] = Figures.Empty.id();
            fArr[i][6] = Figures.Empty.id();
        }
        f.setField(fArr);
        controller.setField(f);
    }

    @Override
    public void moveCalledPlugin(GameController controller, Point src, Point tgt) {

    }

    @Override
    public void gameOver(GameController controller) {

    }

    @Override
    public boolean getGameOver() {
        return false;
    }
}
