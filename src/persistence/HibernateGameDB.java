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
            final AnnotationConfiguration cfg = new AnnotationConfiguration()
                    .setProperty("hibernate.connection.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/chess?createDatabaseIfNotExist=true")
                    .setProperty("hibernate.connection.username", "root")
                    .setProperty("hibernate.show_sql","false")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .setProperty("hibernate.hbm2ddl.auto","update");
            cfg.addAnnotatedClass(model.Field.class);
            cfg.addAnnotatedClass(controller.GameController.class);
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
