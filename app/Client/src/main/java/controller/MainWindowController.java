package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Order;
import model.Product;
import model.User;
import services.IObserver;
import services.IService;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainWindowController extends UnicastRemoteObject implements Serializable, IObserver {
    private User loggedUser;
    private IService service;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableView<Product> tableProducts;

    ObservableList<Product> modelProduct = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Order,Integer> orderIdColumn;
    @FXML
    private TableColumn<Order,String> buyerColumn;
    @FXML
    private TableView<Order> tableOrders;

    ObservableList<Order> modelOrder = FXCollections.observableArrayList();

    public MainWindowController() throws RemoteException {
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));

        tableProducts.setItems(modelProduct);

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            modelProduct.clear();
            try {
                modelProduct.setAll(getProductList().stream()
                        .filter(x->x.getName().toLowerCase().
                                startsWith(newValue.toLowerCase())).collect(Collectors.toList()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        orderIdColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        buyerColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("buyer"));

        tableOrders.setItems(modelOrder);
    }

    public void setService(IService service) throws Exception {
        this.service = service;
        modelProduct.setAll(getProductList());
        modelOrder.setAll(getOrderList());
    }

    private List<Product> getProductList() throws Exception {
        return StreamSupport.stream(service.findAllProducts().spliterator(),false)
                .collect(Collectors.toList());
    }

    private List<Order> getOrderList() throws Exception {
        return StreamSupport.stream(service.findAllOrders().spliterator(),false)
                .collect(Collectors.toList());
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void handleOrder(ActionEvent actionEvent) throws IOException {
        Product product = tableProducts.getSelectionModel().getSelectedItem();
        if(product != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/orderWindow.fxml"));
            Scene orderScene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(orderScene);
            stage.show();
            OrderWindowController orderController = loader.getController();
            orderController.setService(service);
            orderController.setProduct(product);
        }
    }

    @Override
    public void notifyNewOrder(Order order) throws RemoteException {
        for(int i=0;i<modelProduct.size();i++){
            if(modelProduct.get(i).getId().equals(order.getProduct().getId()))
                modelProduct.get(i).setQuantity(order.getProduct().getQuantity());
        }
        tableProducts.refresh();
        modelOrder.add(order);
        tableOrders.refresh();
    }
}
