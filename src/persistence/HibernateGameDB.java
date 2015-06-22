package persistence;

import controller.GameController;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.util.List;

/**
 * Created by moe on 17/06/15.
 */
public class HibernateGameDB implements IGameDB{
    static class HibernateUtil {
        private static final SessionFactory sessionFactory;
        static {
            final AnnotationConfiguration cfg = new
                    AnnotationConfiguration();
            cfg.configure(new File("hibernate.cfg.xml"));
            sessionFactory = cfg.buildSessionFactory();
        }
        private HibernateUtil() {
        }
        public static SessionFactory getInstance() {
            return sessionFactory;
        }
    }

    private Session sess;

    public HibernateGameDB(){
        try {
            sess = HibernateUtil.getInstance().getCurrentSession();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public boolean doeGameExistWithUUID(String uuid) {
        List games = sess.createCriteria(GameController.class).add(Restrictions.idEq(uuid)).list();
        return !games.isEmpty();
    }

    @Override
    public void saveGame(GameController game) {
        sess.saveOrUpdate(game);
    }

    @Override
    public GameController loadGameWithUUID(String uuid) {
        List<GameController> games = sess.createCriteria(GameController.class).add(Restrictions.idEq(uuid)).list();
        if(!games.isEmpty())
            return games.get(0);
        return null;
    }

    @Override
    public void deleteGameWithUUID(String uuid) {
        sess.delete(loadGameWithUUID(uuid));
    }

    @Override
    public List<GameController> listGames(String uuid) {
        return null;
    }
}
