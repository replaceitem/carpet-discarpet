package net.replaceitem.discarpet.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BotConfig {
    @SerializedName("bot_id")
    public String BOT_ID;
    @SerializedName("bot_token")
    public String BOT_TOKEN;
    @SerializedName("intents")
    public List<String> INTENTS;
    @SerializedName("member_cache_policy")
    public String MEMBER_CACHE_POLICY;

    public void fillDefaults() {
        if(BOT_ID == null) BOT_ID = "Your bot ID";
        if(BOT_TOKEN == null) BOT_TOKEN = "Your bot token";
        if(INTENTS == null) INTENTS = List.of();
        if(MEMBER_CACHE_POLICY == null) MEMBER_CACHE_POLICY = "all";
    }
}
