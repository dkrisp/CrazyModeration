package ca.krisp.crazymoderation.commands;

import ca.krisp.crazymoderation.CrazyModeration;
import net.risenteam.risencore.commands.RisenCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandSS extends RisenCommand {

    private final CrazyModeration crazyModeration;
    public CommandSS(CrazyModeration crazyModeration) {
        super("ss");
        this.setPermission("crazymoderation.ss");
        this.setPermissionMessage(ChatColor.RED + "You do not have permission to use this command!");
        this.crazyModeration = crazyModeration;
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {


    }
}
