package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Order;
import model.Product;
import services.IService;

import javax.swing.*;

public class OrderWindowController {
    private IService service;
    private Product product;

    @FXML
    private Spinner<Integer> numberSpinner;
    @FXML
    private Label productName;
    @FXML
    private Label totalPrice;
    @FXML
    private TextField buyerTextField;

    @FXML
    private void initialize() {
        var factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, 1);
        factory.setWrapAround(true);

        numberSpinner.setValueFactory(factory);
        numberSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            totalPrice.setText(String.valueOf(newValue * product.getPrice()));
        });
    }

    public void setService(IService service){
        this.service = service;
    }

    public void setProduct(Product product){
        this.product = product;
        productName.setText(product.getName());
        totalPrice.setText(product.getPrice().toString());
    }

    public void handlePlaceOrder(ActionEvent actionEvent) {
        if(buyerTextField.getText().toString().length()>0)
            if(product.getQuantity() >= numberSpinner.getValue()){
                Order order = new Order(this.product, numberSpinner.getValue(), buyerTextField.getText());
                service.addOrder(order);
            }
            else
                JOptionPane.showMessageDialog(null, "Quantity of product unavailable",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
        Stage stage = (Stage) buyerTextField.getScene().getWindow();
        stage.close();
    }
}
