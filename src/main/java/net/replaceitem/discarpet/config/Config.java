package net.replaceitem.discarpet.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Config {
    @SerializedName("bots")
    public List<BotConfig> BOTS;
    @SerializedName("connect_timeout")
    public Integer CONNECT_TIMEOUT;
    
    public void fillDefaults() {
        if(BOTS == null) BOTS = List.of(new BotConfig());
        BOTS.forEach(BotConfig::fillDefaults);
        if(CONNECT_TIMEOUT == null) CONNECT_TIMEOUT = 10;
    }
}