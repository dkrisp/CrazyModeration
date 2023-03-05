package ca.krisp.crazymoderation.managers;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.language.LanguageParser;
import ca.krisp.crazymoderation.player.CMPlayer;
import net.risenteam.risencore.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerManager {

    private final Map<UUID, CMPlayer> players = new HashMap<>();
    private final CrazyModeration crazyModeration;

    public PlayerManager(CrazyModeration crazyModeration) {
        this.crazyModeration = crazyModeration;
    }

    public void addPlayer(Player player){
        players.put(player.getUniqueId(), new CMPlayer(player));
    }

    public void addPlayer(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) {
            Logger.error(LanguageParser.get("playerNotFound"));
            return;
        }
        players.put(uuid, new CMPlayer(Bukkit.getPlayer(uuid)));
    }

    public void removePlayer(Player player){
        savePlayer(player);

        players.remove(player.getUniqueId());
    }

    public void removePlayer(UUID uuid){
        CMPlayer cmPlayer = players.get(uuid);
        if(cmPlayer != null) savePlayer(cmPlayer);
        players.remove(uuid);
    }

    public CMPlayer getPlayer(Player player){
        return players.get(player.getUniqueId());
    }

    public CMPlayer getPlayer(UUID uuid){
        return players.get(uuid);
    }

    public boolean isRegistered(Player player){
        return players.containsKey(player.getUniqueId());
    }

    public boolean isRegistered(UUID uuid){
        return players.containsKey(uuid);
    }

    public Collection<CMPlayer> getPlayers(){
        return players.values();
    }

    public void loadPlayers(){
        //TODO: Load players from database
        for(Player player : Bukkit.getOnlinePlayers()){
            addPlayer(player);
        }
    }

    public void savePlayers(){
        for(CMPlayer player : players.values()){
            savePlayer(player);
        }
        Logger.success("Saved all players to database.");
    }

    public void savePlayer(CMPlayer player){
        //TODO: Save player to database
    }

    public void savePlayer(Player player){
        savePlayer(getPlayer(player));
    }

    public void unloadPlayers(){
        this.savePlayers();
        players.clear();
    }

}
