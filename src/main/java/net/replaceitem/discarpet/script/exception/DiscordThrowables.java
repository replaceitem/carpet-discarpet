package net.replaceitem.discarpet.script.exception;

import carpet.script.api.Auxiliary;
import carpet.script.exception.ThrowStatement;
import carpet.script.exception.Throwables;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.google.gson.JsonParseException;
import net.replaceitem.discarpet.Discarpet;
import org.javacord.api.exception.*;
import org.javacord.api.util.rest.RestRequestResponseInformation;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static carpet.script.exception.Throwables.THROWN_EXCEPTION_TYPE;
import static carpet.script.exception.Throwables.register;

public class DiscordThrowables {
    public static final Throwables DISCORD_EXCEPTION = register("discord_exception", THROWN_EXCEPTION_TYPE);
    
    public static final Throwables API_EXCEPTION = register("api_exception", DISCORD_EXCEPTION);
    
    public static final Throwables MISSING_PERMISSION = register("missing_permission", API_EXCEPTION);
    public static final Throwables RATE_LIMIT = register("rate_limit", API_EXCEPTION);
    public static final Throwables BAD_REQUEST = register("bad_request", API_EXCEPTION);
    
    public static final Throwables MISSING_INTENT = register("missing_intent", DISCORD_EXCEPTION);
    
    public static ThrowStatement convert(Throwable exception, String message) {
        if(exception instanceof ExecutionException executionException && executionException.getCause() != null) return convert(executionException.getCause(), message);
        if(exception instanceof DiscordException discordException) {
            Value value = discordException.getResponse().<Value>map(DiscordThrowables::createErrorMap).orElse(Value.NULL);

            Throwables throwable = switch (exception) {
                case MissingPermissionsException ignored -> MISSING_PERMISSION;
                case RatelimitException ignored -> RATE_LIMIT;
                case BadRequestException ignored -> BAD_REQUEST;
                default -> API_EXCEPTION;
            };
            return new ThrowStatement(value, throwable);
        }
        if(exception instanceof MissingIntentException missingIntentException) {
            return new ThrowStatement(missingIntentException.getMessage(), MISSING_INTENT);
        }
        Discarpet.LOGGER.error("Could now convert exception type {} to discarpet exception ({}):", exception.getClass().getSimpleName(), message, exception);
        return new ThrowStatement(exception.getMessage(), DISCORD_EXCEPTION);
    }
    
    public static ThrowStatement genericCode(int code) {
        return new ThrowStatement(createSimpleError(code), DISCORD_EXCEPTION);
    }

    private static MapValue createSimpleError(int code) {
        Map<Value, Value> map = new HashMap<>();
        map.put(StringValue.of("code"), NumericValue.of(code));
        return MapValue.wrap(map);
    }
    private static MapValue createErrorMap(RestRequestResponseInformation restRequestResponseInformation) {
        Map<Value, Value> map = new HashMap<>();
        map.put(StringValue.of("code"), NumericValue.of(restRequestResponseInformation.getCode()));
        restRequestResponseInformation.getBody().ifPresent(body -> {
            Value json;
            try {
                json = Auxiliary.GSON.fromJson(body, Value.class);
            } catch (JsonParseException e) {
                json = StringValue.of(body);
            }
            map.put(StringValue.of("body"), json);
        });
        return MapValue.wrap(map);
    }
    
    public static class Codes {
        public static final int CANNOT_EXECUTE_ACTION_ON_CHANNEL_TYPE = 50024;
        public static final int INVALID_EMOJI = 50151;
    }
}
