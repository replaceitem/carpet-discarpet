package Discarpet;

import com.google.gson.annotations.SerializedName;

public class BotConfig {
    @SerializedName("bot_id")
    public final String BOT_ID;
    @SerializedName("bot_token")
    public final String BOT_TOKEN;
    @SerializedName("member_intent")
    public final boolean MEMBER_INTENT;
    @SerializedName("presence_intent")
    public final boolean PRESENCE_INTENT;

    public BotConfig(String id, String token, boolean member, boolean presence) {
        BOT_ID = id;
        BOT_TOKEN = token;
        MEMBER_INTENT = member;
        PRESENCE_INTENT = presence;
    }
}
