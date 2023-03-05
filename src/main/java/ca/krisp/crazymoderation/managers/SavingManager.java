package ca.krisp.crazymoderation.managers;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.player.CMPlayer;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class SavingManager {

    private CrazyModeration crazyModeration;

    public SavingManager(CrazyModeration crazyModeration){
        this.crazyModeration = crazyModeration;
    }

    public void save(){
        boolean mysql = false;
        if (this.crazyModeration.getConfig().getString("savingType").equalsIgnoreCase("mysql")) {
            mysql = true;
        }

        if(mysql){
            //todo: connect
        }else {
            File dataFolder = new File(this.crazyModeration.getDataFolder(), "data");
            if(!dataFolder.exists()){
                dataFolder.mkdir();
            }

            for (CMPlayer player : this.crazyModeration.getPlayerManager().getPlayers()) {
                File file = new File(dataFolder, player.getUniqueId().toString() + ".json");

                if(!file.exists()){
                    try{
                        file.createNewFile();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                try(FileWriter writer = new FileWriter(file)){
                    writer.write(this.crazyModeration.getGson().toJson(player, CMPlayer.class));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
