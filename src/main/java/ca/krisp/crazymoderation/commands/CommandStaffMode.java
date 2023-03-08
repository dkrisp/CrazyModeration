package ca.krisp.crazymoderation.commands;

import org.bukkit.entity.Player;

public class CommandStaffMode extends CrazyPlayerCommand{


    public CommandStaffMode() {
        super("staffmode", "sm");
        this.setPermission("crazymoderation.staffmode");

    }

    @Override
    public void onCommand(Player player, String label, String[] args) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        //TODO: STAFFMODE ITEMS


    }
}
