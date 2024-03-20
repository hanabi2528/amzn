package com.hanabi.amzn;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public final class Amzn extends JavaPlugin {
    private Connection connection;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("amznプラグインが起動しました");
        connectToDatabase();
        createShopTable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void connectToDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/ShopPlugin/shop.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // ショップのテーブルを作成
    private void createShopTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS shops (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "owner_uuid VARCHAR(36) NOT NULL," +
                    "item_id VARCHAR(64) NOT NULL," +
                    "price INTEGER NOT NULL)" +
                    "amount INTEGER NOT NULL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("amsell")) {
            Player player = (Player) sender;
            if(args.length == 0){
                sender.sendMessage(ChatColor.YELLOW + "/amsell <価格> <個数>でアイテムを出品します");
                return false;
            }
            if (args.length == 1){
                sender.sendMessage(ChatColor.RED + "個数を設定してください");
                return false;
            }

            if(args.length == 2){
                int price = Integer.parseInt(args[0]);
                int amount = Integer.parseInt(args[1]);
                if(price <= 0){
                    sender.sendMessage(ChatColor.RED + "価格は1円以上にしてください");
                    return false;
                }
                if (amount <= 0){
                    sender.sendMessage(ChatColor.RED + "個数は正の整数にしてください");
                }
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                int amountInHand = itemStack.getAmount();
                if(itemStack.getType() == Material.AIR){
                    sender.sendMessage(ChatColor.RED + "アイテムを手に持ってください");
                    return false;
                }
                if(amountInHand < amount){
                    sender.sendMessage(ChatColor.RED + "アイテムの数が足りません！");
                    return false;
                }

                String itemID = itemStack.getType().toString();
                String ownerUUID = player.getUniqueId().toString();

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO shops (owner_uuid, item_id, price) VALUES (?, ?, ?)")) {
                    statement.setString(1, ownerUUID);
                    statement.setString(2, itemID);
                    statement.setInt(3, price);
                    statement.setInt(4, amount);
                    statement.executeUpdate();
                    player.sendMessage("アイテムが出品されました！");
                    itemStack.setAmount(amount);
                    player.getInventory().removeItem(itemStack);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return true;

            }
        }
        return false;
    }

}

