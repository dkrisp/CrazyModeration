package ca.krisp.crazymoderation.commands;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.language.LanguageParser;
import org.bukkit.command.CommandSender;

public class CommandCrazyModeration extends CrazyCommand{

    private final CrazyModeration crazyModeration;

    public CommandCrazyModeration(CrazyModeration crazyModeration) {
        super("crazymoderation", "cm");
        this.setPermission("crazymoderation.admin");
        this.crazyModeration = crazyModeration;
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if(args.length == 0){
            //TODO: HELP MENU
        }

        if(args[0].equalsIgnoreCase("reload")){
            this.crazyModeration.reloadConfig();
            LanguageParser.init(this.crazyModeration);
            sender.sendMessage(LanguageParser.get("pluginReloaded"));
            return;
        }

        if(args[0].equalsIgnoreCase("version")){
            sender.sendMessage(LanguageParser.get("pluginVersion").replace("{version}", this.crazyModeration.getDescription().getVersion()));
            return;
        }

        //TODO: HELP MENU
    }
}
