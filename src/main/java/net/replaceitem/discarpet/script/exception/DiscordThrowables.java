package net.replaceitem.discarpet.script.exception;

import carpet.script.api.Auxiliary;
import carpet.script.exception.ThrowStatement;
import carpet.script.exception.Throwables;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import com.google.gson.JsonParseException;
import net.dv8tion.jda.api.exceptions.*;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.api.requests.Response;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.script.util.MapBuilder;
import net.replaceitem.discarpet.script.util.ValueUtil;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static carpet.script.exception.Throwables.THROWN_EXCEPTION_TYPE;
import static carpet.script.exception.Throwables.register;

public class DiscordThrowables {
    public static final Throwables DISCORD_EXCEPTION = register("discord_exception", THROWN_EXCEPTION_TYPE);
    
    public static final Throwables API_EXCEPTION = register("api_exception", DISCORD_EXCEPTION);
    
    public static final Throwables RATE_LIMIT = register("rate_limit", DISCORD_EXCEPTION);
    
    public static final Throwables MISSING_PERMISSION = register("missing_permission", DISCORD_EXCEPTION);
    public static final Throwables HTTP_EXCEPTION = register("http_exception", DISCORD_EXCEPTION);
    
    public static ThrowStatement convert(Throwable exception) {
        return convert(exception, null);
    }
    
    public static ThrowStatement convert(Throwable exception, @Nullable String message) {
        String finalMessage = message == null ? exception.getMessage() : exception.getMessage() + ": " + message;
        switch (exception) {
            case ExecutionException executionException when executionException.getCause() != null -> {
                return convert(executionException.getCause(), message);
            }
            case ErrorResponseException errorResponseException -> {
                Value value = createErrorMap(errorResponseException.getResponse());
                return new ThrowStatement(value, API_EXCEPTION);
            }
            case PermissionException permissionException -> {
                MapBuilder map = new MapBuilder()
                        .put("permission", ValueUtil.ofEnum(permissionException.getPermission()))
                        .put("message", permissionException.getMessage());
                if(permissionException instanceof InsufficientPermissionException insufficientPermissionException){
                    map.put("server", Long.toUnsignedString(insufficientPermissionException.getGuildId()));
                    map.put("channel", Long.toUnsignedString(insufficientPermissionException.getChannelId()));
                }
                return new ThrowStatement(map.build(), MISSING_PERMISSION);
            }
            case RateLimitedException ignored -> {
                return new ThrowStatement(finalMessage, RATE_LIMIT);
            }
            case HttpException ignored -> {
                return new ThrowStatement(finalMessage, HTTP_EXCEPTION);
            }
            case IOException ignored -> {
                return new ThrowStatement(finalMessage, Throwables.IO_EXCEPTION);
            }
            case UncheckedIOException uncheckedIOException when uncheckedIOException.getCause() != null -> {
                return convert(uncheckedIOException.getCause(), message);
            }
            default -> {
                Discarpet.LOGGER.error("Could not convert exception type {} to discarpet exception ({}):", exception.getClass().getSimpleName(), message, exception);
                return new ThrowStatement(exception.getMessage(), Throwables.THROWN_EXCEPTION_TYPE);
            }
        }
    }
    
    public static ThrowStatement genericCode(ErrorResponse errorCode) {
        return new ThrowStatement(createSimpleError(errorCode), DISCORD_EXCEPTION);
    }
    
    public static ThrowStatement genericMessage(String message) {
        return new ThrowStatement(message, DISCORD_EXCEPTION);
    }

    private static MapValue createSimpleError(ErrorResponse errorCode) {
        return new MapBuilder()
                .put("code", NumericValue.of(errorCode.getCode()))
                .put("message", StringValue.of(errorCode.getMeaning()))
                .build();
    }
    private static MapValue createErrorMap(Response response) {
        MapBuilder map = new MapBuilder()
                .put("code", NumericValue.of(response.code))
                .put("message", StringValue.of(getMessage(response)));
        response.optObject().ifPresent(body -> {
            Value json;
            try {
                json = Auxiliary.GSON.fromJson(body.toString(), Value.class);
            } catch (JsonParseException e) {
                json = StringValue.of(body.toString());
            }
            map.put("body", json);
        });
        return map.build();
    }
    private static String getMessage(Response response) {
        return Objects.equals(response.message, Response.ERROR_MESSAGE) && response.getException() != null ?
                response.getException().getMessage() :
                response.message;
    }
}
