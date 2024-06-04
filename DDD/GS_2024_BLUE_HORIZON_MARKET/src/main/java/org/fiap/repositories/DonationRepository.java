package org.fiap.repositories;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.Donation;
import org.fiap.utils.Log4jLogger;
import org.fiap.infrastructure.QueryProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class DonationRepository extends _BaseRepositoryImpl<Donation> {
    Log4jLogger<Donation> logger = new Log4jLogger<>(Donation.class);
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public static final String TB_NAME = "GS_DONATIONS";

    public static final HashMap<String, String> TB_COLUMNS = new HashMap<>() {{
        put("DONATION_ID", "DONATION_ID");
        put("ORDER_ID", "ORDER_ID");
        put("AMOUNT", "AMOUNT");
        put("NGO_ID", "NGO_ID");
        put("CREATED_AT", "CREATED_AT");
        put("UPDATED_AT", "UPDATED_AT");
    }};

    public DonationRepository() {
        super(Donation.class);
        initialize();
    }

    public void initialize() {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = '" + TB_NAME.toUpperCase() + "'";
        String createTableSQL = "CREATE TABLE " + TB_NAME + " ("
                + "DONATION_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                + "ORDER_ID NUMBER NOT NULL, "
                + "AMOUNT NUMBER(10, 2) NOT NULL, "
                + "NGO_ID NUMBER NOT NULL, "
                + "CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "CONSTRAINT fk_order_donation FOREIGN KEY (ORDER_ID) REFERENCES GS_ORDERS(ORDER_ID), "
                + "CONSTRAINT fk_ngo_donation FOREIGN KEY (NGO_ID) REFERENCES GS_NGOS(NGO_ID))";

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


    @Query("INSERT INTO GS_DONATIONS (ORDER_ID, AMOUNT, NGO_ID, CREATED_AT, UPDATED_AT) VALUES (?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)")
    public void create(Donation donation, int orderId, int ngoId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "create",
                    orderId,
                    donation.getAmount(),
                    ngoId);
            logger.logCreate(donation);

            // Atualizar o valor total de doações da ONG
            new NgoRepository().addDonationToNgo(donation.getAmount(), ngoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating donation: " + e.getMessage());
        }
    }


    @Query("SELECT * FROM GS_DONATIONS WHERE DONATION_ID = ?")
    public Donation readById(int id) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> {
                Donation donation = new Donation(
                        rs.getInt("DONATION_ID"),
                        new OrderRepository().readById(rs.getInt("ORDER_ID")),
                        rs.getDouble("AMOUNT"),
                        new NgoRepository().readById(rs.getInt("NGO_ID"))
                );
                donation.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                donation.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());

                logger.logReadById(donation);
                return donation;
            }, "readById", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading donation by id: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_DONATIONS ORDER BY DONATION_ID ASC")
    public List<Donation> readAll() {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                Donation donation = new Donation(
                        rs.getInt("DONATION_ID"),
                        new OrderRepository().readById(rs.getInt("ORDER_ID")),
                        rs.getDouble("AMOUNT"),
                        new NgoRepository().readById(rs.getInt("NGO_ID"))
                );
                donation.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                donation.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());

                logger.logReadAll();
                return donation;
            }, "readAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading all donations: " + e.getMessage());
        }
    }

    @Query("UPDATE GS_DONATIONS SET ORDER_ID = ?, AMOUNT = ?, NGO_ID = ?, UPDATED_AT = CURRENT_TIMESTAMP WHERE DONATION_ID = ?")
    public boolean updateById(Donation donation, int id) {
        try {
            // Recupera a doação original para atualizar o valor total de doações da ONG
            Donation originalDonation = readById(id);
            QueryProcessor.executeAnnotatedMethod(this, "updateById",
                    donation.getOrder().getId(),
                    donation.getAmount(),
                    donation.getNgo().getId(),
                    id);
            logger.logUpdateById(donation);

            // Atualizar o valor total de doações da ONG
            new NgoRepository().removeDonationFromNgo(originalDonation.getAmount(), originalDonation.getNgo().getId());
            new NgoRepository().addDonationToNgo(donation.getAmount(), donation.getNgo().getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Query("DELETE FROM GS_DONATIONS WHERE DONATION_ID = ?")
    public boolean deleteById(int id) {
        try {
            Donation deletedDonation = readById(id);
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);
            // Atualizar o valor total de doações da ONG
            new NgoRepository().removeDonationFromNgo(deletedDonation.getAmount(), deletedDonation.getNgo().getId());
            logger.logDeleteById(deletedDonation);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Query("DELETE FROM GS_DONATIONS WHERE ORDER_ID = ?")
    public void deleteDonationsByOrderId(int orderId) {
        try {
            Donation deletedDonation = readById(orderId);
            QueryProcessor.executeAnnotatedMethod(this, "deleteDonationsByOrderId", orderId);
            logger.logDeleteById(deletedDonation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting donations: " + e.getMessage());
        }
    }


    @Query("SELECT * FROM GS_DONATIONS WHERE ORDER_ID = ?")
    public List<Donation> readByOrderId(int orderId) {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                Donation donation = new Donation(
                        rs.getInt("DONATION_ID"),
                        new OrderRepository().readById(rs.getInt("ORDER_ID")),
                        rs.getDouble("AMOUNT"),
                        new NgoRepository().readById(rs.getInt("NGO_ID"))
                );
                donation.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                donation.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return donation;
            }, "readByOrderId", orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading donations by order ID: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_DONATIONS WHERE NGO_ID = ?")
    public List<Donation> readByNgoId(int ngoId) {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                Donation donation = new Donation(
                        rs.getInt("DONATION_ID"),
                        new OrderRepository().readById(rs.getInt("ORDER_ID")),
                        rs.getDouble("AMOUNT"),
                        new NgoRepository().readById(rs.getInt("NGO_ID"))
                );
                donation.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                donation.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return donation;
            }, "readByNgoId", ngoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading donations by NGO ID: " + e.getMessage());
        }
    }

}
