package com.hanabi.amzn;

import org.bukkit.plugin.java.JavaPlugin;

public final class Amzn extends JavaPlugin {
    databace databace = new databace();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("amznプラグインが起動しました");
        getCommand("amsell").setExecutor(new amsell());
        databace.CreateTable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        databace.CloseConnection();
    }
}

