package plugin;

import controller.GameController;
import controller.IPlugin;
import model.Field;
import model.Figures;
import org.apache.log4j.Logger;

import java.awt.*;


public class FrenchRevolutionPlugin implements IPlugin{
    private static Logger logger = Logger.getLogger("FrenchRevolutionPlugin.class");

    @Override
    public void gameCreatedPlugin(GameController controller) {
        Field f = new Field();
        int[][] fArr = f.getField();
        for(int i=0;i<8;i++){
            fArr[i][6] = Figures.KingWhite.id();
            fArr[i][7] = Figures.QueenWhite.id();

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
}
