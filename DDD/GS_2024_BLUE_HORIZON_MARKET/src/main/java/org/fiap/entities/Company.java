package org.fiap.entities;

import org.fiap.repositories.UserRepository;
import org.fiap.utils.ReceitaWsUtil;
import org.fiap.utils.ViaCepValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Company extends _BaseEntity{
    private static final Pattern PhonePattern = Pattern.compile("^\\s*(\\d{2})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$");

    private User admin;
    private String name;
    private String cnpj;
    private String phone;
    private String website;
    private List<Product>  products;
    private String verificationStatus; // 'verified', 'pending'

    public Company() {
    }

    public Company(String name, String cnpj, String phone, String website, String verificationStatus, int admin_id) {
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
        this.website = website;
        this.verificationStatus = verificationStatus;
        this.admin = new UserRepository().readById(admin_id);
    }

    public Company(int id, User admin, String name, String cnpj, String phone, String website, List<Product> products, String verificationStatus) {
        super(id);
        this.admin = admin;
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
        this.website = website;
        this.products = products;
        this.verificationStatus = verificationStatus;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.setUpdatedAt(LocalDateTime.now());

    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
        this.setUpdatedAt(LocalDateTime.now());

    }


    @Override
    public String toString() {
        return "Company{" +
                "admin=" + admin +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", verificationStatus='" + verificationStatus + '\'' +
                "} " + super.toString();
    }

    public Map<Boolean, String> validate() {
        Map<Boolean, String> validation = super.validate();
        if (this.admin == null) {
            validation.put(false, "Admin não pode ser vazio");
        }

        if (this.name == null || this.name.isEmpty()) {
            validation.put(false, "Nome não pode ser vazio");
        }

        if (this.cnpj == null || this.cnpj.isEmpty()) {
            validation.put(false, "CNPJ não pode ser vazio");
        } else {
            try {
                if (!ReceitaWsUtil.isEmpresaAtiva(this.cnpj)) {
                    validation.put(false, "CNPJ inválido");
                }
            } catch (Exception e) {
                validation.put(false, "Erro ao validar CNPJ: " + e.getMessage());
            }
        }

        if (this.phone == null || this.phone.isEmpty()) {
            validation.put(false, "Telefone não pode ser vazio");
        } else if (!PhonePattern.matcher(this.phone).matches()) {
            validation.put(false, "Telefone inválido. Formato esperado: XX-XXXXX-XXXX ou XX-XXXX-XXXX");
        }

        if (this.website == null || this.website.isEmpty()) {
            validation.put(false, "Website não pode ser vazio");
        }

        return validation;

    }
}
