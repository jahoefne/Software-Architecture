package persistence;

import controller.GameController;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class LightCouchGameDB implements IGameDB {
    CouchDbClient dbClient;

    public LightCouchGameDB() {
        CouchDbProperties properties = new CouchDbProperties()
                .setDbName("lightchess")
                .setCreateDbIfNotExist(true)
                .setProtocol("http")
                .setHost("127.0.0.1")
                .setPort(5984)
                .setMaxConnections(1000)
                .setConnectionTimeout(0);

        dbClient = new CouchDbClient(properties);
    }

    @Override
    public boolean doeGameExistWithUUID(String uuid) {
        return dbClient.contains(uuid);
    }

    @Override
    public void saveGame(GameController game) {
        if(game.get_rev()!=null)
            dbClient.update(game);
        else
            dbClient.save(game);
    }

    @Override
    public GameController loadGameWithUUID(String uuid) {
        return dbClient.find(GameController.class, uuid);
    }

    @Override
    public void deleteGameWithUUID(String uuid) {
        dbClient.remove(loadGameWithUUID(uuid));
    }

    @Override
    public List<GameController> listGames(String uuid) {
        /** This can be done more efficient, but it works for now */
       /* List<GameController> games = new ArrayList<GameController>();

        for(String id : ids){
            GameController game = loadGameWithUUID(id);
            if(game.getCreatedBy()==uuid)
                games.add(game);
        }*/
        return new ArrayList<GameController>();
    }
}
