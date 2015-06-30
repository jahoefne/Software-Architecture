package persistence;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import controller.GameController;

import java.util.ArrayList;
import java.util.List;

/**
 * Db4o implementation of IGameDB
 * Ugly Sessions because DB4o fails without locking
 */
public class DB4OGameDB implements IGameDB {
    private static final ObjectContainer session = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "Games.db");


    @Override
    public boolean doeGameExistWithUUID(final String uuid) {
        synchronized(DB4OGameDB.class) {
            return session.query(new Predicate<GameController>() {
                public boolean match(GameController game) {
                    return game.get_id().equals(uuid);
                }
            }).size() != 0;
        }
    }

    @Override
    public void saveGame(GameController game) {
        synchronized(DB4OGameDB.class) {
            session.store(game);
            session.commit();
        }
    }

    @Override
    public GameController loadGameWithUUID(final String uuid) {
        synchronized(DB4OGameDB.class) {
            ObjectSet<GameController> set = session.query(new Predicate<GameController>() {
                public boolean match(GameController game) {
                    return game.get_id().equals(uuid);
                }
            });
            if (set.hasNext()){
                return set.next();
            }
            return null;
        }
    }

    @Override
    public void deleteGameWithUUID(String uuid) {
        synchronized(DB4OGameDB.class) {
            session.delete(loadGameWithUUID(uuid));
            session.commit();
        }
    }
}
