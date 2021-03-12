package ORM;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import tables.JobEntity;
import tables.RetireeEntity;


import java.util.List;

public class ORM {

  SessionFactory sessionFactory;

  public ORM() { sessionFactory = HibernateUtil.getSessionFactory();}

  public List<RetireeEntity> listRetirees() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("from RetireeEntity");
    List<RetireeEntity> result = query.list();
    tx.commit();
    return result;
  }

  public List<JobEntity> listJobs() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();
    Query query = session.createQuery("from JobEntity");
    List<JobEntity> result = query.list();
    tx.commit();
    return result;
  }


  /**select*
   from retiree
   where retirement_experience > 5;
   */
  public List<RetireeEntity> listRetExp() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();
    Query query = session.createQuery("from RetireeEntity where retirementExperience > 5");
    List<RetireeEntity> result = query.getResultList();
    tx.commit();
    return result;
  }


  /**
   * select retiree.id, surname, name, retirement_experience, job.job_position
   * from retiree
   *          left join job
   *                    on retiree.job_id = job.id;
   */

  public List<Object[]> leftJoinQuery() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();
    Query query = session.createQuery("""
                                            select r.id, r.surname, r.name, r.retirementExperience, j.jobPosition
                                                from RetireeEntity r
                                                    left join JobEntity j
                                                        on r.jobId =j.id
                                                        """);
    List<Object[]> result = query.list();
    tx.commit();
    return result;
  }

  /**
   * select count(*)
   * from retiree
   * where retirement_experience=2;
   */

  public long countRetirees() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("select count(*) from RetireeEntity where retirementExperience = 2");
    Long result = (Long)query.getSingleResult();

    tx.commit();
    return result;
  }

  /**
   * select sum (retirement)
   * from retiree
   * where retirement_experience > 10;
   */

  public double sumRetirees() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("select sum(retirement) from RetireeEntity where retirementExperience > 10");
    Double result = (Double) query.getSingleResult();
    tx.commit();
    return result;
  }

  /**
   * select max(retirement)
   * from retiree;
   */
  public Double maxRetirement() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("select max(retirement) from RetireeEntity");
    Double result = (Double)query.getSingleResult();
    tx.commit();
    return result;
  }

  /**
   * select min(retirement)
   * from retiree;
   *
   */

  public Double minRetirement() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("select min(retirement) from RetireeEntity");
    Double result = (Double)query.getSingleResult();
    tx.commit();
    return result;
  }


/**
 * select retiree.*, job.job_position
 * from retiree inner join job
 *                         on retiree.job_id = job.id
 * where job_position = 'teachers higher category';
 */

  public List<Object[]> innerJoinQuery() {
    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();
    Query query = session.createQuery("""
                                              select r, j.jobPosition
                                                  from RetireeEntity r
                                                      inner join JobEntity j
                                                          on r.jobId =j.id
                                                          where j.jobPosition = 'teachers higher category'
                                                          """);
    List<Object[]> result = query.list();
    tx.commit();
    return result;
  }





}

class HibernateUtil {
  private static SessionFactory sessionFactory;

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      // loads configuration and mappings
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().configure();
      sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    return sessionFactory;
  }
}