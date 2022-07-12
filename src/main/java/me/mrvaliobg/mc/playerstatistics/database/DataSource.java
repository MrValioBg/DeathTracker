package me.mrvaliobg.mc.playerstatistics.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private HikariConfig config;
    private HikariDataSource dataSrc;

    private final String databaseName;

    public DataSource(final String host, final String username, final String database, final String password, final String port) {
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
        this.databaseName = database;
    }

    public Connection getConnection() throws SQLException {
        return dataSrc.getConnection();
    }

    public String getDatabaseName() {
        return this.databaseName;
    }
}