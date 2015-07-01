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

    @Override
    public void gameCreatedPlugin(GameController controller) {
        logger.debug("loaded");

        Field f = new Field();
        int[][] fArr = f.getField();
        for(int i=0;i<8;i++){
            if (i > 1 && i < 6) {
                fArr[i][2] = Figures.QueenBlack.id();
                fArr[i][5] = Figures.QueenWhite.id();
            }


            fArr[i][1] = Figures.QueenBlack.id();
            fArr[i][6] = Figures.QueenWhite.id();


            if (i != 4) {
                fArr[i][0] = Figures.QueenBlack.id();
                fArr[i][7] = Figures.QueenWhite.id();
            }
        }

        fArr[0][1] = Figures.Empty.id();
        fArr[7][1] = Figures.Empty.id();

        fArr[0][6] = Figures.Empty.id();
        fArr[7][6] = Figures.Empty.id();

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
