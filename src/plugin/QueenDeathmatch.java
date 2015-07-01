package plugin;

import controller.GameController;
import controller.IPlugin;
import model.Field;
import model.Figures;
import org.apache.log4j.Logger;

import java.awt.*;

/**
 * "Team Deathmatch" with Queen's only.
 */
public class QueenDeathmatch implements IPlugin {
    private static Logger logger = Logger.getLogger("QueenDeathmatch.class");
    private static final int COLUMNS = 8;

    @Override
    public void gameCreatedPlugin(GameController controller) {
        logger.debug("loaded");

        Field f = new Field();
        int[][] fArr = f.getField();
        for(int i=0; i<COLUMNS; i++){
            if (i > 1 && i < COLUMNS - 2) {
                fArr[i][2] = Figures.QueenBlack.id();
                fArr[i][5] = Figures.QueenWhite.id();
            }


            fArr[i][1] = Figures.QueenBlack.id();
            fArr[i][COLUMNS - 2] = Figures.QueenWhite.id();


            if (i != 4) {
                fArr[i][0] = Figures.QueenBlack.id();
                fArr[i][COLUMNS - 1] = Figures.QueenWhite.id();
            }
        }

        fArr[0][1] = Figures.Empty.id();
        fArr[COLUMNS - 1][1] = Figures.Empty.id();

        fArr[0][COLUMNS - 2] = Figures.Empty.id();
        fArr[COLUMNS - 1][COLUMNS - 2] = Figures.Empty.id();

        f.setField(fArr);
        controller.setField(f);
    }

    @Override
    public void moveCalledPlugin(GameController controller, Point src, Point tgt) {
        logger.debug("move called "+ src.toString() + " to "+ tgt.toString());
    }

    @Override
    public void gameOver(GameController controller) {
        logger.debug("gameOver called");
    }

    @Override
    public boolean getGameOver() {
        return false;
    }
}
