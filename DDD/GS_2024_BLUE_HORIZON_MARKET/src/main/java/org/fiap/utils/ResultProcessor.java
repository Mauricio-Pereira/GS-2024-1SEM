package org.fiap.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultProcessor<T> {
    T process(ResultSet rs) throws SQLException;
}