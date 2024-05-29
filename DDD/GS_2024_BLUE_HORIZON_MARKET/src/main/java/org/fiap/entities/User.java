package org.fiap.entities;

import java.time.LocalDateTime;

public class User extends _BaseEntity{
    private String email;
    private String password;
    private String userType; // 'buyer', 'admin_company', 'admin_ong'
    private String cep;
    private String addressNumber;
    private String addressComplement;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(int id, String email, String password, String userType, String cep, String addressNumber, String addressComplement, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.cep = cep;
        this.addressNumber = addressNumber;
        this.addressComplement = addressComplement;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
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
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", cep='" + cep + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", addressComplement='" + addressComplement + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "} " + super.toString();
    }
}
