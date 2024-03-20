package com.hanabi.amzn;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class databace {
    private final String DB_name = "database.db";
    private Connection connection = null;
    private Statement statement = null;

    public databace() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.DB_name);
            this.statement = connection.createStatement();
            Bukkit.getLogger().info("データベースに接続しました");
        } catch (Exception e) {
            Bukkit.getLogger().info(e.toString());
        }
    }

    public void CreateTable() {
        try {
            this.statement.executeUpdate("create table amzndata(uuid text,name text,ItemID text,price integer,amount integer)");
            Bukkit.getLogger().info("ーーーーーーーーーーーーーーーー");
            Bukkit.getLogger().info("テーブルを作成しました");
            Bukkit.getLogger().info("ーーーーーーーーーーーーーーー");
        } catch (SQLException e) {
            Bukkit.getLogger().warning("ーーーーーーーーーーーーーーー");
            Bukkit.getLogger().warning(e.toString());
            Bukkit.getLogger().warning("ーーーーーーーーーーーーーーー");
        }
    }

    public void CloseConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            Bukkit.getLogger().warning("ーーーーーーーーーーーーーーー");
            Bukkit.getLogger().warning(e.toString());
            Bukkit.getLogger().warning("ーーーーーーーーーーーーーーー");
        }
    }

    public void amsellData(Player player,int price,int amount){
        try{
            this.statement = connection.createStatement();
            String uuid = player.getUniqueId().toString();
            String name = player.getDisplayName();
            String ItemID = player.getInventory().getItemInMainHand().getType().name();

            this.statement = connection.createStatement();
            this.statement.executeUpdate("insert into moneydata values('\" + uuid + \"', '\" + name + \"', '\" + ItemID + \"','\" + price + \"','\" + amount + \"',0)\"");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
