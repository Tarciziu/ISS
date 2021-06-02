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

public class ModifyOrderWindowController {
    private IService service;
    private Order order;
    Integer initQuantity;

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

    }

    public void setService(IService service){
        this.service = service;
    }

    public void setOrder(Order order){
        this.order = order;
        buyerTextField.setText(order.getBuyer());
        productName.setText(order.getProduct().getName());
        totalPrice.setText(String.valueOf(order.getProduct().getPrice()*order.getQuantity()));
        initQuantity = order.getQuantity();
        var factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15, order.getQuantity());
        factory.setWrapAround(true);

        numberSpinner.setValueFactory(factory);
        numberSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            totalPrice.setText(String.valueOf(newValue * order.getProduct().getPrice()));
        });
    }

    public void handleModifyOrder(ActionEvent actionEvent) {
        if(buyerTextField.getText().toString().length()>0)
            if(order.getProduct().getQuantity()+order.getQuantity() >= numberSpinner.getValue())
            {
                order.setQuantity(numberSpinner.getValue());
                order.getProduct().setQuantity(order.getProduct().getQuantity()+(initQuantity-order.getQuantity()));
                order.setBuyer(buyerTextField.getText());
                service.updateOrder(order);
            }
            else
                JOptionPane.showMessageDialog(null, "Quantity of product unavailable",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
        Stage stage = (Stage) buyerTextField.getScene().getWindow();
        stage.close();
    }
}
