package ca.krisp.crazymoderation;

import ca.krisp.crazymoderation.commands.CommandCrazyModeration;
import ca.krisp.crazymoderation.commands.CommandFreeze;
import ca.krisp.crazymoderation.commands.CommandManager;
import ca.krisp.crazymoderation.commands.CommandSS;
import ca.krisp.crazymoderation.language.LanguageParser;
import ca.krisp.crazymoderation.managers.PlayerManager;
import ca.krisp.crazymoderation.managers.StaffChatManager;
import ca.krisp.crazymoderation.task.PlayerDataSavingTask;
import ca.krisp.crazymoderation.task.ServerAsyncTask;
import ca.krisp.crazymoderation.utils.Logger;
import com.google.gson.Gson;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStream;
import java.io.InputStreamReader;

public class CrazyModeration extends JavaPlugin {

    private final Gson gson = new Gson();
    private final CommandManager commandManager;
    private final PlayerManager playerManager;
    private final StaffChatManager staffChatManager;

    private boolean debug;

    private int taskSaveID = -1;
    private int taskServerID = -1;

    public CrazyModeration() {
        this.commandManager = new CommandManager(this);
        this.playerManager = new PlayerManager(this);
        this.staffChatManager = new StaffChatManager();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        this.saveResource("languages/en.json", false);

        LanguageParser.init(this);

        this.debug = this.getConfig().getBoolean("debug");
        if(isDebug()) Logger.log("Debug mode is enabled.");


        if(isDebug()) Logger.log("Loading inventory manager.");
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();
        if(isDebug()) Logger.log("Inventory manager loaded.");


        this.commandManager.registerCommand(new CommandSS(this));
        this.commandManager.registerCommand(new CommandFreeze(this));
        this.commandManager.registerCommand(new CommandCrazyModeration(this));

        this.playerManager.loadPlayers();

        taskServerID = Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ServerAsyncTask(this.playerManager), 20L, 20L).getTaskId();

        if(isDebug()) Logger.success("CrazyModeration has been enabled!");
    }

    @Override
    public void onDisable() {
        this.playerManager.unloadPlayers();
        if (Bukkit.getScheduler().isCurrentlyRunning(taskSaveID)) {
            Bukkit.getScheduler().cancelTask(taskSaveID);
        }

        if(Bukkit.getScheduler().isCurrentlyRunning(taskServerID)){
            Bukkit.getScheduler().cancelTask(taskServerID);
        }

        if(isDebug()) Logger.success("CrazyModeration has been disabled!");
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public StaffChatManager getStaffChatManager() {
        return staffChatManager;
    }

    @Override
    public void saveDefaultConfig() {
        super.saveDefaultConfig();

        if(isDebug()) Logger.log("Saving default configuration file.");
        String currentVersion = this.getConfig().getString("config-version", "null");

        if(currentVersion.equalsIgnoreCase(this.getDescription().getVersion())){
            if(isDebug()) Logger.log("Configuration file is up to date.");
            return;
        }

        if(isDebug()) Logger.log("Configuration file is outdated. Updating...");

        InputStream resource = this.getResource("config.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(resource));

        //Copy new key from default config
        for(String key : yamlConfiguration.getKeys(true)){
            if(!this.getConfig().contains(key)){
                this.getConfig().set(key, yamlConfiguration.get(key));
            }
        }

        this.saveConfig();
        if(isDebug()) Logger.success("Configuration file successfully updated.");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        if(isDebug()) {
            Logger.log("Loading configuration file.");
            Logger.log("Config version: " + this.getConfig().getString("config-version", this.getDescription().getVersion()));
        }

        boolean preventCrash = this.getConfig().getBoolean("preventCrash");
        long preventCrashInterval = Math.max(this.getConfig().getLong("preventCrashInterval"), 60) * 20L;

        if(preventCrash){
            if(taskSaveID == -1 || !Bukkit.getScheduler().isCurrentlyRunning(taskSaveID)){
                taskSaveID = Bukkit.getScheduler().runTaskTimerAsynchronously(this, new PlayerDataSavingTask(this.playerManager), preventCrashInterval, preventCrashInterval).getTaskId();
            }
        }else {
            if(Bukkit.getScheduler().isCurrentlyRunning(taskSaveID)){
                Bukkit.getScheduler().cancelTask(taskSaveID);
            }
        }

        if(isDebug()) Logger.success("Configuration file successfully loaded.");
    }


    public boolean isDebug() {
        return debug;
    }

    public Gson getGson() {
        return gson;
    }

    private static InventoryManager inventoryManager;

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
