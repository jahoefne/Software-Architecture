package plugin;

import controller.GameController;
import controller.IPlugin;
import model.Field;
import model.Figures;
import org.apache.log4j.Logger;

import java.awt.*;


public class FrenchRevolutionPlugin implements IPlugin{
    private static Logger logger = Logger.getLogger("FrenchRevolutionPlugin.class");
    private static final int COLUMNS = 8;

    @Override
    public void gameCreatedPlugin(GameController controller) {

        Field f = new Field();
        int[][] fArr = f.getField();
        for(int i=0; i<COLUMNS - 2; i++){
            fArr[i][COLUMNS - 2] = Figures.KingWhite.id();
            fArr[i][COLUMNS - 1] = Figures.QueenWhite.id();

            fArr[i][0] = Figures.PawnBlack.id();
            fArr[i][1] = Figures.PawnBlack.id();
        }
        f.setField(fArr);
        controller.setField(f);
    }

    @Override
    public void moveCalledPlugin(GameController controller, Point src, Point tgt) {
        logger.debug("move called "+ src.toString() + " to "+ tgt.toString());
    }

    @Override
    public void gameOver(GameController controller) {
        logger.debug("GameOver");
    }

    @Override
    public boolean getGameOver() {
        return false;
    }
}
