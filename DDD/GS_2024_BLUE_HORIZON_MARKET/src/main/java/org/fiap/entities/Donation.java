package org.fiap.entities;

import java.time.LocalDateTime;

public class Donation {
    private int orderId; // Reference to Order
    private Double amount;
    private int ngoId; // Reference to NGO
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Donation() {
    }

    public Donation(int orderId, Double amount, int ngoId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.amount = amount;
        this.ngoId = ngoId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getNgoId() {
        return ngoId;
    }

    public void setNgoId(int ngoId) {
        this.ngoId = ngoId;
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
        return "Donation{" +
                "orderId=" + orderId +
                ", amount=" + amount +
                ", ngoId=" + ngoId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
