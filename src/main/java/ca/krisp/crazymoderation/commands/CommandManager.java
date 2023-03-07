package ca.krisp.crazymoderation.commands;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

public class CommandManager {

    private SimpleCommandMap commandMap;
    public CommandManager(CrazyModeration plugin) {
        SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getPluginManager();
        try{
            Field commandMap = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            this.commandMap = (SimpleCommandMap) commandMap.get(pluginManager);
            commandMap.setAccessible(false);

            if(plugin.isDebug()) Logger.success("CommandManager has been initialized!");
        }catch (Exception e){
            Logger.fail("Failed to initialize CommandManager!");

            if(plugin.isDebug()) e.printStackTrace();
        }
    }

    public void registerCommand(CrazyCommand command){
        commandMap.register("crazymoderation", command);
    }
}
