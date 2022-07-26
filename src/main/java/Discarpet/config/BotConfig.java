package Discarpet.config;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BotConfig {
    @SerializedName("bot_id")
    public String BOT_ID;
    @SerializedName("bot_token")
    public String BOT_TOKEN;
    @SerializedName("intents")
    public List<String> INTENTS;

    public void fillDefaults() {
        if(BOT_ID == null) BOT_ID = "Your bot ID";
        if(BOT_TOKEN == null) BOT_TOKEN = "Your bot token";
        if(INTENTS == null) INTENTS = List.of();
    }
}
