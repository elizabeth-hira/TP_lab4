package queryManagers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class HibernateQuery {


    SessionFactory sessionFactory;

    public HibernateQuery() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List getList(String hql){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(hql);
        List rows = query.list();
        tx.commit();
        session.close();
        return rows;
    }

    public Object getSingleResult(String hql){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery(hql);
        Object result = query.getSingleResult();
        tx.commit();
        session.close();
        return result;
    }

    public void releaseResources() {
        sessionFactory.close();
    }
}

class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure();
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }

        return sessionFactory;
    }
}
