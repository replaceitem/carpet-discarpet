package Discarpet.config;

import Discarpet.Discarpet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected Config config;
    protected final File configFile;

    public ConfigManager(File configFile) {
        this.configFile = configFile;
    }
    
    public boolean loadAndUpdate() {
        boolean newlyCreated = this.load();
        this.config.fillDefaults();
        this.save();
        return newlyCreated;
    }
    
    public Config getConfig() {
        return config;
    }
    
    public boolean load() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            config = GSON.fromJson(reader, Config.class);
            return false;
        } catch (IOException e) {
            config = new Config();
            return true;
        }
    }
    
    public void save() {
        String jsonConfig = GSON.toJson(config);        
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(jsonConfig);
            writer.flush();
        } catch (IOException e) {
            Discarpet.LOGGER.error("Could not save config", e);
        }
    }
}
