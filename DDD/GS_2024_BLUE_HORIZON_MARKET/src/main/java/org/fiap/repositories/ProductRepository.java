package org.fiap.repositories;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;
import org.fiap.entities.Product;
import org.fiap.utils.Log4jLogger;
import org.fiap.utils.QueryProcessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class ProductRepository extends _BaseRepositoryImpl<Product> {
    Log4jLogger<Product> logger = new Log4jLogger<>(Product.class);
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    public static final String TB_NAME = "GS_PRODUCTS";

    public static final HashMap<String, String> TB_COLUMNS = new HashMap<>() {{
        put("PRODUCT_ID", "PRODUCT_ID");
        put("NAME", "NAME");
        put("DESCRIPTION", "DESCRIPTION");
        put("PRICE", "PRICE");
        put("STOCK", "STOCK");
        put("CREATED_AT", "CREATED_AT");
        put("UPDATED_AT", "UPDATED_AT");
        put("COMPANY_ID", "COMPANY_ID");
    }};

    public ProductRepository() {
        super(Product.class);
        initialize();
    }

    public void initialize() {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = '" + TB_NAME.toUpperCase() + "'";
        String createTableSQL = "CREATE TABLE " + TB_NAME + " ("
                + "PRODUCT_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                + "NAME VARCHAR2(255) NOT NULL, "
                + "DESCRIPTION VARCHAR2(255) NOT NULL, "
                + "PRICE NUMBER(10, 2) NOT NULL, "
                + "STOCK NUMBER NOT NULL, "
                + "CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP, "
                + "COMPANY_ID NUMBER NOT NULL, "
                + "CONSTRAINT fk_company FOREIGN KEY (COMPANY_ID) REFERENCES GS_COMPANIES(COMPANY_ID))";

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

    @Query("INSERT INTO GS_PRODUCTS (NAME, DESCRIPTION, PRICE, STOCK, CREATED_AT, UPDATED_AT, COMPANY_ID) VALUES (?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP, ?)")
    public void create(Product product, int companyId) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "create",
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    companyId);
            logger.logCreate(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating product: " + e.getMessage());
        }
    }

    @Query("SELECT * FROM GS_PRODUCTS WHERE PRODUCT_ID = ?")
    public Product readById(int id) {
        try {
            return QueryProcessor.executeSingleResultQuery(this, rs -> {
                Product product = new Product(
                        rs.getInt("PRODUCT_ID"),
                        null, // Placeholder for Company, which needs to be set separately
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDouble("PRICE"),
                        rs.getInt("STOCK")
                );
                product.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());

                logger.logReadById(product);
                return product;
            }, "readById", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading product by id: " + e.getMessage());
        }
    }


    @Query("SELECT * FROM GS_PRODUCTS")
    public List<Product> readAll() {
        try {
            return QueryProcessor.executeSelectQuery(this, rs -> {
                Product product = new Product(
                        rs.getInt("PRODUCT_ID"),
                        null, // Placeholder for Company, which needs to be set separately
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDouble("PRICE"),
                        rs.getInt("STOCK")
                );
                product.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                logger.logReadAll();
                return product;
            }, "readAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading all products: " + e.getMessage());
        }
    }

    @Query("UPDATE GS_PRODUCTS SET NAME = ?, DESCRIPTION = ?, PRICE = ?, STOCK = ?, UPDATED_AT = CURRENT_TIMESTAMP WHERE PRODUCT_ID = ?")
    public boolean updateById(Product product, int id) {
        try {
            QueryProcessor.executeAnnotatedMethod(this, "updateById",
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    id);
            logger.logUpdateById(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

    @Query("DELETE FROM GS_PRODUCTS WHERE PRODUCT_ID = ?")
    public boolean deleteById(int id) {
        try {
            // Primeiro, exclua todos os OrderItems relacionados a esse Product
            deleteOrderItemsByProductId(id);

            // Agora, exclua o Product
            QueryProcessor.executeAnnotatedMethod(this, "deleteById", id);
            logger.logDeleteById(readById(id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
