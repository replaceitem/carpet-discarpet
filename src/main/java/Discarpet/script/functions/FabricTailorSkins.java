package Discarpet.script.functions;

import carpet.script.annotation.AnnotationParser;
import carpet.script.annotation.ScarpetFunction;
import com.google.gson.JsonParser;
import net.minecraft.server.network.ServerPlayerEntity;
import org.samo_lego.fabrictailor.casts.TailoredPlayer;

import java.util.Base64;

public class FabricTailorSkins {

    public static final String STEVE = "MHF_STEVE";

    public static void init() {
        AnnotationParser.parseFunctionClass(FabricTailorSkins.class);
    }


    /**
     * Helper function to get texture hash from skin
     * that was set with FabricTailor mod.
     * <p>
     * Can be used with <a href="https://mc-heads.net/avatar/{textureid}">https://mc-heads.net/avatar/%7Btextureid%7D</a>
     * to get the head texture.
     * </p>
     *
     * @param player Player to get skin for
     * @return player's skin id (hash)
     */
    @ScarpetFunction
    public String dc_get_fabrictailor_skin_id(ServerPlayerEntity player) {
        String skin = ((TailoredPlayer) player).getSkinValue();

        if (skin == null) {
            // Fallback to default skin
            var textures = player.getGameProfile().getProperties().get("textures").stream().findAny();

            if (textures.isPresent()) {
                skin = textures.get().getValue();
            } else {
                return STEVE;
            }
        }

        // Parse base64 skin
        String decoded = new String(Base64.getDecoder().decode(skin));

        // Parse as json, then get textures -> SKIN -> url value
        String url = JsonParser.parseString(decoded)
                .getAsJsonObject()
                .getAsJsonObject("textures")
                .getAsJsonObject("SKIN")
                .getAsJsonPrimitive("url")
                .getAsString();

        // Extract id from url
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
