package services;

import model.Order;
import model.Product;
import model.User;

public interface IService {
    void login(User user, IObserver client) throws Exception;
    void logout(User user, IObserver client) throws Exception;
    Iterable<Product> findAllProducts();
    Iterable<Order> findAllOrders();
    void addOrder(Order order);
    void updateOrder(Order order);
}
