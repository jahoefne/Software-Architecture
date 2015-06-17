package persistence;

import controller.GameController;

import java.util.List;

/**
 * Created by moe on 17/06/15.
 */
public class HibernateGameDB implements IGameDB{
    @Override
    public boolean doeGameExistWithUUID(String uuid) {
        return false;
    }

    @Override
    public void saveGame(GameController game) {

    }

    @Override
    public GameController loadGameWithUUID(String uuid) {
        return null;
    }

    @Override
    public void deleteGameWithUUID(String uuid) {

    }

    @Override
    public List<GameController> listGames(String uuid) {
        return null;
    }
}
