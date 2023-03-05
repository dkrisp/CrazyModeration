package ca.krisp.crazymoderation.task;

import ca.krisp.crazymoderation.managers.PlayerManager;

public class PlayerDataSavingTask implements Runnable {

    private final PlayerManager playerManager;

    public PlayerDataSavingTask(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void run() {
        this.playerManager.savePlayers();
    }
}
