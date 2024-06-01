package org.fiap.repositories;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.User;
import org.fiap.utils.QueryProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class UserRepository extends _BaseRepositoryImpl<User> {

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
                + "USER_TYPE VARCHAR2(50) NOT NULL, "
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
            QueryProcessor.executeAnnotatedMethod(this, "create", user.getName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getUserType(), user.getPhone(), java.sql.Date.valueOf(user.getBirthDate()));
            System.out.println("Cadastrado");
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

                return user;
            }, "readAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding all users: " + e.getMessage());
        }
    }


    @Query("UPDATE GS_USERS SET NOME = ?, SOBRENOME = ?, EMAIL = ?, PASSWORD = ?, USER_TYPE = ?, PHONE = ?, BIRTHDATE = ?, UPDATED_AT = CURRENT_TIMESTAMP WHERE USER_ID = ?")
    public void updateById(User user, int id) {
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
            System.out.println("User updated");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    @Query("DELETE FROM GS_USERS WHERE USER_ID = ?")
    public void deleteById(int id) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);
            System.out.println("User deleted");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

}
