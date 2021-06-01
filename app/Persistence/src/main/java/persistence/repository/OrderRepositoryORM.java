package persistence.repository;

import model.Order;
import model.OrderDTO;
import model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import persistence.IOrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryORM implements IOrderRepository {
    private SessionFactory sessionFactory;

    public OrderRepositoryORM() {
        this.sessionFactory = SessionCreator.getSessionFactory();
    }

    @Override
    public void save(Order order) {
        OrderDTO orderDTO = new OrderDTO(order.getProduct().getId(),order.getQuantity(), order.getBuyer());
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(orderDTO);
                tx.commit();
            } catch (RuntimeException ex) {
                System.out.println(ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public void update(Order order) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.update(order);
                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Iterable<Order> findAll() {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("Searching for orders");
                orderDTOs = session.createQuery("from OrderDTO", OrderDTO.class).list();
                System.out.println("Found "+orderDTOs.size() + " orders");
                for(OrderDTO orderDTO : orderDTOs){
                    Product product = session.createQuery("from Product where id=:product_id", Product.class)
                            .setParameter("product_id",orderDTO.getProduct_id()).list().get(0);
                    Order order = new Order(product, orderDTO.getQuantity(),orderDTO.getBuyer());
                    order.setId(orderDTO.getId());
                    orders.add(order);
                }
                tx.commit();
            } catch (RuntimeException ex) {
                System.out.println(ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return orders;
    }

    @Override
    public Order findOne(Long id) {
        return null;
    }
}
