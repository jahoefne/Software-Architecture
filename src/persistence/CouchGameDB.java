package persistence;

import controller.GameController;
import org.apache.log4j.Logger;
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

    private HttpClient httpClient;
    private CouchDbInstance dbInstance;
    private CouchDbConnector db;
    private static Logger logger = Logger.getLogger("CouchGameDB.class");

    public CouchGameDB() {
        try {
            httpClient = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984/").build();
        } catch (MalformedURLException e) {
            logger.error(e.toString());
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

}
