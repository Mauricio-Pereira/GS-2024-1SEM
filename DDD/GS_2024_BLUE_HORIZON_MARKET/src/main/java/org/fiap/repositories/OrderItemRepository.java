package org.fiap.repositories;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.Order;
import org.fiap.entities.OrderItem;
import org.fiap.utils.Log4jLogger;
import org.fiap.utils.QueryProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class OrderItemRepository extends _BaseRepositoryImpl<OrderItem> {
    Log4jLogger<OrderItem> logger = new Log4jLogger<>(OrderItem.class);
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public static final String TB_NAME = "GS_ORDER_ITEMS";

    public static final HashMap<String, String> TB_COLUMNS = new HashMap<>() {{
        put("ORDER_ITEM_ID", "ORDER_ITEM_ID");
        put("ORDER_ID", "ORDER_ID");
        put("PRODUCT_ID", "PRODUCT_ID");
        put("QUANTITY", "QUANTITY");
        put("PRICE", "PRICE");
        put("CREATED_AT", "CREATED_AT");
        put("UPDATED_AT", "UPDATED_AT");
    }};

    public OrderItemRepository() {
        super(OrderItem.class);
        initialize();
    }

    public void initialize() {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = '" + TB_NAME.toUpperCase() + "'";
        String createTableSQL = "CREATE TABLE " + TB_NAME + " ("
                + "ORDER_ITEM_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                + "ORDER_ID NUMBER NOT NULL, "
                + "PRODUCT_ID NUMBER NOT NULL, "
                + "QUANTITY NUMBER NOT NULL, "
                + "PRICE NUMBER(10, 2) NOT NULL, "
                + "CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "CONSTRAINT fk_order FOREIGN KEY (ORDER_ID) REFERENCES GS_ORDERS(ORDER_ID), "
                + "CONSTRAINT fk_product FOREIGN KEY (PRODUCT_ID) REFERENCES GS_PRODUCTS(PRODUCT_ID))";

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

    @Query("INSERT INTO GS_ORDER_ITEMS (ORDER_ID, PRODUCT_ID, QUANTITY, PRICE, CREATED_AT, UPDATED_AT) VALUES (?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)")
    public void create(OrderItem orderItem) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "create",
                    orderItem.getOrder().getId(),
                    orderItem.getProduct().getId(),
                    orderItem.getQuantity(),
                    orderItem.getPrice());
            logger.logCreate(orderItem);

            // Recalcular e atualizar os valores do pedido
            Order order = new OrderRepository().readById(orderItem.getOrder().getId());
            order.addItem(orderItem);
            new OrderRepository().updateOrderAmounts(order);
            new OrderRepository().updateById(order, orderItem.getOrder().getId()); // Atualiza a ordem no banco de dados
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating order item: " + e.getMessage());
        }
    }


    @Query("SELECT * FROM GS_ORDER_ITEMS WHERE ORDER_ITEM_ID = ?")
    public OrderItem readById(int id) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> {
                OrderItem orderItem = new OrderItem(
                        rs.getInt("ORDER_ITEM_ID"),
                        new OrderRepository().readById(rs.getInt("ORDER_ID")), // Recupera o pedido associado
                        new ProductRepository().readById(rs.getInt("PRODUCT_ID")), // Recupera o produto associado
                        rs.getInt("QUANTITY")
                );
                orderItem.setPrice(rs.getDouble("PRICE"));
                orderItem.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                orderItem.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return orderItem;
            }, "readById", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading order item by id: " + e.getMessage());
        }
    }


    @Query("SELECT * FROM GS_ORDER_ITEMS")
    public List<OrderItem> readAll() {
        logger.logReadAll();
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                OrderItem orderItem = new OrderItem(
                        rs.getInt("ORDER_ITEM_ID"),
                        null,
                        new ProductRepository().readById(rs.getInt("PRODUCT_ID")),
                        rs.getInt("QUANTITY")
                );
                orderItem.setPrice(rs.getDouble("PRICE"));
                orderItem.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                orderItem.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return orderItem;
            }, "readAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading all order items: " + e.getMessage());
        }
    }

    @Query("UPDATE GS_ORDER_ITEMS SET ORDER_ID = ?, PRODUCT_ID = ?, QUANTITY = ?, PRICE = ?, UPDATED_AT = CURRENT_TIMESTAMP WHERE ORDER_ITEM_ID = ?")
    public boolean updateById(OrderItem orderItem, int id, int orderId, int productId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "updateById",
                    orderId,
                    productId,
                    orderItem.getQuantity(),
                    orderItem.getPrice(),
                    id);
            logger.logUpdateById(orderItem);

            // Recalcular e atualizar os valores do pedido
            Order order = new OrderRepository().readById(orderId);
            order.addItem(orderItem);  // Supondo que o orderItem já exista na lista, isso pode precisar de ajuste
            new OrderRepository().updateOrderAmounts(order);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Query("SELECT * FROM GS_ORDER_ITEMS WHERE PRODUCT_ID = ?")
    public List<OrderItem> getItemsByProductId(int productId) {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                OrderItem orderItem = new OrderItem(
                        rs.getInt("ORDER_ITEM_ID"),
                        new OrderRepository().readById(rs.getInt("ORDER_ID")),
                        new ProductRepository().readById(rs.getInt("PRODUCT_ID")),
                        rs.getInt("QUANTITY")
                );
                orderItem.setPrice(rs.getDouble("PRICE"));
                orderItem.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                orderItem.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return orderItem;
            }, "getItemsByProductId", productId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving order items by product ID: " + e.getMessage());
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

    @Query("DELETE FROM GS_ORDER_ITEMS WHERE PRODUCT_ID = ?")
    public void deleteOrderItemsByProductId(int productId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "deleteOrderItemsByProductId", productId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting order items by product id: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_ORDER_ITEMS WHERE ORDER_ID = ?")
    public List<OrderItem> readByOrderId(int orderId) {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                OrderItem orderItem = new OrderItem(
                        rs.getInt("ORDER_ITEM_ID"),
                        new OrderRepository().readById(rs.getInt("ORDER_ID")),
                        new ProductRepository().readById(rs.getInt("PRODUCT_ID")),
                        rs.getInt("QUANTITY")
                );
                orderItem.setPrice(rs.getDouble("PRICE"));
                orderItem.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                orderItem.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return orderItem;
            }, "readByOrderId", orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading order items by order ID: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_ORDER_ITEMS WHERE ORDER_ID = ?")
    public List<OrderItem> getItemsByOrderId(int orderId) {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                OrderItem orderItem = new OrderItem(
                        rs.getInt("ORDER_ITEM_ID"),
                        new OrderRepository().readById(rs.getInt("ORDER_ID")),
                        new ProductRepository().readById(rs.getInt("PRODUCT_ID")),
                        rs.getInt("QUANTITY")
                );
                orderItem.setPrice(rs.getDouble("PRICE"));
                orderItem.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                orderItem.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return orderItem;
            }, "getItemsByOrderId", orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving order items by order ID: " + e.getMessage());
        }
    }

    @Query("DELETE FROM GS_ORDER_ITEMS WHERE ORDER_ID = ?")
    public void deleteOrderItems(int orderId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "deleteOrderItems", orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting order items for order id: " + orderId);
        }
    }

    @Query("DELETE FROM GS_ORDER_ITEMS WHERE ORDER_ITEM_ID = ?")
    public boolean deleteById(int id) {
        try {
            OrderItem deletedOrderItem = readById(id);
            Order order = new OrderRepository().readById(deletedOrderItem.getOrder().getId());
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);

            // Recalcular e atualizar os valores do pedido
            order.removeItem(deletedOrderItem);
            new OrderRepository().updateOrderAmounts(order);
            logger.logDeleteById(deletedOrderItem);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
