package ca.krisp.crazymoderation.managers;

import ca.krisp.crazymoderation.language.LanguageParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StaffChatManager {

    public void sendStaffMessage(Player sender, String message){
        String formattedChat = LanguageParser.getUnformatted("staffChatFormat").replace("{player}", sender.getName()).replace("{message}", message);
        Bukkit.getLogger().info("(StaffChat) " + formattedChat);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("crazymoderation.staffchat")) {
                onlinePlayer.sendMessage(formattedChat);
            }
        }
    }

}
