package me.mrvaliobg.mc.playerstatistics.database.tables;

import me.mrvaliobg.mc.playerstatistics.database.source.DataSource;
import me.mrvaliobg.mc.playerstatistics.database.Methods;
import me.mrvaliobg.mc.playerstatistics.logging.ClassLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static me.mrvaliobg.mc.playerstatistics.database.Methods.DATABASE_ERROR;
import static me.mrvaliobg.mc.playerstatistics.database.Methods.EXECUTOR_SERVICE;

public final class Statistics {

    private static final Logger LOGGER = new ClassLogger(Statistics.class);
    private static final String SELECT = "SELECT <STAT> FROM " + DataSource.INSTANCE.getDatabaseName() + ".main_statistics WHERE player_uuid = ?;";
    private static final String SELECT_ALL = "SELECT player_uuid FROM " + DataSource.INSTANCE.getDatabaseName() + ".main_statistics;";
    private static final String UPDATE = "UPDATE " + DataSource.INSTANCE.getDatabaseName() + ".main_statistics SET <STAT> = ? WHERE player_uuid = ?;";
    private static final String INSERT = "INSERT INTO " + DataSource.INSTANCE.getDatabaseName() + ".main_statistics SET player_uuid = '?', `<STAT>` = '?';";

    private Statistics() {
    }

    public static int getStatisticsIntData(final String uuid, final String statisticsType) {
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

    public static void setStatisticsIntData(final String uuid, final int statisticsValue, final String statisticsType) {
        if (!contains(uuid, statisticsType)) {
            insertPlayer(uuid, statisticsType, statisticsValue);
        }
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

    private static boolean contains(final String uuid, final String statisticsType) {
        boolean contains = false;
        ResultSet resultSet = null;
        try (Connection connection = Methods.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(replaceData(SELECT, statisticsType))) {
            preparedStatement.setString(1, uuid);
            resultSet = Methods.executeStatement(preparedStatement);
            contains = resultSet != null && resultSet.next();
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
        return contains;
    }

    public static Map<String, Integer> getAllDataForStat(final String statisticsType) {
        final Map<String, Integer> statisticsData = new HashMap<>();
        try (Connection connection = Methods.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(replaceData(SELECT_ALL, statisticsType));
             ResultSet resultSet = Methods.executeStatement(preparedStatement)) {
            if (resultSet != null) {
                while (resultSet.next()) {
                    final String uuidPlayer = resultSet.getString(1);
                    statisticsData.put(uuidPlayer, getStatisticsIntData(uuidPlayer, statisticsType));
                }

            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, DATABASE_ERROR, e);
        }
        return statisticsData;
    }

    private static void insertPlayer(final String uuid, final String statisticsType, final int statisticValue) {
        String query = replaceData(INSERT.replaceFirst(Pattern.quote("?"), uuid).replaceFirst(Pattern.quote("?"), String.valueOf(statisticValue)), statisticsType);
        Methods.executeQuery(query);
        System.out.println("" + INSERT);
    }

    private static String replaceData(final String statement, final String data) {
        return statement.replace("<STAT>", data);
    }

}
