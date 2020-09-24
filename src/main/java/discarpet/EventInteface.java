package discarpet;

import net.minecraft.text.Text;

public interface EventInteface {
    public void onChatMessage(Text text);
    public void onDiscordMessage(String content, String author, String channel);
    //public void onDiscordMessage()
}
