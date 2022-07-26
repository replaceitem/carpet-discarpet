package Discarpet.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Config {
    @SerializedName("bots")
    public List<BotConfig> BOTS;
    @SerializedName("disable_reconnect_logs")
    public Boolean DISABLE_RECONNECT_LOGS;
    
    public void fillDefaults() {
        if(BOTS == null) BOTS = List.of(new BotConfig());
        if(DISABLE_RECONNECT_LOGS == null) DISABLE_RECONNECT_LOGS = false; 
        BOTS.forEach(BotConfig::fillDefaults);
    }
}