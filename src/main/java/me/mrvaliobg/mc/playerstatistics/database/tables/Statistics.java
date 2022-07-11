package me.mrvaliobg.mc.playerstatistics.database.tables;

import me.mrvaliobg.mc.playerstatistics.database.DataSource;
import me.mrvaliobg.mc.playerstatistics.database.Methods;
import me.mrvaliobg.mc.playerstatistics.logging.ClassLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.mrvaliobg.mc.playerstatistics.database.Methods.DATABASE_ERROR;
import static me.mrvaliobg.mc.playerstatistics.database.Methods.EXECUTOR_SERVICE;

public class Statistics {

    private static final Logger LOGGER = new ClassLogger(Statistics.class);
    private static final String SELECT = "SELECT <STAT> FROM " + DataSource.INSTANCE.getDatabaseName() + ".main_statistics WHERE player_uuid = ?;";
    private static final String UPDATE = "UPDATE " + DataSource.INSTANCE.getDatabaseName() + ".main_statistics SET <STAT> = ? WHERE player_uuid = ?;";

    private static int getInt(final String uuid, final String statisticsType) {
        int blocksPlaced = 0;
        ResultSet resultSet = null;
        try (Connection connection = Methods.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(replaceData(SELECT, statisticsType))) {
            preparedStatement.setString(1, uuid);
            resultSet = Methods.executeStatement(preparedStatement);
            if (resultSet != null && resultSet.next()) {
                blocksPlaced = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignored) {
                }
            }
        }
        return blocksPlaced;
    }

    private static void setInt(final String uuid, final int statisticsValue, final String statisticsType) {
        EXECUTOR_SERVICE.submit(() -> {
            try (Connection connection = Methods.getConnection(); PreparedStatement statement = connection.prepareStatement(replaceData(UPDATE, statisticsType))) {
                statement.setInt(1, statisticsValue);
                statement.setString(2, uuid);
                statement.execute();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
            }
        });
    }

    private static String replaceData(final String statement, final String data) {
        return statement.replace("<STAT>", data);
    }

}
