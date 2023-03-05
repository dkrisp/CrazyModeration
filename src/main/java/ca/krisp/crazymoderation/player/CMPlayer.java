package ca.krisp.crazymoderation.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CMPlayer {

    private UUID uuid;
    private boolean frozen;

    public CMPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.frozen = false;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public UUID getUniqueId() {
        return uuid;
    }


    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }
}
