package persistence;

import controller.GameController;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class CouchGameDB implements IGameDB {

    HttpClient httpClient;
    CouchDbInstance dbInstance;
    CouchDbConnector db;

    public CouchGameDB() {
        try {
            httpClient = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984/").build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        dbInstance = new StdCouchDbInstance(httpClient);
        db = new StdCouchDbConnector("SASS2015_Chess_11_Games", dbInstance);
        db.createDatabaseIfNotExists();
    }

    @Override
    public boolean doeGameExistWithUUID(String uuid) {
        return db.find(GameController.class, uuid) != null;
    }

    @Override
    public void saveGame(GameController game) {
        db.update(game);
    }

    @Override
    public GameController loadGameWithUUID(String uuid) {
        return db.find(GameController.class, uuid);
    }

    @Override
    public void deleteGameWithUUID(String uuid) {
        db.delete(loadGameWithUUID(uuid));
    }

    @Override
    public List<GameController> listGames(String uuid) {
        /** This can be done more efficient, but it works for now */
        List<String> ids = db.getAllDocIds();
        List<GameController> games = new ArrayList<GameController>();

        for(String id : ids){
            GameController game = loadGameWithUUID(id);
            if(game.getCreatedBy()==uuid){
                games.add(game);
            }
        }
        return games;
    }
}
