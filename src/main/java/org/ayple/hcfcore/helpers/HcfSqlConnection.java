package org.ayple.hcfcore.helpers;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class HcfSqlConnection {
    static String DB_NAME = ConfigHelper.getConfig().getString("DB_NAME");
    static String DB_HOST = ConfigHelper.getConfig().getString("DB_HOST");
    static String DB_PORT = ConfigHelper.getConfig().getString("DB_PORT");
    static String DB_PASS = ConfigHelper.getConfig().getString("DB_PASS");
    static String DB_USER = ConfigHelper.getConfig().getString("DB_USER");
//    static String url = "jdbc:sqlite:" + Hcfcore.getInstance().getDataFolder().getAbsolutePath() + "/" + DB_NAME;
    String url = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false&characterEncoding=utf8";
    Connection connection;

    public HcfSqlConnection() throws SQLException {



//        File file = new File(Hcfcore.getInstance().getDataFolder().getAbsolutePath(), DB_NAME);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                Hcfcore.getInstance().getLogger().log(Level.SEVERE, "File write error! (creating database)");
//            }
//        }

//        System.out.println(file.getAbsolutePath());
        try {
            this.connection = DriverManager.getConnection(url, DB_USER, DB_PASS);
//            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
            Hcfcore.getInstance().getLogger().log(Level.SEVERE, "FAILED TO CONNECT TO DB!");
            throw e;
        }
//        this.getConnection().createStatement().execute("USE `hcf`;");


    }

    public static String getFactionsTableSQL() {
        return "CREATE TABLE IF NOT EXISTS `factions` (\n"
                + "`id` VARCHAR(100) NOT NULL UNIQUE ,\n"
                + "`faction_name` VARCHAR(20) NOT NULL UNIQUE,\n"
                + "`dtr` FLOAT NOT NULL DEFAULT 1.01,\n"
                + "`ally_faction_id` VARCHAR(100),\n"
                + "`faction_bal` INT NOT NULL DEFAULT 0,\n"
                + "`faction_hq_x` INT,\n"
                + "`faction_hq_y` INT,\n"
                + "`faction_hq_z` INT,\n"
                + "`corner_1_x` INT,\n"
                + "`corner_1_z` INT,\n"
                + "`corner_2_x` INT,\n"
                + "`corner_2_z` INT,\n"
                + "`datetime_created` TIMESTAMP DEFAULT NOW() NOT NULL);";
    }

    public static String getPlayerDataTableSQL() {
        return "CREATE TABLE IF NOT EXISTS `player_data` (\n"
                + "`player_id` VARCHAR(100) NOT NULL UNIQUE,\n"
                + "`lives` INT NOT NULL DEFAULT 0,\n"
                + "`kills` INT NOT NULL DEFAULT 0);";
    }

    public static String getFactionMembersTableSQL() {
        return "CREATE TABLE IF NOT EXISTS `faction_members` (\n"
                + "`faction_id` VARCHAR(100) NOT NULL,\n"
                + "`player_uuid` VARCHAR(100) NOT NULL UNIQUE,\n"
                + "`rank` SMALLINT NOT NULL DEFAULT 0,\n"
                + "FOREIGN KEY (`faction_id`) REFERENCES factions(`id`),\n"
                + "FOREIGN KEY (`player_uuid`) REFERENCES player_data(`player_id`));";
    }

    public static void createDatabase() throws SQLException {
        return;
//        System.out.println(getFactionsTableSQL());
//        System.out.println(getPlayerDataTableSQL());
//        System.out.println(getFactionMembersTableSQL());
//
//        HcfSqlConnection c = new HcfSqlConnection();
//        Connection conn = c.getConnection();
////        conn.createStatement().execute("CREATE DATABASE IF NOT EXISTS `hcf`;");
//        try {
//            conn.createStatement().execute(getFactionsTableSQL());
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Problem creating factions");
//
//        }
//        try {
//            conn.createStatement().execute(getPlayerDataTableSQL());
//        } catch (SQLException e) {
//            System.out.println("Problem creating player data");
//        }
//        try {
//            conn.createStatement().execute(getFactionMembersTableSQL());
//        } catch (SQLException e) {
//            System.out.println("Problem creating faction members");
//
//        }
//
//        conn.close();
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public Connection getConnection() {
        return this.connection;
    }
}
