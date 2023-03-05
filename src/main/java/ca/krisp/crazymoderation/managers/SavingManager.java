package ca.krisp.crazymoderation.managers;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.player.CMPlayer;
import net.risenteam.risencore.utils.Logger;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SavingManager {

    private CrazyModeration crazyModeration;
    private Connection connection;
    private String connectUrl;
    private String username, password;

    private boolean mysql;

    public SavingManager(CrazyModeration crazyModeration) {
        this.crazyModeration = crazyModeration;
        updateUrl();
    }

    public void updateUrl() {
        if (this.crazyModeration.getConfig().getString("savingType").equalsIgnoreCase("mysql")) {
            mysql = true;
        }

        this.connectUrl = "jdbc:mysql://" + this.crazyModeration.getConfig().getString("mysql.host") + ":" + this.crazyModeration.getConfig().getInt("mysql.port") + "/" + this.crazyModeration.getConfig().getString("mysql.database");
        this.username = this.crazyModeration.getConfig().getString("mysql.username");
        this.password = this.crazyModeration.getConfig().getString("mysql.password");
    }

    public void save() {
        for (CMPlayer player : this.crazyModeration.getPlayerManager().getPlayers()) {
            savePlayer(player);
        }
    }


    //Create a method to connect to the database
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Load the driver

            //Create a connection to the database
            updateUrl();
            connection = DriverManager.getConnection(connectUrl, username, password);

            //Create tables if they don't exist
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `players` (`uuid` VARCHAR(50) NOT NULL, `data` LONGTEXT NOT NULL, PRIMARY KEY (`uuid`)) ENGINE = MyISAM").executeUpdate();
        } catch (SQLException e) {
            Logger.error("Could not connect to MySQL server! because: " + e.getMessage());
            if (this.crazyModeration.isDebug()) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            Logger.error("Could not find MySQL driver!");
            if (this.crazyModeration.isDebug()) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                Logger.error("Could not disconnect from MySQL server! because: " + e.getMessage());
                if (this.crazyModeration.isDebug()) e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        if (connection != null) {
            try {
                return !connection.isClosed();
            } catch (SQLException e) {
                Logger.error("Could not check if MySQL is connected! because: " + e.getMessage());
                if (this.crazyModeration.isDebug()) e.printStackTrace();
            }
        }
        return connection != null;
    }

    public void savePlayer(CMPlayer player) {
        if (mysql) {
            if (!isConnected()) {
                connect();
            }

            try {
                connection.prepareStatement("INSERT INTO `players` (`uuid`, `data`) VALUES ('" + player.getUniqueId().toString() + "', '" + this.crazyModeration.getGson().toJson(player, CMPlayer.class) + "') ON DUPLICATE KEY UPDATE `data` = '" + this.crazyModeration.getGson().toJson(player, CMPlayer.class) + "'").executeUpdate();
            } catch (SQLException e) {
                Logger.error("Could not save player " + player.getUniqueId() + " to MySQL server! because: " + e.getMessage());
                if (this.crazyModeration.isDebug()) e.printStackTrace();
            }finally {
                disconnect();
            }
        } else {
            File dataFolder = new File(this.crazyModeration.getDataFolder(), "data");
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File file = new File(dataFolder, player.getUniqueId().toString() + ".json");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(this.crazyModeration.getGson().toJson(player, CMPlayer.class));
            } catch (Exception e) {
                Logger.error("Could not save player " + player.getUniqueId() + " to file! because: " + e.getMessage());
                if (this.crazyModeration.isDebug()) e.printStackTrace();
            }

        }
    }
}
