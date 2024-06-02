package org.fiap.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Product extends _BaseEntity {
    private Company company;
    private String name;
    private String description;
    private Double price;
    private int stock;

    public Product() {
    }

    public Product(int id, Company company, String name, String description, Double price, int stock) {
        super(id);
        this.company = company;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(String name, String description, Double price, int stock, Company company) {
        this.company = company;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
        this.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Product{" +
                "company=" + company +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                "} " + super.toString();
    }

    public Map<Boolean, String> validate() {
        Map<Boolean, String> validation = new HashMap<>();

        if (this.company == null) {
            validation.put(false, "Empresa não pode ser nulo");
        }

        if (this.name == null || this.name.isEmpty()) {
            validation.put(false, "Nome não pode ser vazio");
        }

        if (this.description == null || this.description.isEmpty()) {
            validation.put(false, "Descrição não pode ser vazia");
        }

        if (this.price == null || this.price <= 0) {
            validation.put(false, "Preço não pode ser menor ou igual a zero");
        }

        if (this.stock < 0) {
            validation.put(false, "Estoque não pode ser menor que zero");
        }

        return validation;
    }
}
