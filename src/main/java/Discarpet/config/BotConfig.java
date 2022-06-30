package Discarpet.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BotConfig {
    @SerializedName("bot_id")
    public String BOT_ID;
    @SerializedName("bot_token")
    public String BOT_TOKEN;
    @SerializedName("intents")
    public List<String> INTENTS;
    
    public static BotConfig getTutorialConfig() {
        BotConfig botConfig = new BotConfig();
        botConfig.BOT_ID = "Your bot ID";
        botConfig.BOT_TOKEN = "Your bot token";
        botConfig.INTENTS = List.of();
        return botConfig;
    }
}
