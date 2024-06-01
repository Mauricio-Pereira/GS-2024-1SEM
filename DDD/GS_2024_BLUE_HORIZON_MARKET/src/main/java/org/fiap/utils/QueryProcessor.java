package org.fiap.utils;

import org.fiap.annotations.Query;
import org.fiap.connection.DatabaseConnection;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryProcessor {

    private static final DatabaseConnection databaseConnection = new DatabaseConnection();

    public static void executeAnnotatedMethod(Object obj, String methodName, Object... params) throws Exception {
        Method method = getMethodByName(obj.getClass(), methodName);
        if (method != null && method.isAnnotationPresent(Query.class)) {
            Query query = method.getAnnotation(Query.class);
            executeUpdateQuery(query.value(), params);
        }
    }

    private static Method getMethodByName(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    public static <T> List<T> executeSelectQuery(Object obj, ResultProcessor<T> processor, String methodName, Object... params) throws Exception {
        Method method = getMethodByName(obj.getClass(), methodName);
        if (method != null && method.isAnnotationPresent(Query.class)) {
            Query query = method.getAnnotation(Query.class);
            return executeQuery(query.value(), processor, params);
        }
        return new ArrayList<>();
    }

    public static <T> T executeSingleResultQuery(Object obj, ResultProcessor<T> processor, String methodName, Object... params) throws Exception {
        List<T> results = executeSelectQuery(obj, processor, methodName, params);
        if (!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    private static <T> List<T> executeQuery(String query, ResultProcessor<T> processor, Object... params) throws Exception {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }


            if (query.trim().toUpperCase().startsWith("SELECT")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<T> results = new ArrayList<>();
                    while (resultSet.next()) {
                        if (processor != null) {
                            results.add(processor.process(resultSet));
                        }
                    }
                    return results;
                }
            } else {
                statement.executeUpdate();
                return new ArrayList<>();
            }
        }


    }

    private static void executeUpdateQuery(String query, Object... params) throws Exception {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }

            statement.executeUpdate();
        }
    }
}
