package org.fiap.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Donation extends _BaseEntity {

    private Order order;
    private Double amount;
    private Ngo ngo;

    public Donation() {
    }

    public Donation(int id, Order order, Double amount, Ngo ngo) {
        super(id);
        this.order = order;
        this.amount = amount;
        this.ngo = ngo;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Donation(Order order, Double amount, Ngo ngo) {
        this.order = order;
        this.amount = amount;
        this.ngo = ngo;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }



    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Valor da doação não pode ser menor que zero");
        }
        this.amount = amount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Ngo getNgo() {
        return ngo;
    }

    public void setNgo(Ngo ngo) {
        this.ngo = ngo;
        this.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Donation{" +
                "order=" + order +
                ", amount=" + amount +
                ", ngo=" + ngo +
                "} " + super.toString();
    }

    public Map<Boolean, String> validate() {
        Map<Boolean, String> validation = new HashMap<>();
        if (this.order == null) {
            validation.put(false, "Pedido não pode ser nulo");
        }
        if (this.amount == null || this.amount <= 0) {
            validation.put(false, "Valor da doação deve ser maior que zero");
        }
        if (this.ngo == null) {
            validation.put(false, "NGO não pode ser nulo");
        }
        return validation;
    }
}
