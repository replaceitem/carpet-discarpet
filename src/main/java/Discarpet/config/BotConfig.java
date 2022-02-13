package Discarpet.config;

import com.google.gson.annotations.SerializedName;

public class BotConfig {
    @SerializedName("bot_id")
    public String BOT_ID;
    @SerializedName("bot_token")
    public String BOT_TOKEN;
    @SerializedName("member_intent")
    public boolean MEMBER_INTENT;
    @SerializedName("presence_intent")
    public boolean PRESENCE_INTENT;
    
    public static BotConfig getTutorialConfig() {
        BotConfig botConfig = new BotConfig();
        botConfig.BOT_ID = "Your bot ID";
        botConfig.BOT_TOKEN = "Your bot token";
        botConfig.MEMBER_INTENT = false;
        botConfig.PRESENCE_INTENT = false;
        return botConfig;
    }
}
