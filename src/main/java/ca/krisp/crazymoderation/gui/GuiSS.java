package ca.krisp.crazymoderation.gui;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.utils.Color;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GuiSS implements InventoryProvider {
    
    @Override
    public void init(Player player, InventoryContents contents) {
        //TODO: Add items to the inventory MultiVersions
    }

    @Override
    public void update(Player player, InventoryContents contents) {}
    
    public static SmartInventory getInventory(Player target){
        return SmartInventory.builder()
                .id("ss")
                .manager(CrazyModeration.getInventoryManager())
                .provider(new GuiSS())
                .size(3, 9)
                .title(Color.colorize("&c&lCrazyModeration &7- &c&lSS"))
                .build();
    }
    
}
