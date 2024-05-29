package org.fiap.entities;

import java.time.LocalDateTime;

public class NGO extends _BaseEntity{
    private Integer contactId; // Reference to User
    private String name;
    private String mission;
    private String phone;
    private String website;
    private String contactEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NGO() {
    }

    public NGO(int id, Integer contactId, String name, String mission, String phone, String website, String contactEmail, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.contactId = contactId;
        this.name = name;
        this.mission = mission;
        this.phone = phone;
        this.website = website;
        this.contactEmail = contactEmail;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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
        return "NGO{" +
                "contactId=" + contactId +
                ", name='" + name + '\'' +
                ", mission='" + mission + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                "} " + super.toString();
    }
}
