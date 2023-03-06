package ca.krisp.crazymoderation.language;

import ca.krisp.crazymoderation.CrazyModeration;
import ca.krisp.crazymoderation.utils.Color;
import ca.krisp.crazymoderation.utils.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class LanguageParser {

    private static JSONObject obj;
    private static String prefix;

    public static void init(CrazyModeration moderation) {
        //TODO: Update languages files depending on config version.

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
            //read the resource file
            //get default file to copy
            InputStream resource = moderation.getResource("languages/" + language + ".json");
            if(resource == null){
                Logger.fail("Language file does not exist in the jar!");
                resource = moderation.getResource("languages/en.json");
                return;
            }

            //read the default file
            try(BufferedReader defaultReader = new BufferedReader(new InputStreamReader(resource))){
                StringBuilder defaultBuilder = new StringBuilder();
                while (defaultReader.ready()) {
                    defaultBuilder.append(defaultReader.readLine());
                }
                JSONObject defaultObj = (JSONObject) new JSONParser().parse(defaultBuilder.toString());
                //compare the two files
                for(Object key : defaultObj.keySet()){
                    if(!obj.containsKey(key)){
                        Logger.fail("Language file is missing key: " + key);
                        Logger.warn("Implementing key in language file.");
                        obj.put(key, defaultObj.get(key));
                    }
                }
                //save the file
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
                    writer.write(obj.toJSONString());
                    Logger.success("Language file updated!");
                }catch (IOException e){
                    Logger.fail("Failed to save language file!");
                    e.printStackTrace();
                }
            }
        }catch (ParseException e){
            Logger.fail("Failed to parse JSON!");
            e.printStackTrace();
        }catch (Exception e){
            Logger.fail("Failed to read language file.");
        }
    }

    public static String get(String key){
        if(obj == null)
            return "Language file not loaded!";
        Object objKey = obj.get(key);
        if(objKey == null){
            return "Language key not found! Please report this to the admin. Key: " + key + "";
        }
        String message = (String) objKey;
        return prefix + " " + Color.colorize(message);
    }

    public static String getUnformatted(String message){
        if(obj == null)
            return "Language file not loaded!";
        return Color.colorize((String) obj.get(message));
    }
}
