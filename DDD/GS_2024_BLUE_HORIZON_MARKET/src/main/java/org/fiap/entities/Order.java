package org.fiap.entities;

import java.time.LocalDateTime;

public class Order extends _BaseEntity{

    private String orderNumber;
    private int buyerId; // Reference to User
    private Double totalAmount;
    private Double donationAmount;
    private Double maintenanceAmount;
    private String orderStatus; // 'paid', 'pending', 'canceled'
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order() {
    }

    public Order(int id, String orderNumber, int buyerId, Double totalAmount, Double donationAmount, Double maintenanceAmount, String orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.orderNumber = orderNumber;
        this.buyerId = buyerId;
        this.totalAmount = totalAmount;
        this.donationAmount = donationAmount;
        this.maintenanceAmount = maintenanceAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Double getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(Double maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber='" + orderNumber + '\'' +
                ", buyerId=" + buyerId +
                ", totalAmount=" + totalAmount +
                ", donationAmount=" + donationAmount +
                ", maintenanceAmount=" + maintenanceAmount +
                ", orderStatus='" + orderStatus + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "} " + super.toString();
    }
}
