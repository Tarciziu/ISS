package model;

import java.io.Serializable;

public class OrderDTO implements Entity, Serializable {
    private Long id;
    private Long product_id;
    private Integer quantity;
    private String buyer;

    public OrderDTO() {
    }

    public OrderDTO(Long product, Integer quantity, String buyer) {
        this.product_id = product;
        this.quantity = quantity;
        this.buyer = buyer;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
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
