package ca.krisp.crazymoderation;

import ca.krisp.crazymoderation.commands.CommandFreeze;
import ca.krisp.crazymoderation.commands.CommandSS;
import ca.krisp.crazymoderation.language.LanguageParser;
import ca.krisp.crazymoderation.managers.PlayerManager;
import ca.krisp.crazymoderation.task.PlayerDataSavingTask;
import ca.krisp.crazymoderation.task.ServerAsyncTask;
import net.risenteam.risencore.RisenPlugin;
import net.risenteam.risencore.commands.CommandManager;
import net.risenteam.risencore.utils.Logger;
import org.bukkit.Bukkit;

public class CrazyModeration extends RisenPlugin {

    private final CommandManager commandManager;
    private final PlayerManager playerManager;

    private boolean debug;

    private int taskSaveID = -1;
    private int taskServerID = -1;

    public CrazyModeration() {
        commandManager = new CommandManager();
        this.playerManager = new PlayerManager(this);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        this.debug = this.getConfig().getBoolean("debug");

        this.commandManager.registerCommand(new CommandSS(this));
        this.commandManager.registerCommand(new CommandFreeze(this));

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

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        if(isDebug()) {
            Logger.log("Loading configuration file.");
            Logger.log("Config version: " + this.getConfig().getString("config-version", this.getDescription().getVersion()));
        }


        LanguageParser.init(this);

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
}
