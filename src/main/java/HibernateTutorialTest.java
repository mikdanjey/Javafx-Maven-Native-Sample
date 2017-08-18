import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import sample.UsersEntity;

import java.util.ArrayList;
import java.util.List;

public class HibernateTutorialTest {
    private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;

    public static void main(String[] args) {

        Configuration config = new Configuration();
        config.configure();
        config.addAnnotatedClass(UsersEntity.class);
        config.addResource("UsersEntity.hbm.xml");
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        factory = config.buildSessionFactory(serviceRegistry);

        HibernateTutorialTest hbTest = new HibernateTutorialTest();
//        hbTest.insertUser("Mark","Johnson","mark.johnson@gmail.com");
//        hbTest.insertUser("Samuel","Johnson","sam.johnson@gmail.com");

        List<UsersEntity> users = hbTest.listUsers();
        for (UsersEntity u : users) {
            System.out.print(u.getId() + " ");
            System.out.print(u.getFirstname() + " ");
            System.out.print(u.getLastname() + " ");
            System.out.print(u.getEmail() + " ");
            System.out.println();
        }

    }

    private int insertUser(String fname, String lname, String email) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userIdSaved = null;
        try {
            tx = session.beginTransaction();
            UsersEntity u = new UsersEntity(fname, lname, email);
            userIdSaved = (Integer) session.save(u);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }

        return userIdSaved;

    }

    private List listUsers() {
        Session sesn = factory.openSession();
        Transaction tx;
        List users = new ArrayList();
        try {
            tx = sesn.beginTransaction();
            users = sesn.createQuery("From UsersEntity").list();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            sesn.close();
        }

        return users;
    }

}