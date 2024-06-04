package org.fiap.repositories;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.Donation;
import org.fiap.entities.Ngo;
import org.fiap.utils.Log4jLogger;
import org.fiap.infrastructure.QueryProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class NgoRepository extends _BaseRepositoryImpl<Ngo> {
    Log4jLogger<Ngo> logger = new Log4jLogger<>(Ngo.class);
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public static final String TB_NAME = "GS_NGOS";

    public static final HashMap<String, String> TB_COLUMNS = new HashMap<>() {{
        put("NGO_ID", "NGO_ID");
        put("CONTACT_USER_ID", "CONTACT_USER_ID");
        put("NAME", "NAME");
        put("MISSION", "MISSION");
        put("PHONE", "PHONE");
        put("WEBSITE", "WEBSITE");
        put("CONTACT_EMAIL", "CONTACT_EMAIL");
        put("TOTAL_DONATIONS", "TOTAL_DONATIONS");
        put("CREATED_AT", "CREATED_AT");
        put("UPDATED_AT", "UPDATED_AT");
    }};

    public NgoRepository() {
        super(Ngo.class);
        initialize();
    }

    public void initialize() {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = '" + TB_NAME.toUpperCase() + "'";
        String createTableSQL = "CREATE TABLE " + TB_NAME + " ("
                + "NGO_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                + "CONTACT_USER_ID NUMBER NOT NULL, "
                + "NAME VARCHAR2(255) NOT NULL, "
                + "MISSION VARCHAR2(255) NOT NULL, "
                + "PHONE VARCHAR2(20), "
                + "WEBSITE VARCHAR2(255), "
                + "CONTACT_EMAIL VARCHAR2(255), "
                + "TOTAL_DONATIONS NUMBER DEFAULT 0, "
                + "CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "CONSTRAINT fk_contact_user FOREIGN KEY (CONTACT_USER_ID) REFERENCES GS_USERS(USER_ID))";


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


    @Query("INSERT INTO GS_NGOS (CONTACT_USER_ID, NAME, MISSION, PHONE, WEBSITE, CONTACT_EMAIL, CREATED_AT, UPDATED_AT) VALUES (?, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)")
    public void create(Ngo ngo, int admin_id) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "create",
                    admin_id,
                    ngo.getName(),
                    ngo.getMission(),
                    new UserRepository().readById(admin_id).getPhone(),
                    ngo.getWebsite(),
                    new UserRepository().readById(admin_id).getEmail());

            logger.logCreate(ngo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating NGO: " + e.getMessage());
        }
    }


    @Query("SELECT * FROM GS_NGOS WHERE NGO_ID = ?")
    public Ngo readById(int id) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> {
                Ngo ngo = new Ngo(
                        rs.getInt("NGO_ID"),
                        new UserRepository().readById(rs.getInt("CONTACT_USER_ID")),
                        rs.getString("NAME"),
                        rs.getString("MISSION"),
                        rs.getString("PHONE"),
                        rs.getString("WEBSITE"),
                        rs.getString("CONTACT_EMAIL")
                );
                ngo.setTotalDonations(rs.getDouble("TOTAL_DONATIONS"));
                ngo.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                ngo.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());

                logger.logReadById(ngo);
                return ngo;
            }, "readById", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading NGO by id: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_NGOS ORDER BY NGO_ID ASC")
    public List<Ngo> readAll() {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                Ngo ngo = new Ngo(
                        rs.getInt("NGO_ID"),
                        new UserRepository().readById(rs.getInt("CONTACT_USER_ID")),
                        rs.getString("NAME"),
                        rs.getString("MISSION"),
                        rs.getString("PHONE"),
                        rs.getString("WEBSITE"),
                        rs.getString("CONTACT_EMAIL")
                );
                ngo.setTotalDonations(rs.getDouble("TOTAL_DONATIONS"));
                ngo.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                ngo.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());

                logger.logReadAll();
                return ngo;
            }, "readAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading all NGOs: " + e.getMessage());
        }
    }

    @Query("UPDATE GS_NGOS SET CONTACT_USER_ID = ?, NAME = ?, MISSION = ?, PHONE = ?, WEBSITE = ?, CONTACT_EMAIL = ?, UPDATED_AT = CURRENT_TIMESTAMP WHERE NGO_ID = ?")
    public boolean updateById(Ngo ngo, int id) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "updateById",
                    ngo.getContactUser().getId(),
                    ngo.getName(),
                    ngo.getMission(),
                    ngo.getPhone(),
                    ngo.getWebsite(),
                    ngo.getContactEmail(),
                    id);
            logger.logUpdateById(ngo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Query("DELETE FROM GS_NGOS WHERE NGO_ID = ?")
    public boolean deleteById(int id) {
        try {
            Ngo deletedNgo = readById(id);
            // Excluir doações relacionadas
            List<Donation> donations = new DonationRepository().readByNgoId(id);
            for (Donation donation : donations) {
                new DonationRepository().deleteById(donation.getId());
            }

            // Excluir a ONG
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);
            logger.logDeleteById(deletedNgo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting NGO: " + e.getMessage());
        }
    }


    @Query("UPDATE GS_NGOS SET TOTAL_DONATIONS = TOTAL_DONATIONS + ? WHERE NGO_ID = ?")
    public void addDonationToNgo(double amount, int ngoId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "addDonationToNgo", amount, ngoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding donation to NGO: " + e.getMessage());
        }
    }

    @Query("UPDATE GS_NGOS SET TOTAL_DONATIONS = TOTAL_DONATIONS - ? WHERE NGO_ID = ?")
    public void removeDonationFromNgo(double amount, int ngoId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "removeDonationFromNgo", amount, ngoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error removing donation from NGO: " + e.getMessage());
        }
    }
}