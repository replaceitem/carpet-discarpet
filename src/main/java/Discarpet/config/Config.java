package Discarpet.config;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Config {
    @SerializedName("bots")
    public ArrayList<BotConfig> BOTS;
    @SerializedName("disable_reconnect_logs")
    public boolean DISABLE_RECONNECT_LOGS;

    public static Config getDefault() {
        Config config = new Config();
        config.BOTS = new ArrayList<>();
        config.BOTS.add(BotConfig.getTutorialConfig());
        config.DISABLE_RECONNECT_LOGS = false;
        return config;
    }
}