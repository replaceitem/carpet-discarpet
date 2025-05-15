package net.replaceitem.discarpet.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Config {
    @SerializedName("bots")
    public List<BotConfig> BOTS;
    
    public void fillDefaults() {
        if(BOTS == null) BOTS = List.of(new BotConfig());
        BOTS.forEach(BotConfig::fillDefaults);
    }
}