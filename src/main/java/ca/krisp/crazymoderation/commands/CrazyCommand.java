package ca.krisp.crazymoderation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public abstract class CrazyCommand extends Command {

    public CrazyCommand(String name, String ... aliases) {
        super(name, "", "/" + name, Arrays.asList(aliases));
    }

    public CrazyCommand(String name, String description, String usageMessage, String ... aliases) {
        super(name, description, usageMessage, Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(!testPermission(commandSender)) {
            return false;
        }

        onCommand(commandSender, s, strings);
        return false;
    }

    public abstract void onCommand(CommandSender sender, String label, String[] args);
}
