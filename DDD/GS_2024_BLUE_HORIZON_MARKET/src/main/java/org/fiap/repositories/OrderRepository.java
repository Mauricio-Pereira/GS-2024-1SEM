package org.fiap.repositories;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.*;
import org.fiap.utils.Log4jLogger;
import org.fiap.utils.QueryProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderRepository extends _BaseRepositoryImpl<Order> {
    Log4jLogger<Order> logger = new Log4jLogger<>(Order.class);
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public static final String TB_NAME = "GS_ORDERS";

    public static final HashMap<String, String> TB_COLUMNS = new HashMap<>() {{
        put("ORDER_ID", "ORDER_ID");
        put("ORDER_NUMBER", "ORDER_NUMBER");
        put("BUYER_ID", "BUYER_ID");
        put("TOTAL_AMOUNT", "TOTAL_AMOUNT");
        put("DONATION_AMOUNT", "DONATION_AMOUNT");
        put("MAINTENANCE_AMOUNT", "MAINTENANCE_AMOUNT");
        put("ORDER_STATUS", "ORDER_STATUS");
        put("CREATED_AT", "CREATED_AT");
        put("UPDATED_AT", "UPDATED_AT");
    }};

    public OrderRepository() {
        super(Order.class);
        initialize();
    }

    public void initialize() {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = '" + TB_NAME.toUpperCase() + "'";
        String createTableSQL = "CREATE TABLE " + TB_NAME + " ("
                + "ORDER_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                + "ORDER_NUMBER VARCHAR2(75) NOT NULL, "
                + "BUYER_ID NUMBER NOT NULL, "
                + "TOTAL_AMOUNT NUMBER(10, 2) NOT NULL, "
                + "DONATION_AMOUNT NUMBER(10, 2) NOT NULL, "
                + "MAINTENANCE_AMOUNT NUMBER(10, 2) NOT NULL, "
                + "ORDER_STATUS VARCHAR2(20) NOT NULL, "
                + "CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "CONSTRAINT fk_buyer FOREIGN KEY (BUYER_ID) REFERENCES GS_USERS(USER_ID))";

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

    @Query("INSERT INTO GS_ORDERS (ORDER_NUMBER, BUYER_ID, TOTAL_AMOUNT, DONATION_AMOUNT, MAINTENANCE_AMOUNT, ORDER_STATUS, CREATED_AT, UPDATED_AT) VALUES (?, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)")
    public void create(Order order, int buyerId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "create",
                    order.getOrderNumber(),
                    buyerId,
                    order.getTotalAmount(),
                    order.getDonationAmount(),
                    order.getMaintenanceAmount(),
                    order.getOrderStatus());

            logger.logCreate(order);

            // Recupera o ID gerado automaticamente usando o número da ordem
            Integer generatedId = getOrderIdByOrderNumber(order.getOrderNumber());

            if (generatedId != null) {
                order.setId(generatedId);
            } else {
                throw new RuntimeException("Error retrieving generated order ID.");
            }

            // Cria automaticamente uma doação com base no valor de donationAmount da Order
            List<Ngo> ngos = new NgoRepository().readAll();
            if (!ngos.isEmpty()) {
                double amountPerNgo = order.getDonationAmount() / ngos.size();
                for (Ngo ngo : ngos) {
                    Donation donation = new Donation(order, amountPerNgo, ngo);
                    new DonationRepository().create(donation, order.getId(), ngo.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating order: " + e.getMessage());
        }
    }




    @Query("SELECT * FROM GS_ORDERS WHERE ORDER_ID = ?")
    public Order readById(int id) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> {
                Order order = new Order(
                        rs.getInt("ORDER_ID"),
                        rs.getString("ORDER_NUMBER"),
                        null,
                        rs.getDouble("TOTAL_AMOUNT"),
                        rs.getDouble("DONATION_AMOUNT"),
                        rs.getDouble("MAINTENANCE_AMOUNT"),
                        rs.getString("ORDER_STATUS"),
                        null
                );
                order.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                order.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());


                logger.logReadById(order);
                return order;
            }, "readById", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading order by id: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_ORDERS")
    public List<Order> readAll() {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                Order order = new Order(
                        rs.getInt("ORDER_ID"),
                        rs.getString("ORDER_NUMBER"),
                        null,
                        rs.getDouble("TOTAL_AMOUNT"),
                        rs.getDouble("DONATION_AMOUNT"),
                        rs.getDouble("MAINTENANCE_AMOUNT"),
                        rs.getString("ORDER_STATUS"),
                        null
                );
                order.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                order.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());


                logger.logReadAll();
                return order;
            }, "readAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading all orders: " + e.getMessage());
        }
    }

    @Query("UPDATE GS_ORDERS SET ORDER_NUMBER = ?, BUYER_ID = ?, TOTAL_AMOUNT = ?, DONATION_AMOUNT = ?, MAINTENANCE_AMOUNT = ?, ORDER_STATUS = ?, UPDATED_AT = SYSTIMESTAMP WHERE ORDER_ID = ?")
    public boolean updateById(Order order, int orderId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "updateById",
                    order.getOrderNumber(),
                    order.getBuyer().getId(),
                    order.getTotalAmount(),
                    order.getDonationAmount(),
                    order.getMaintenanceAmount(),
                    order.getOrderStatus(),
                    orderId);
            logger.logUpdateById(order);

            // Atualizar doações associadas
            new DonationRepository().deleteDonationsByOrderId(orderId);
            List<Ngo> ngos = new NgoRepository().readAll();
            if (!ngos.isEmpty()) {
                double amountPerNgo = order.getDonationAmount() / ngos.size();
                for (Ngo ngo : ngos) {
                    Donation donation = new Donation(0, order, amountPerNgo, ngo);
                    new DonationRepository().create(donation, orderId, ngo.getId());
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Query("DELETE FROM GS_ORDER_ITEMS WHERE ORDER_ID = ?")
    public void deleteOrderItemsByOrderId(int orderId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "deleteOrderItemsByOrderId", orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting order items by order id: " + e.getMessage());
        }
    }

    @Query("DELETE FROM GS_ORDERS WHERE ORDER_ID = ?")
    public boolean deleteById(int id) {
        try {
            Order deletedOrder = new OrderRepository().readById(id);
            // Excluir itens de pedido relacionados
            List<OrderItem> orderItems = new OrderItemRepository().readByOrderId(id);
            for (OrderItem item : orderItems) {
                new OrderItemRepository().deleteById(item.getId());
            }

            // Excluir doações relacionadas
            List<Donation> donations = new DonationRepository().readByOrderId(id);
            for (Donation donation : donations) {
                new DonationRepository().deleteById(donation.getId());
            }

            // Excluir a ordem
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);
            logger.logDeleteById(deletedOrder);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting order: " + e.getMessage());
        }
    }




    @Query("UPDATE GS_ORDERS SET TOTAL_AMOUNT = ?, DONATION_AMOUNT = ?, MAINTENANCE_AMOUNT = ?, UPDATED_AT = CURRENT_TIMESTAMP WHERE ORDER_ID = ?")
    public void updateOrderAmounts(Order order) {
        order.recalculateAmounts();
        try {
            QueryProcessor.executeAnnotatedMethod(this, "updateOrderAmounts",
                    order.getTotalAmount(),
                    order.getDonationAmount(),
                    order.getMaintenanceAmount(),
                    order.getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating order amounts: " + e.getMessage());
        }
    }

    @Query("SELECT ORDER_ID FROM GS_ORDERS WHERE ORDER_NUMBER = ?")
    public Integer getOrderIdByOrderNumber(String orderNumber) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> rs.getInt("ORDER_ID"), "getOrderIdByOrderNumber", orderNumber);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving order ID by order number: " + e.getMessage());
        }
    }

}
