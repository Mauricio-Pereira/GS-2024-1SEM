package org.fiap.repositories;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.Company;
import org.fiap.utils.Log4jLogger;
import org.fiap.infrastructure.QueryProcessor;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class CompanyRepository extends _BaseRepositoryImpl<Company> {
    Log4jLogger<Company> logger = new Log4jLogger<>(Company.class);
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public static final String TB_NAME = "GS_COMPANIES";

    public static final HashMap<String, String> TB_COLUMNS = new HashMap<>() {{
        put("COMPANY_ID", "COMPANY_ID");
        put("NAME", "NAME");
        put("CNPJ", "CNPJ");
        put("PHONE", "PHONE");
        put("WEBSITE", "WEBSITE");
        put("VERIFICATION_STATUS", "VERIFICATION_STATUS");
        put("CREATED_AT", "CREATED_AT");
        put("UPDATED_AT", "UPDATED_AT");
        put("ADMIN_ID", "ADMIN_ID");
    }};

    public CompanyRepository() {
        super(Company.class);
        initialize();
    }

    public void initialize() {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = '" + TB_NAME.toUpperCase() + "'";
        String createTableSQL = "CREATE TABLE " + TB_NAME + " ("
                + "COMPANY_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                + "NAME VARCHAR2(255) NOT NULL, "
                + "CNPJ VARCHAR2(20) NOT NULL, "
                + "PHONE VARCHAR2(20), "
                + "WEBSITE VARCHAR2(255), "
                + "VERIFICATION_STATUS VARCHAR2(50), "
                + "CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "ADMIN_ID NUMBER NOT NULL, "
                + "CONSTRAINT fk_admin FOREIGN KEY (ADMIN_ID) REFERENCES GS_USERS(USER_ID))";

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Verifica se a tabela já existe
            ResultSet rs = statement.executeQuery(checkTableExistsSQL);
            if (rs.next() && rs.getInt(1) == 0) {
                // Cria a tabela se não existir
                statement.execute(createTableSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing the database: " + e.getMessage());
        }
    }

    @Query("INSERT INTO GS_COMPANIES (NAME, CNPJ, PHONE, WEBSITE, VERIFICATION_STATUS, CREATED_AT, UPDATED_AT, ADMIN_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
    public void create(Company company, int adminId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "create",
                    company.getName(), company.getCnpj(), company.getPhone(),
                    company.getWebsite(), company.getVerificationStatus(),
                    Timestamp.valueOf(company.getCreatedAt()), Timestamp.valueOf(company.getUpdatedAt()), adminId);
            logger.logCreate(company);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating company: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_COMPANIES WHERE COMPANY_ID = ?")
    public Company readById(int id) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> {
                Company company = new Company(
                        rs.getInt("COMPANY_ID"),
                        null, // O admin será recuperado depois
                        rs.getString("NAME"),
                        rs.getString("CNPJ"),
                        rs.getString("PHONE"),
                        rs.getString("WEBSITE"),
                        null, // A lista de produtos pode ser recuperada separadamente
                        rs.getString("VERIFICATION_STATUS")
                );
                company.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                company.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                int adminId = rs.getInt("ADMIN_ID");
                company.setAdmin(new UserRepository().readById(adminId));
                logger.logReadById(company);
                return company;
            }, "readById", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading company by id: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_COMPANIES ORDER BY COMPANY_ID ASC")
    public List<Company> readAll() {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                Company company = new Company(
                        rs.getInt("COMPANY_ID"),
                        null, // O admin será recuperado depois
                        rs.getString("NAME"),
                        rs.getString("CNPJ"),
                        rs.getString("PHONE"),
                        rs.getString("WEBSITE"),
                        null, // A lista de produtos pode ser recuperada separadamente
                        rs.getString("VERIFICATION_STATUS")
                );
                company.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                company.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                int adminId = rs.getInt("ADMIN_ID");
                company.setAdmin(new UserRepository().readById(adminId));
                return company;
            }, "readAll");


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading all companies: " + e.getMessage());
        }

    }

    @Query("UPDATE GS_COMPANIES SET NAME = ?, CNPJ = ?, PHONE = ?, WEBSITE = ?, VERIFICATION_STATUS = ?, ADMIN_ID = ?, UPDATED_AT = CURRENT_TIMESTAMP WHERE COMPANY_ID = ?")
    public boolean updateById(Company company, int id) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "updateById",
                    company.getName(),
                    company.getCnpj(),
                    company.getPhone(),
                    company.getWebsite(),
                    company.getVerificationStatus(),
                    new CompanyRepository().readById(id).getAdmin().getId(),
                    id);
            logger.logUpdateById(company);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
           return false;
        }
    }

    @Query("DELETE FROM GS_COMPANIES WHERE COMPANY_ID = ?")
    public boolean deleteById(int id) {
        try {
            Company deletedCompany = readById(id);
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);
            logger.logDeleteById(deletedCompany);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting company: " + e.getMessage());
        }
    }

}
