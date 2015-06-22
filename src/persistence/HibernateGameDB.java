package persistence;

import controller.GameController;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.util.List;

public class HibernateGameDB implements IGameDB{
    static class HibernateUtil {
        private static final SessionFactory sessionFactory;
        static {
            final AnnotationConfiguration cfg = new AnnotationConfiguration();
            cfg.configure(new File("src/hibernate.cfg.xml"));
            sessionFactory = cfg.buildSessionFactory();
        }
        private HibernateUtil() {
        }
        public static SessionFactory getInstance() {
            return sessionFactory;
        }
    }

    @Override
    public boolean doeGameExistWithUUID(String uuid) {
        Session session = HibernateUtil.getInstance().openSession();
        Transaction t = session.beginTransaction();
        List games = session.createCriteria(GameController.class).add(Restrictions.idEq(uuid)).list();
        t.commit();
        return !games.isEmpty();
    }

    @Override
    public void saveGame(GameController game) {
        Session session = HibernateUtil.getInstance().openSession();
        Transaction t= session.beginTransaction();
        session.saveOrUpdate(game.getField());
        session.saveOrUpdate(game);
        t.commit();
    }

    @Override
    public GameController loadGameWithUUID(String uuid) {
        Session session = HibernateUtil.getInstance().openSession();
        Transaction t= session.beginTransaction();
        List games = session.createCriteria(GameController.class).add(Restrictions.eq("id", uuid)).list();
        t.commit();
        if(!games.isEmpty()&& games.get(0) instanceof GameController)
            return (GameController)games.get(0);
        return null;
    }

    @Override
    public void deleteGameWithUUID(String uuid) {
        Session session = HibernateUtil.getInstance().openSession();
        Transaction t= session.beginTransaction();
        session.delete(loadGameWithUUID(uuid));
        t.commit();
    }

    @Override
    public List<GameController> listGames(String uuid) {
        return null;
    }
}
