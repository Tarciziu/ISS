package persistence.repository;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.IUserRepository;

import java.util.List;


public class UserRepositoryORM implements IUserRepository {
    private SessionFactory sessionFactory;

    public UserRepositoryORM() {
        this.sessionFactory = SessionCreator.getSessionFactory();
    }

    @Override
    public void save(User user) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(user);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User findOne(Long id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        User user = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("Searching for user " + username);
                user = session.createQuery("FROM User WHERE username = :username", User.class)
                        .setParameter("username", username)
                        .list().get(0);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                System.out.println(ex);
            }
        }
        System.out.println("User:");
        System.out.println(user);
        return user;
    }
}
