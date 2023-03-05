package ca.krisp.crazymoderation.listeners;

import ca.krisp.crazymoderation.CrazyModeration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    private final CrazyModeration crazyModeration;

    public PlayerListener(CrazyModeration crazyModeration){
        this.crazyModeration = crazyModeration;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!crazyModeration.getPlayerManager().isRegistered(player)){
            crazyModeration.getPlayerManager().addPlayer(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(crazyModeration.getPlayerManager().isRegistered(player)){
            crazyModeration.getPlayerManager().removePlayer(player);
        }
    }


    //handle freeze todo: optimisation
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(crazyModeration.getPlayerManager().isRegistered(player)){
            if(crazyModeration.getPlayerManager().getPlayer(player).isFrozen()){
                event.setTo(event.getFrom());
            }
        }
    }
}
