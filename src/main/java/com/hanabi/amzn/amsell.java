package com.hanabi.amzn;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class amsell implements CommandExecutor {
    databace databace = new databace();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("amsell")) {
            Player player = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.YELLOW + "/amsell <価格> <個数>でアイテムを出品します");
                return false;
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "個数を設定してください");
                return false;
            }

            if (args.length == 2) {
                int price = Integer.parseInt(args[0]);
                int amount = Integer.parseInt(args[1]);
                if (price <= 0) {
                    sender.sendMessage(ChatColor.RED + "価格は1円以上にしてください");
                    return false;
                }
                if (amount <= 0) {
                    sender.sendMessage(ChatColor.RED + "個数は正の整数にしてください");
                }
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                int amountInHand = itemStack.getAmount();
                if (itemStack.getType() == Material.AIR) {
                    sender.sendMessage(ChatColor.RED + "アイテムを手に持ってください");
                    return false;
                }
                if (amountInHand < amount) {
                    sender.sendMessage(ChatColor.RED + "アイテムの数が足りません！");
                    return false;
                }
                return false;
            }
        }
        return false;
    }
}

