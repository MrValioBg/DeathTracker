package me.mrvaliobg.mc.playerstatistics.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class DataSource {

    private DataSource() {
    }

    private static String databaseName;
    private static HikariDataSource dataSrc;
    private static HikariConfig config;

    public static void init(final String host, final String username, final String database, final String password, final String port) {
        if (config != null) {
            config.getScheduledExecutor().shutdown();
        }
        if (dataSrc != null) {
            dataSrc.close();
        }
        config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolName("PlayerStats-DB");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "252560");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSrc = new HikariDataSource(config);
        databaseName = database;
    }

    public static Connection getConnection() throws SQLException {
        return dataSrc.getConnection();
    }

    public static String getDatabaseName() {
        return databaseName;
    }
}