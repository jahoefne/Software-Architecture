package persistence;

import controller.GameController;

import java.util.List;

public interface IGameDB {
    /** returns true if a game with uuid exists */
    boolean doeGameExistWithUUID(String uuid);

    /** Saves an GameController in the Database */
    void saveGame(GameController game);

    /** load the game with the given uuid from the database **/
    GameController loadGameWithUUID(String uuid);

    /** Delete the game with uuid from the database */
    void deleteGameWithUUID(String uuid);

}
