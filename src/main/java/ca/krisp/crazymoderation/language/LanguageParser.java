package ca.krisp.crazymoderation.language;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.utils.Color;
import net.risenteam.risencore.utils.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LanguageParser {

    private static JSONObject obj;
    private static String prefix;

    public static void init(CrazyModeration moderation) {
        prefix = Color.colorize(moderation.getConfig().getString("prefix"));
        String language = moderation.getConfig().getString("language");

        File file = new File(moderation.getDataFolder(), "languages/" + language + ".json");

        if(!file.exists()){
            Logger.fail("Language file does not exist!");
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            StringBuilder builder = new StringBuilder();
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
            obj = (JSONObject) new JSONParser().parse(builder.toString());
            Logger.success("Language file loaded for language : " + language);
        }catch (ParseException e){
            Logger.fail("Failed to parse JSON!");
            e.printStackTrace();
        }catch (Exception e){
            Logger.fail("Failed to read language file.");
        }
    }

    public static String get(String message){
        if(obj == null)
            return "Language file not loaded!";
        return prefix + " " + Color.colorize((String) obj.get(message));
    }

    public static String getUnformatted(String message){
        if(obj == null)
            return "Language file not loaded!";
        return Color.colorize((String) obj.get(message));
    }
}
