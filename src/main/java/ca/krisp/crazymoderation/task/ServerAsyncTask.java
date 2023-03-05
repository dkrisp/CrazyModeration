package ca.krisp.crazymoderation.task;

import ca.krisp.crazymoderation.language.LanguageParser;
import ca.krisp.crazymoderation.managers.PlayerManager;
import ca.krisp.crazymoderation.player.CMPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerAsyncTask implements Runnable{

    private final PlayerManager playerManager;

    public ServerAsyncTask(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void run() {
        for (CMPlayer cmPlayer : this.playerManager.getPlayers()) {
            if (cmPlayer.isFrozen()) {
                Player player = cmPlayer.getPlayer();
                player.sendMessage(LanguageParser.get("playerFreezeMessage"));
            }
        }
    }
}
