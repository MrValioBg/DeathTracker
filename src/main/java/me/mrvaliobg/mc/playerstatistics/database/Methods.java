package me.mrvaliobg.mc.playerstatistics.database;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public final class Methods {

    public static final String DATABASE_ERROR = "Database connection error occurred";
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool(10);

    private Methods() {

    }

    public static void executeQuery(final String query) {
        EXECUTOR_SERVICE.submit(() -> {
            try (Connection connection = getConnection();
                 Statement statement = connection != null ? connection.createStatement() : null) {
                if (connection != null) {
                    statement.execute(query);
                }
            } catch (SQLException e) {
                log.error(DATABASE_ERROR + " " + query, e);
            }
        });
    }

    public static ResultSet executeStatement(final PreparedStatement preparedStatement) {
        final Future<ResultSet> submit = EXECUTOR_SERVICE.submit(() -> {
            ResultSet resultSet = null;
            try {
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException e) {
                log.error(DATABASE_ERROR, e);
            }
            return resultSet;
        });
        try {
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(DATABASE_ERROR, e);
        }
        return null;
    }

    public static Connection getConnection() {
        try {
            return DataSource.getConnection();
        } catch (SQLException e) {
            log.error(DATABASE_ERROR, e);
            return null;
        }
    }
}
