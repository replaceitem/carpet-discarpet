package Discarpet;

import com.google.gson.annotations.SerializedName;

public class BotConfig {
    @SerializedName("bot_id")
    public final String BOT_ID;
    @SerializedName("bot_token")
    public final String BOT_TOKEN;

    public BotConfig(String id, String token) {
        BOT_ID = id;
        BOT_TOKEN = token;
    }
}
