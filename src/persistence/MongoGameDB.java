package persistence;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ReflectionDBObject;
import com.mongodb.WriteConcern;
import controller.GameController;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MongoGameDB implements IGameDB {

    final Morphia morphia;
    final Datastore datastore;

    public MongoGameDB() {
        morphia = new Morphia();

        morphia.mapPackage("controller.GameController");
        morphia.mapPackage("model.Field");

        // create the Datastore connecting to the default port on the local host
        datastore = morphia.createDatastore(new MongoClient("localhost"), "morphia_example");
        datastore.ensureIndexes();
    }

    @Override
    public boolean doeGameExistWithUUID(String uuid) {
        return loadGameWithUUID(uuid) != null;
    }

    @Override
    public void saveGame(GameController game) {
        datastore.save(game, WriteConcern.JOURNALED);
    }

    @Override
    public GameController loadGameWithUUID(String uuid) {
        return datastore.createQuery(GameController.class).field("_id").equal(uuid).get();
    }

    @Override
    public void deleteGameWithUUID(String uuid) {
        datastore.delete(loadGameWithUUID(uuid));
    }

    @Override
    public List<GameController> listGames(String uuid) {
        /** This can be done more efficient, but it works for now */
        return new ArrayList<GameController>();
    }
}
