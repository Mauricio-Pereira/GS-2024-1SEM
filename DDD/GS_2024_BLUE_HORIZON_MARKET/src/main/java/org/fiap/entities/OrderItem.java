package org.fiap.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderItem extends _BaseEntity {

    private Order order;
    private Product product;
    private int quantity;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(int id, Order order, Product product, int quantity, Double price) {
        super(id);
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser menor que zero");
        }
        this.quantity = quantity;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Preço não pode ser menor que zero");
        }
        this.price = price;
        this.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                "} " + super.toString();
    }

    public Map<Boolean, String> validate() {
        Map<Boolean, String> validation = new HashMap<>();
        if (this.order == null) {
            validation.put(false, "Pedido não pode ser nulo");
        }
        if (this.product == null) {
            validation.put(false, "Produto não pode ser nulo");
        }
        if (this.quantity <= 0) {
            validation.put(false, "Quantidade deve ser maior que zero");
        }
        if (this.price == null || this.price <= 0) {
            validation.put(false, "Preço deve ser maior que zero");
        }
        return validation;
    }
}
