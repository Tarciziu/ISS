package persistence.repository;

import model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.IProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryORM implements IProductRepository {
    private SessionFactory sessionFactory;

    public ProductRepositoryORM() {
        this.sessionFactory = SessionCreator.getSessionFactory();
    }

    @Override
    public void save(Product product) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(product);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Product product) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.err.println("Stergem mesajul " + product.getId());
                session.delete(product);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(Product product) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.update(product);
                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Iterable<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("Searching for products");
                products = session.createQuery("from Product", Product.class).list();
                System.out.println("Found "+products.size() + " products");
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return products;
    }

    @Override
    public Product findOne(Long id) {
        Product product = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                product = session.createQuery("from Product where id = :id", Product.class)
                        .setParameter("id", id).list().get(0);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return product;
    }
}
