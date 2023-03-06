package ca.krisp.crazymoderation.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
public class Logger {


    private static final String SUCCESS = ChatColor.translateAlternateColorCodes('&', "&a[SUCCESS] &r");
    private static final String INFO = ChatColor.translateAlternateColorCodes('&', "&b[INFO] &r");
    private static final String WARN = ChatColor.translateAlternateColorCodes('&', "&6[WARN] &r");
    private static final String FAIL = ChatColor.translateAlternateColorCodes('&', "&c[FAIL] &r");
    private static final String ERROR = ChatColor.translateAlternateColorCodes('&', "&c[ERROR] &r");

    public static void success(String message){
        Bukkit.getConsoleSender().sendMessage(SUCCESS + message);
    }

    public static void log(String message){
        Bukkit.getConsoleSender().sendMessage(INFO + message);
    }

    public static void warn(String message){
        Bukkit.getConsoleSender().sendMessage(WARN + message);
    }

    public static void fail(String message){
        Bukkit.getConsoleSender().sendMessage(FAIL + message);
    }

    public static void error(String message){
        Bukkit.getConsoleSender().sendMessage(ERROR + message);
    }


}
