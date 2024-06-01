package org.fiap.repositories;

import oracle.sql.NUMBER;
import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.Address;
import org.fiap.entities.User;
import org.fiap.utils.Log4jLogger;
import org.fiap.utils.QueryProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressRepository extends _BaseRepositoryImpl<Address>{
    Log4jLogger<Address> logger = new Log4jLogger<>(Address.class);
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public static final String TB_NAME = "GS_ADDRESSES";

    public static final HashMap<String, String> TB_COLUMNS = new HashMap<>(){{
            put("ADDRESS_ID", "ADDRESS_ID");
            put("CEP", "CEP");
            put("COUNTRY", "COUNTRY");
            put("STATE", "STATE");
            put("CITY", "CITY");
            put("NEIGHBORHOOD", "NEIGHBORHOOD");
            put("STREET", "STREET");
            put("NUMBER", "NUMBER");
            put("COMPLEMENT", "COMPLEMENT");
            put("CREATED_AT", "CREATED_AT");
            put("UPDATED_AT", "UPDATED_AT");
            put("GS_USERS_USER_ID", "GS_USERS_USER_ID");
    }};

    public AddressRepository() {
        super(Address.class);
        initialize();
    }
    public void initialize() {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = '" + TB_NAME.toUpperCase() + "'";
        String createTableSQL = "CREATE TABLE " + TB_NAME + " ("
                + "ADDRESS_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
                + "CEP VARCHAR2(10) NOT NULL, "
                + "COUNTRY VARCHAR2(100) NOT NULL, "
                + "STATE VARCHAR2(100) NOT NULL, "
                + "CITY VARCHAR2(100) NOT NULL, "
                + "NEIGHBORHOOD VARCHAR2(100) NOT NULL, "
                + "STREET VARCHAR2(255) NOT NULL, "
                + "\"NUMBER\" VARCHAR2(10) NOT NULL, "
                + "COMPLEMENT VARCHAR2(255), "
                + "CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "GS_USERS_USER_ID NUMBER, "
                + "CONSTRAINT fk_user FOREIGN KEY (GS_USERS_USER_ID) REFERENCES GS_USERS(USER_ID))";


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

    @Query("INSERT INTO GS_ADDRESSES (CEP, COUNTRY, STATE, CITY, NEIGHBORHOOD, STREET, \"NUMBER\", COMPLEMENT, CREATED_AT, " +
            "UPDATED_AT, GS_USERS_USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
    public void create(Address address, int userId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "create", address.getZipCode(), address.getCountry(),
                    address.getState(), address.getCity(), address.getNeighborhood(), address.getStreet(), address.getNumber(),
                    address.getComplement(), address.getCreatedAt(), address.getUpdatedAt(), userId);
            logger.logCreate(address);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating address: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_ADDRESSES WHERE ADDRESS_ID = ?")
    public Address readById(int id) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> {
                    Address address = new Address(
                            rs.getInt("ADDRESS_ID"),
                            rs.getString("STREET"),
                            rs.getString("NUMBER"),
                            rs.getString("COMPLEMENT"),
                            rs.getString("NEIGHBORHOOD"),
                            rs.getString("CITY"),
                            rs.getString("STATE"),
                            rs.getString("COUNTRY"),
                            rs.getString("CEP")
                    );
                    address.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                    address.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                    logger.logReadById(address);
                    return address;
            }, "readById", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading address by id: " + e.getMessage());
        }
    }


    @Query("SELECT * FROM GS_ADDRESSES")
    public List<Address> readAll() {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                Address address = new Address(
                        rs.getInt("ADDRESS_ID"),
                        rs.getString("STREET"),
                        rs.getString("NUMBER"),
                        rs.getString("COMPLEMENT"),
                        rs.getString("NEIGHBORHOOD"),
                        rs.getString("CITY"),
                        rs.getString("STATE"),
                        rs.getString("COUNTRY"),
                        rs.getString("CEP")
                );
                address.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                address.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                logger.logReadAll();
                return address;
            }, "readAll");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading all addresses: " + e.getMessage());
        }
    }

    @Query("UPDATE GS_ADDRESSES SET CEP = ?, COUNTRY = ?, STATE = ?, CITY = ?, NEIGHBORHOOD = ?, STREET = ?, \"NUMBER\" = ?, COMPLEMENT = ?, UPDATED_AT = SYSTIMESTAMP, GS_USERS_USER_ID = ? WHERE ADDRESS_ID = ?")
    public boolean updateById(Address address, int id) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "updateById", address.getZipCode(), address.getCountry(),
                    address.getState(), address.getCity(), address.getNeighborhood(), address.getStreet(), address.getNumber(),
                    address.getComplement(), address.getUser() != null ? address.getUser().getId() : null, id);
            logger.logUpdateById(address);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating address: " + e.getMessage());
        }
    }

    @Query("DELETE FROM GS_ADDRESSES WHERE ADDRESS_ID = ?")
    public boolean deleteById(int id) {
        try {
            Address deletedAddress = readById(id);
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);
            logger.logDeleteById(deletedAddress);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting address: " + e.getMessage());
        }
    }




}
