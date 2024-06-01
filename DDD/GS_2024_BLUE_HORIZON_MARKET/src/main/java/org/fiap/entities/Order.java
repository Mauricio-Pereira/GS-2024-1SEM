package org.fiap.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order extends _BaseEntity {

    private String orderNumber;
    private User buyer;
    private Double totalAmount;
    private Double donationAmount;
    private Double maintenanceAmount;
    private String orderStatus; // 'paid', 'pending', 'canceled'
    private List<OrderItem> items;

    public Order() {
        this.orderNumber = generateOrderNumber();
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.donationAmount = 0.0;
        this.maintenanceAmount = 0.0;
        this.orderStatus = "pending"; // Default status
    }

    public Order(int id, String orderNumber, User buyer, Double totalAmount, Double donationAmount, Double maintenanceAmount, String orderStatus, List<OrderItem> items) {
        super(id);
        this.orderNumber = orderNumber != null ? orderNumber : generateOrderNumber();
        this.buyer = buyer;
        this.totalAmount = totalAmount;
        this.donationAmount = donationAmount;
        this.maintenanceAmount = maintenanceAmount;
        this.orderStatus = orderStatus != null ? orderStatus : "pending";
        this.items = items != null ? items : new ArrayList<>();
    }

    private String generateOrderNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return timestamp + "-" + uuid;
    }

    // Getters and setters

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(Double maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
        this.recalculateAmounts();
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        this.recalculateAmounts();
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
        this.recalculateAmounts();
        this.setUpdatedAt(LocalDateTime.now());
    }

    private void recalculateAmounts() {
        this.totalAmount = items.stream().mapToDouble(OrderItem::getPrice).sum();
        double taxa = totalAmount * 0.12; // 12% do totalAmount
        this.donationAmount = taxa * 0.6667; // 8% do totalAmount (8/12 = 0.6667)
        this.maintenanceAmount = taxa * 0.3333; // 4% do totalAmount (4/12 = 0.3333)
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber='" + orderNumber + '\'' +
                ", buyer=" + buyer +
                ", totalAmount=" + totalAmount +
                ", donationAmount=" + donationAmount +
                ", maintenanceAmount=" + maintenanceAmount +
                ", orderStatus='" + orderStatus + '\'' +
                ", items=" + items +
                "} " + super.toString();
    }

    public Map<Boolean, String> validate() {
        Map<Boolean, String> validation = new HashMap<>();
        if (totalAmount < 0) {
            validation.put(false, "Valor total inválido");
        }

        if (donationAmount < 0) {
            validation.put(false, "Valor de doação inválido");
        }

        if (maintenanceAmount < 0) {
            validation.put(false, "Valor de manutenção inválido");
        }

        if (orderStatus == null || orderStatus.isEmpty()) {
            validation.put(false, "Status do pedido não pode ser vazio");
        }

        if (items == null || items.isEmpty()) {
            validation.put(false, "Pedido sem itens");
        }
        return validation;
    }
}
