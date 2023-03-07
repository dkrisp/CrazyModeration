package ca.krisp.crazymoderation.commands;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.gui.GuiSS;
import org.bukkit.entity.Player;

public class CommandSS extends CrazyPlayerCommand {

    private final CrazyModeration crazyModeration;
    public CommandSS(CrazyModeration crazyModeration) {
        super("ss");
        this.setPermission("crazymoderation.ss");
        this.crazyModeration = crazyModeration;
    }

    @Override
    public void onCommand(Player player, String label, String[] args) {
    }
}
