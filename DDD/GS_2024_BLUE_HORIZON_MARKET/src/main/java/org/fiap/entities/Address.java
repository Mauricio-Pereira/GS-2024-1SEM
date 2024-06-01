package org.fiap.entities;

import org.fiap.utils.ViaCepValidator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Address extends _BaseEntity {
    private static final Pattern CepPattern = Pattern.compile("^\\d{8}$");
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private User user;
    public Address() {
    }

    public Address(int id, String street, String number, String complement, String neighborhood, String city, String state, String country, String zipCode) {
        super(id);
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public Address(String zipCode, String country, String state, String city, String neighborhood, String street, String number, String complement, User user) {
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.user = user;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
        this.setUpdatedAt(LocalDateTime.now());
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", complement='" + complement + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                "} " + super.toString();
    }

    public Map<Boolean, String> validate() {
        Map<Boolean, String> validation = new HashMap<>();
        if (street == null || street.isEmpty()) {
            validation.put(false, "Rua não pode ser vazia");
        }
        if (number == null || number.isEmpty()) {
            validation.put(false, "Número não pode ser vazio");
        }
        if (neighborhood == null || neighborhood.isEmpty()) {
            validation.put(false, "Bairro não pode ser vazio");
        }
        if (city == null || city.isEmpty()) {
            validation.put(false, "Cidade não pode ser vazia");
        }
        if (state == null || state.isEmpty()) {
            validation.put(false, "Estado não pode ser vazio");
        }
        if (country == null || country.isEmpty()) {
            validation.put(false, "País não pode ser vazio");
        }
        if (zipCode == null || zipCode.isEmpty() || !CepPattern.matcher(zipCode).matches()) {
            validation.put(false, "CEP inválido");
        } else if (!ViaCepValidator.isCepValid(zipCode)) {
            validation.put(false, "CEP não encontrado");
        }

        return validation;
    }
}
