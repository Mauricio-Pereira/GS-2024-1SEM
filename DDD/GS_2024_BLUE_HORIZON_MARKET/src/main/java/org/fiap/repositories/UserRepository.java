package org.fiap.repositories;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.User;
import org.fiap.utils.Log4jLogger;
import org.fiap.utils.QueryProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class UserRepository extends _BaseRepositoryImpl<User> {
    Log4jLogger<User> logger = new Log4jLogger<>(User.class);
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public static final String TB_NAME = "GS_USERS";
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "USER_ID", "USER_ID",
            "NOME", "NOME",
            "SOBRENOME", "SOBRENOME",
            "EMAIL", "EMAIL",
            "PASSWORD", "PASSWORD",
            "USER_TYPE", "USER_TYPE",
            "PHONE", "PHONE",
            "BIRTHDATE", "BIRTHDATE",
            "CREATED_AT", "CREATED_AT",
            "UPDATED_AT", "UPDATED_AT"
    );

    public UserRepository() {
        super(User.class);
        initialize();
    }

    public void initialize() {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = '" + TB_NAME.toUpperCase() + "'";
        String createTableSQL = "CREATE TABLE " + TB_NAME + " ("
                + "USER_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                + "NOME VARCHAR2(255) NOT NULL, "
                + "SOBRENOME VARCHAR2(255) NOT NULL, "
                + "EMAIL VARCHAR2(255) NOT NULL UNIQUE, "
                + "PASSWORD VARCHAR2(255) NOT NULL, "
                + "USER_TYPE VARCHAR2(50) NOT NULL CHECK (USER_TYPE IN ('buyer', 'admin_company', 'admin_ngo')), "
                + "PHONE VARCHAR2(20), "
                + "BIRTHDATE DATE, "
                + "CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, "
                + "UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)";

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

    @Query("INSERT INTO GS_USERS (NOME, SOBRENOME, EMAIL, PASSWORD, USER_TYPE, PHONE, BIRTHDATE, CREATED_AT, UPDATED_AT) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    public void create(User user) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "create", user.getName(), user.getLastName(),
                    user.getEmail(), user.getPassword(), user.getUserType(), user.getPhone(), java.sql.Date.valueOf(user.getBirthDate()));
            logger.logCreate(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }



    @Query("SELECT * FROM GS_USERS WHERE USER_ID = ?")
    public User readById(int id) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> {
                User user = new User(rs.getInt("USER_ID"),
                        rs.getString("NOME"),
                        rs.getString("SOBRENOME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("USER_TYPE"),
                        rs.getString("PHONE"),
                        rs.getDate("BIRTHDATE").toLocalDate());
                logger.logReadById(user);
                return user;
            }, "readById", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding user by id: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_USERS")
    public List<User> readAll() {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                User user = new User(rs.getInt("USER_ID"),
                        rs.getString("NOME"),
                        rs.getString("SOBRENOME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("USER_TYPE"),
                        rs.getString("PHONE"),
                        rs.getDate("BIRTHDATE").toLocalDate());
                logger.logReadAll();
                return user;
            }, "readAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding all users: " + e.getMessage());
        }
    }


    @Query("UPDATE GS_USERS SET NOME = ?, SOBRENOME = ?, EMAIL = ?, PASSWORD = ?, USER_TYPE = ?, PHONE = ?, BIRTHDATE = ?, UPDATED_AT = CURRENT_TIMESTAMP WHERE USER_ID = ?")
    public boolean updateById(User user, int id) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "updateById",
                    user.getName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getUserType(),
                    user.getPhone(),
                    java.sql.Date.valueOf(user.getBirthDate()),
                    id);
            logger.logUpdateById(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Query("DELETE FROM GS_USERS WHERE USER_ID = ?")
    public boolean deleteById(int id) {
        try {
            User deletedUser = readById(id);
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);
            logger.logDeleteById(deletedUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
