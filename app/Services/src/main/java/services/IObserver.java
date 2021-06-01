package services;

import model.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void notifyNewOrder(Order order) throws RemoteException;
}
