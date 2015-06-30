package controller;

import java.awt.*;

/**
 * Created by moe on 30/06/15.
 */
public interface IPlugin {
    void gameCreatedPlugin(GameController controller);
    void moveCalledPlugin(GameController controller, Point src, Point tgt);
    void gameOver(GameController controller);
}
