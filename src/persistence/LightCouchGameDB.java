package persistence;

import controller.GameController;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.NoDocumentException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class LightCouchGameDB implements IGameDB {
    private CouchDbClient dbClient;

    public LightCouchGameDB() {
        CouchDbProperties properties = new CouchDbProperties()
                .setDbName("lightchess")
                .setCreateDbIfNotExist(true)
                .setProtocol("http")
                .setHost("lenny2.in.htwg-konstanz.de")
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
        if(game.get_rev()!=null) {
            dbClient.update(game);
        }else{
            dbClient.save(game);
        }
    }

    @Override
    public GameController loadGameWithUUID(String uuid) {
        try {
            return dbClient.find(GameController.class, uuid);
        }catch(NoDocumentException e){
            return null;
        }
    }

    @Override
    public void deleteGameWithUUID(String uuid) {
        dbClient.remove(loadGameWithUUID(uuid));
    }

}
