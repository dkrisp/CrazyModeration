package ca.krisp.crazymoderation.commands;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.language.LanguageParser;
import ca.krisp.crazymoderation.player.CMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFreeze extends CrazyCommand {

    private final CrazyModeration crazyModeration;

    public CommandFreeze(CrazyModeration crazyModeration) {
        super("freeze");
        this.setPermission("crazymoderation.freeze");
        this.crazyModeration = crazyModeration;
    }

    @Override
    public void onCommand(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /freeze <player>");
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(LanguageParser.get("playerNotFound"));
            return;
        }

        if (player.hasPermission("crazymoderation.freeze.bypass")) {
            sender.sendMessage(LanguageParser.get("playerFreezeBypass"));
            return;
        }

        CMPlayer cmPlayer = this.crazyModeration.getPlayerManager().getPlayer(player);
        if (cmPlayer == null) {
            sender.sendMessage(LanguageParser.get("errorWhileProcessingCommand"));
            return;
        }

        if (cmPlayer.isFrozen()) {
            cmPlayer.setFrozen(false);
            sender.sendMessage(LanguageParser.get("playerUnfrozen").replace("{player}", player.getName()));
            player.sendMessage(LanguageParser.get("playerUnfrozenByPlayer").replace("{player}", sender.getName()));
        } else {
            cmPlayer.setFrozen(true);
            sender.sendMessage(LanguageParser.get("playerFreeze").replace("{player}", player.getName()));
            player.sendMessage(LanguageParser.get("playerFreezeBy").replace("{player}", sender.getName()));
        }

    }
}
