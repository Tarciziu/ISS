package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product;
import model.User;
import services.IObserver;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainWindowController extends UnicastRemoteObject implements Serializable, IObserver {
    private User loggedUser;
    private IService service;

    @FXML
    TableColumn<Product, String> nameColumn;
    @FXML
    TableColumn<Product, Double> priceColumn;
    @FXML
    TableColumn<Product, Integer> quantityColumn;
    @FXML
    TableView<Product> tableProducts;

    ObservableList<Product> modelProduct = FXCollections.observableArrayList();

    public MainWindowController() throws RemoteException {
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

        tableProducts.setItems(modelProduct);
    }

    public void setService(IService service) throws Exception {
        this.service = service;
        modelProduct.setAll(getProductList());
    }

    private List<Product> getProductList() throws Exception {
        return StreamSupport.stream(service.findAllProducts().spliterator(),false)
                .collect(Collectors.toList());
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
