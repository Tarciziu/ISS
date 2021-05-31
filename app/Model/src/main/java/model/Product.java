package model;

import java.io.Serializable;

public class Product implements Entity, Serializable {
    private Long id;
    private String name; // product name
    private Double price;
    private Integer quantity; // on stock

    public Product() {
    }

    public Product(String name, Double price, Integer quantity) {
        this.price = price;
        this.quantity = quantity;
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
