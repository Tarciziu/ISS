package model;

import java.io.Serializable;

public class Order implements Entity, Serializable {
    private Long id;
    private Product product;
    private Integer quantity;
    private String buyer;

    public Order() {
    }

    public Order(Product product, Integer quantity, String buyer) {
        this.product = product;
        this.quantity = quantity;
        this.buyer = buyer;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
