package net.replaceitem.discarpet.script.parsable.parsables;

import carpet.script.exception.InternalExpressionException;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.attribute.IPostContainer;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.managers.channel.ChannelManager;
import net.dv8tion.jda.api.managers.channel.attribute.*;
import net.dv8tion.jda.api.managers.channel.concrete.ThreadChannelManager;
import net.dv8tion.jda.api.managers.channel.middleman.AudioChannelManager;
import net.dv8tion.jda.api.managers.channel.middleman.StandardGuildMessageChannelManager;
import net.replaceitem.discarpet.script.parsable.OptionalField;
import net.replaceitem.discarpet.script.parsable.ParsableClass;
import org.jetbrains.annotations.Nullable;

@ParsableClass(name = "channel_updater")
public class ChannelUpdaterParsable {
    // general
    @OptionalField @Nullable
    String name;
    @OptionalField @Nullable
    String topic;
    @OptionalField @Nullable
    Boolean nsfw;
    @OptionalField @Nullable
    Integer position;
    @OptionalField @Nullable
    Channel parent;
    @OptionalField @Nullable
    Integer auto_archive_duration;
    
    // threads
    @OptionalField @Nullable
    Integer slowmode;
    @OptionalField @Nullable
    Boolean archived;
    @OptionalField @Nullable
    Boolean locked;
    @OptionalField @Nullable
    Boolean invitable;
    @OptionalField @Nullable
    Boolean pinned;
    
    // threads container
    @OptionalField @Nullable
    Integer default_threads_slowmode;
    @OptionalField @Nullable
    Boolean tag_required;
    @OptionalField @Nullable
    IPostContainer.SortOrder default_sort_order;
    @OptionalField @Nullable
    Emoji default_reaction;
    
    // voice
    @OptionalField @Nullable
    Integer bitrate;
    @OptionalField @Nullable
    Integer user_limit;
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void apply(ChannelManager<?, ?> manager) {
        // general
        if(name != null) manager.setName(name);

        if(topic != null) {
            if(manager instanceof StandardGuildMessageChannelManager<?,?> standardGuildMessageChannelManager) {
                standardGuildMessageChannelManager.setTopic(topic);
            } else if(manager instanceof IPostContainerManager<?,?> postContainerManager) {
                postContainerManager.setTopic(topic);
            } else throw wrongType("topic", manager);
        }
        
        if(nsfw != null) {
            if(manager instanceof IAgeRestrictedChannelManager<?,?> ageRestrictedChannelManager) {
                ageRestrictedChannelManager.setNSFW(nsfw);
            } else throw wrongType("nsfw", manager);
        }

        if(position != null) {
            if(manager instanceof IPositionableChannelManager<?,?> positionableChannelManager) {
                positionableChannelManager.setPosition(position);
            } else throw wrongType("position", manager);
        }

        if(parent != null) {
            if(manager instanceof ICategorizableChannelManager<?,?> iCategorizableChannelManager) {
                if(!(parent instanceof Category category)) throw new InternalExpressionException("'parent' must be a channel category");
                iCategorizableChannelManager.setParent(category);
            } else throw wrongType("parent", manager);
        }

        if(slowmode != null) {
            if(manager instanceof ISlowmodeChannelManager<?,?> slowmodeChannelManager) {
                slowmodeChannelManager.setSlowmode(slowmode);
            } else throw wrongType("slowmode", manager);
        }
        
        // threads
        if(auto_archive_duration != null) {
            if(manager instanceof ThreadChannelManager threadChannelManager) {
                threadChannelManager.setAutoArchiveDuration(ThreadChannel.AutoArchiveDuration.fromKey(auto_archive_duration));
            } else throw wrongType("auto_archive_duration", manager);
        }

        if(archived != null) {
            if(manager instanceof ThreadChannelManager threadChannelManager) {
                threadChannelManager.setArchived(archived);
            } else throw wrongType("archived", manager);
        }

        if(locked != null) {
            if(manager instanceof ThreadChannelManager threadChannelManager) {
                threadChannelManager.setLocked(locked);
            } else throw wrongType("locked", manager);
        }

        if(invitable != null) {
            if(manager instanceof ThreadChannelManager threadChannelManager) {
                threadChannelManager.setInvitable(invitable);
            } else throw wrongType("invitable", manager);
        }

        if(pinned != null) {
            if(manager instanceof ThreadChannelManager threadChannelManager) {
                threadChannelManager.setInvitable(pinned);
            } else throw wrongType("pinned", manager);
        }

        // threads container
        if(default_threads_slowmode != null) {
            if(manager instanceof IThreadContainerManager<?,?> threadContainerManager) {
                threadContainerManager.setDefaultThreadSlowmode(default_threads_slowmode);
            } else throw wrongType("default_threads_slowmode", manager);
        }

        if(tag_required != null) {
            if(manager instanceof IPostContainerManager<?,?> postContainerManager) {
                postContainerManager.setTagRequired(tag_required);
            } else throw wrongType("tag_required", manager);
        }

        if(default_sort_order != null) {
            if(manager instanceof IPostContainerManager<?,?> postContainerManager) {
                postContainerManager.setDefaultSortOrder(default_sort_order);
            } else throw wrongType("default_sort_order", manager);
        }

        if(default_reaction != null) {
            if(manager instanceof IPostContainerManager<?,?> postContainerManager) {
                postContainerManager.setDefaultReaction(default_reaction);
            } else throw wrongType("default_reaction", manager);
        }

        // voice
        if(bitrate != null) {
            if(manager instanceof AudioChannelManager<?,?> audioChannelManager) {
                audioChannelManager.setBitrate(bitrate);
            } else throw wrongType("bitrate", manager);
        }
        
        if(user_limit != null) {
            if(manager instanceof AudioChannelManager<?,?> audioChannelManager) {
                audioChannelManager.setUserLimit(user_limit);
            } else throw wrongType("user_limit", manager);
        }
    }
    
    private static InternalExpressionException wrongType(String fieldName, ChannelManager<?,?> manager) {
        return new InternalExpressionException("'" + fieldName + "' is not supported for channel of type " + manager.getChannel().getType().name().toLowerCase());
    }
}
