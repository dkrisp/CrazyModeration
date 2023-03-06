package ca.krisp.crazymoderation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class CrazyPlayerCommand extends CrazyCommand {

    public CrazyPlayerCommand(String name, String ... aliases) {
        super(name, aliases);
    }

    public CrazyPlayerCommand(String name, String description, String usageMessage, String ... aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            this.onCommand((Player) sender, label, args);
            return;
        }

        sender.sendMessage("You must be a player to use this command!");
    }

    public abstract void onCommand(Player player, String label, String[] args);
}
