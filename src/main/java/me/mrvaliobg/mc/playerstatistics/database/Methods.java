package me.mrvaliobg.mc.playerstatistics.database;

import me.mrvaliobg.mc.playerstatistics.logging.ClassLogger;

import java.sql.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Methods {

    private static final Logger LOGGER  = new ClassLogger(Methods.class);
    public static final String DATABASE_ERROR = "Database connection error occurred";

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool(10);

    public static void executeQuery(final String query) {
        EXECUTOR_SERVICE.submit(() -> {
            try (Connection connection = getConnection();
                 Statement statement = connection != null ? connection.createStatement() : null) {
                if (connection != null) {
                    statement.execute(query);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, DATABASE_ERROR + " " + query, e);
            }
        });
    }

    public static ResultSet executeStatement(final PreparedStatement preparedStatement) {
        final Future<ResultSet> submit = EXECUTOR_SERVICE.submit(() -> {
            ResultSet resultSet = null;
            try {
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
            }
            return resultSet;
        });
        try {
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
        }
        return null;
    }

    public static Connection getConnection() {
        try {
            return DataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
            return null;
        }
    }
}
