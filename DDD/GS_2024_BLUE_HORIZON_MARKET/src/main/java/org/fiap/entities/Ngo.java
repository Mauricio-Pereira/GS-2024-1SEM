package org.fiap.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Ngo extends _BaseEntity {
    private static final Pattern EmailPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern PhonePattern = Pattern.compile("^\\s*(\\d{2})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$");

    private User contactUser;
    private String name;
    private String mission;
    private String phone;
    private String website;
    private String contactEmail;
    private Double totalDonations;

    public Ngo() {
    }

    public Ngo(int id, User contactUser, String name, String mission, String phone, String website, String contactEmail) {
        super(id);
        this.contactUser = contactUser;
        this.name = name;
        this.mission = mission;
        this.phone = phone;
        this.website = website;
        this.contactEmail = contactEmail;
    }

    public Ngo(User contactUser, String name, String mission, String website) {
        this.contactUser = contactUser;
        this.name = name;
        this.mission = mission;
        this.phone = contactUser.getPhone();
        this.website = website;
        this.contactEmail = contactUser.getEmail();
    }

    public User getContactUser() {
        return contactUser;
    }

    public void setContactUser(User contactUser) {
        this.contactUser = contactUser;
        this.contactEmail = contactUser.getEmail();
        this.phone = contactUser.getPhone();
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getTotalDonations() {
        return totalDonations;
    }

    public void setTotalDonations(Double totalDonations) {
        this.totalDonations = totalDonations;
        this.setUpdatedAt(LocalDateTime.now());
    }
    @Override
    public String toString() {
        return "Ngo{" +
                "contactUser=" + contactUser +
                ", name='" + name + '\'' +
                ", mission='" + mission + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                "} " + super.toString();
    }

    public Map<Boolean, String> validate() {
        Map<Boolean, String> validation = new HashMap<>();
        if (this.name == null || this.name.isEmpty()) {
            validation.put(false, "Nome não pode ser vazio");
        }
        if (this.mission == null || this.mission.isEmpty()) {
            validation.put(false, "Missão não pode ser vazia");
        }
        if (this.phone == null || this.phone.isEmpty()) {
            validation.put(false, "Telefone não pode ser vazio");
        } else if (!PhonePattern.matcher(this.phone).matches()) {
            validation.put(false, "Telefone inválido");
        }
        if (this.website == null || this.website.isEmpty()) {
            validation.put(false, "Website não pode ser vazio");
        }
        if (this.contactEmail == null || this.contactEmail.isEmpty()) {
            validation.put(false, "Email de contato não pode ser vazio");
        } else if (!EmailPattern.matcher(this.contactEmail).matches()) {
            validation.put(false, "Email de contato inválido");
        }
        return validation;
    }
}
