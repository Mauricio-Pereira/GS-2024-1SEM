package org.fiap.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class User extends _BaseEntity {
    private static final Pattern PhonePattern = Pattern.compile("^\\s*(\\d{2})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$");
    private static final Pattern EmailPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern PasswordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String userType; // 'buyer', 'admin_company', 'admin_ngo'
    private Address address;
    private String phone;
    private LocalDate birthDate;

    public User() {
    }

    public User(int id, String name, String lastName, String email, String password, String userType, Address address, String phone, LocalDate birthDate) {
        super(id);
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.address = address;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public User(int id, String name, String lastName, String email, String password, String userType, String phone, LocalDate birthDate) {
        super(id);
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public User(String name, String lastName, String email, String password, String userType, String phone, LocalDate birthDate) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        this.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                "} " + super.toString();
    }

    public static boolean isDateValid(String strDate) {
        String dateFormat = "dd/MM/uuuu";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(strDate, dateTimeFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isMaiorDeIdade() {
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthDate, now);
        return period.getYears() >= 18;
    }

    public Map<Boolean, String> validate() {
        Map<Boolean, String> validation = new HashMap<>();
        if (this.name == null || this.name.isEmpty()) {
            validation.put(false, "Nome não pode ser vazio");
        }

        if (this.lastName == null || this.lastName.isEmpty()) {
            validation.put(false, "Sobrenome não pode ser vazio");
        }



        // Validate email
        if (this.email == null || this.email.isEmpty()) {
            validation.put(false, "Email não pode ser vazio");
        } else if (!EmailPattern.matcher(this.email).matches()) {
            validation.put(false, "Email inválido");
        }

        // Validate password
        if (this.password == null || this.password.isEmpty()) {
            validation.put(false, "Senha não pode ser vazia");
        } else if (!PasswordPattern.matcher(this.password).matches()) {
            validation.put(false, "Senha inválida");
        }

        // Validate userType
        if (this.userType == null || this.userType.isEmpty()) {
            validation.put(false, "Tipo de usuário não pode ser vazio");
        }

        // Validate phone
        if (this.phone == null || this.phone.isEmpty()) {
            validation.put(false, "Telefone não pode ser vazio");
        } else if (!PhonePattern.matcher(this.phone).matches()) {
            validation.put(false, "Telefone inválido. Formato esperado: XX-XXXXX-XXXX ou XX-XXXX-XXXX");
        }

        if (this.birthDate == null) {
            validation.put(false, "Data de nascimento inválida");
        } else if (!isDateValid(this.birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
            validation.put(false, "Data de nascimento inválida");
        } else if (!isMaiorDeIdade()) {
            validation.put(false, "Cliente menor de idade");
        }

        return validation;
    }
}
