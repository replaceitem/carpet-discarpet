package scarpet.discord;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class Chat {
	public List<chat> chatBuffer = new ArrayList<>();

	public void onMinecraftChat(Text text) {
		if(text instanceof TranslatableText) {
			String key = ((TranslatableText) text).getKey();
			String message = text.getString();
			chatBuffer.add(new chat(message, key));
		} else if(text instanceof LiteralText) {
			String key = text.getClass().getName();
			String message = ((LiteralText)text).getString();
			chatBuffer.add(new chat(message, key));
		}
	}

	public chat readChatBuffer() {
		chat cht;
		if(chatBuffer.size() > 0) {
			cht = chatBuffer.get(0);
			chatBuffer.remove(0);
			return cht;
		} else {
			return null;
		}
	}

	public class chat {
		public String text;
		public String key;
		chat(String text, String key) {
			this.text = text;
			this.key = key;
		}
	}
}
