package discarpet;

import carpet.script.Expression;
import carpet.script.LazyValue;
import carpet.script.value.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import discarpet.classes.embedField;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static discarpet.HTTPGetMethod.getHTML;

public class DiscordFunctions {
    public static void apply(Expression expr) {
        expr.addLazyFunction("sd_send_message", 2, (c, t, lv) -> {
            Value ret;
            if(Discarpet.discordEnabled) {
                String channel = ((LazyValue)lv.get(0)).evalValue(c).getString();
                String message = ((LazyValue)lv.get(1)).evalValue(c).getString();
                Discarpet.discordBot.sendMessage(channel, message);
                ret = Value.TRUE;
            } else {
                ret = Value.FALSE;
            }
            return (cc, tt) -> {
                return ret;
            };
        });


        expr.addLazyFunction("sd_message_buffer_size", 0, (c, t, lv) -> {
            Value ret;
            if(Discarpet.discordEnabled) {
                ret = new NumericValue(Discarpet.discordBot.messageBuffer.size());
            } else {
                ret = new NumericValue(-1);
            }
            return (cc, tt) -> {
                return ret;
            };
        });


        expr.addLazyFunction("sd_read_message_buffer", 0, (c, t, lv) -> {
            Value ret;
            if(Discarpet.discordEnabled && Discarpet.discordBot.messageBuffer.size() > 0) {
                Bot.message msg = Discarpet.discordBot.readMessageBuffer();
                Map<Value, Value> retMap = new HashMap<Value,Value>();
                retMap.put(new StringValue("content"),new StringValue(msg.content));
                retMap.put(new StringValue("author"),new StringValue(msg.author));
                retMap.put(new StringValue("channel"),new StringValue(msg.channel));
                ret = MapValue.wrap(retMap);
            } else {
                ret = Value.NULL;
            }
            return (cc, tt) -> {
                return ret;
            };
        });



        expr.addLazyFunction("sd_chat_buffer_size", 0, (c, t, lv) -> {
            Value ret;
            if(Discarpet.discordEnabled) {
                ret = new NumericValue(Discarpet.minecraftChat.chatBuffer.size());
            } else {
                ret = new NumericValue(-1);
            }
            return (cc, tt) -> {
                return ret;
            };
        });


        expr.addLazyFunction("sd_read_chat_buffer", 0, (c, t, lv) -> {
            Value ret;
            if(Discarpet.discordEnabled && Discarpet.minecraftChat.chatBuffer.size() > 0) {
                Chat.chat cht = Discarpet.minecraftChat.readChatBuffer();
                Map<Value, Value> retMap = new HashMap<Value,Value>();
                retMap.put(new StringValue("text"),new StringValue(cht.text));
                retMap.put(new StringValue("key"),new StringValue(cht.key));
                ret = MapValue.wrap(retMap);
            } else {
                ret = Value.NULL;
            }
            return (cc, tt) -> {
                return ret;
            };
        });
        expr.addLazyFunction("sd_set_channel_topic", 2, (c, t, lv) -> {
            if(Discarpet.discordEnabled) {
                String channel = ((LazyValue)lv.get(0)).evalValue(c).getString();
                String description = ((LazyValue)lv.get(1)).evalValue(c).getString();
                Discarpet.discordBot.setChannelTopic(channel,description);
            }
            return (cc, tt) -> {
                return Value.TRUE;
            };
        });

        expr.addLazyFunction("sd_set_activity", 2, (c, t, lv) -> {
            if(Discarpet.discordEnabled) {
                int type = (NumericValue.asNumber(((LazyValue)lv.get(0)).evalValue(c)).getInt());
                String text = (lv.get(1)).evalValue(c).getString();
                Discarpet.discordBot.setActivity(type,text);
            }
            return (cc, tt) -> {
                return Value.TRUE;
            };
        });

        expr.addLazyFunction("sd_set_status", 1, (c, t, lv) -> {
            if(Discarpet.discordEnabled) {
                int status = (NumericValue.asNumber(((LazyValue)lv.get(0)).evalValue(c)).getInt());
                Discarpet.discordBot.setStatus(status);
            }
            return (cc, tt) -> {
                return Value.TRUE;
            };
        });


        expr.addLazyFunction("sd_send_embed", 12, (c, t, lv) -> {
            if(Discarpet.discordEnabled) {
				/*List<Value> l = new ArrayList<>();
				Value v = ListValue.wrap(l);
				((ListValue)v).getItems().
				((MapValue)v).get(new NumericValue(2));
				//(NumericValue(((LazyValue)lv.get(0)).evalValue(c)).getInt());
				int status = (NumericValue.asNumber(((LazyValue)lv.get(0)).evalValue(c)).getInt());
				System.out.println(status);*/
                String channel = (lv.get(0)).evalValue(c).getString();
                String title = (lv.get(1)).evalValue(c).getString();
                String description = (lv.get(2)).evalValue(c).getString();
                String authorName = (lv.get(3)).evalValue(c).getString();
                String authorLink = (lv.get(4)).evalValue(c).getString();
                String authorAvatar = (lv.get(5)).evalValue(c).getString();

                Value fieldValueList = ((lv.get(6)).evalValue(c));
                List<embedField> fieldEmbedList = new ArrayList<>();
                if(fieldValueList.getTypeString() == "list") {
                    List<Value> fieldListValue = ((ListValue)fieldValueList).getItems();
                    for(int i = 0; i < fieldListValue.size();i++) {
                        List<Value> im = ((ListValue)(fieldListValue.get(i))).getItems();
                        if(im.size() == 3) {
                            fieldEmbedList.add(new embedField((NumericValue.asNumber(im.get(0))).getBoolean(),im.get(1).getString(),im.get(2).getString()));
                        }
                    }
                }


                Value colorValueList = ((lv.get(7)).evalValue(c));
                Color color = new Color(0,0,0);
                if(colorValueList.getTypeString() == "list") {
                    List<Value> colorListValue = ((ListValue)colorValueList).getItems();
                    if(colorListValue.size() == 3) {
                        color = new Color((NumericValue.asNumber(colorListValue.get(0))).getInt(),(NumericValue.asNumber(colorListValue.get(1))).getInt(),(NumericValue.asNumber(colorListValue.get(2))).getInt());
                    }
                }

                String footerName = (lv.get(8)).evalValue(c).getString();
                String footerAvatar = (lv.get(9)).evalValue(c).getString();
                String image = (lv.get(10)).evalValue(c).getString();
                String thumbnail = (lv.get(11)).evalValue(c).getString();
                Discarpet.discordBot.sendEmbed(channel, title, description, authorName, authorLink, authorAvatar, fieldEmbedList, color, footerName, footerAvatar, image, thumbnail);
            }
            return (cc, tt) -> {
                return Value.TRUE;
            };
        });


        expr.addLazyFunction("set_tab_text", 2, (c, t, lv) -> {
            Value ret = Value.FALSE;
            Value head = lv.get(0).evalValue(c);
            Value foot = lv.get(1).evalValue(c);

            if(head instanceof FormattedTextValue) {
                Discarpet.customHeader = (LiteralText) ((FormattedTextValue) head).getText();
                Discarpet.updateTabHeader = true;
                ret = Value.TRUE;
            } else if (head instanceof StringValue) {
                Discarpet.customHeader = new LiteralText(((StringValue) head).getString());
                Discarpet.updateTabHeader = true;
                ret = Value.TRUE;
            } else {
                if(head == Value.NULL) {
                    Discarpet.customHeader = new LiteralText("");
                    Discarpet.updateTabHeader = true;
                    ret = Value.TRUE;
                }
            }

            if(foot instanceof FormattedTextValue) {
                Discarpet.customFooter = (LiteralText) ((FormattedTextValue) foot).getText();
                Discarpet.updateTabHeader = true;
                ret = Value.TRUE;
            } else if (foot instanceof StringValue) {
                Discarpet.customFooter = new LiteralText(((StringValue) foot).getString());
                Discarpet.updateTabHeader = true;
                ret = Value.TRUE;
            } else {
                if(foot == Value.NULL) {
                    Discarpet.customFooter = new LiteralText("");
                    Discarpet.updateTabHeader = true;
                    ret = Value.TRUE;
                }
            }


            final Value fret = ret;
            return (cc, tt) -> {
                return fret;
            };
        });

        expr.addLazyFunction("color", 3, (c, t, lv) -> {
            float hue = ((float)(NumericValue.asNumber(((LazyValue)lv.get(0)).evalValue(c)).getInt()))/360;
            float sat = ((float)(NumericValue.asNumber(((LazyValue)lv.get(1)).evalValue(c)).getInt()))/255;
            float bri = ((float)(NumericValue.asNumber(((LazyValue)lv.get(2)).evalValue(c)).getInt()))/255;

            int rgb = Color.HSBtoRGB(hue, sat, bri);
            int red = (rgb >> 16) & 0xFF;
            int green = (rgb >> 8) & 0xFF;
            int blue = rgb & 0xFF;
            String hex = String.format("#%02X%02X%02X",red, green, blue);
            //String hex = String.valueOf(red) + " " + String.valueOf(green) + " " + String.valueOf(blue);
            return (cc, tt) -> {
                return new StringValue(hex);
            };
        });


        expr.addLazyFunction("fetch", 2, (c, t, lv) -> {
            String url = (lv.get(0)).evalValue(c).getString();
            String ret;
            try {
                ret = getHTML(url);
            } catch (Exception e) {
                e.printStackTrace();
                return (cc, tt) -> {
                    return Value.FALSE;
                };
            }
            return (cc, tt) -> {
                return new StringValue(ret);
            };
        });


        expr.addLazyFunction("open_inventory", 3, (c, t, lv) -> {
            Value playerValue = (lv.get(0)).evalValue(c);
            Value inventoryValue = (lv.get(2)).evalValue(c);
            Text inventoryName = new LiteralText((lv.get(1)).evalValue(c).getString());
            PlayerEntity player;
            if(playerValue instanceof EntityValue) {
                Entity entity = ((EntityValue)playerValue).getEntity();
                if(entity instanceof PlayerEntity) {
                    player = ((PlayerEntity)entity);
                } else {
                    return (cc, tt) -> {
                        return Value.FALSE;
                    };
                }
            } else {
                return (cc, tt) -> {
                    return Value.FALSE;
                };
            }

            SimpleInventory inv = new SimpleInventory(27);
            if(inventoryValue instanceof ListValue) {
                List<Value> inventoryList = ((ListValue)inventoryValue).getItems();
                for(int i = 0; i < inventoryList.size(); i++) {
                    Value itemList = inventoryList.get(i);
                    if(itemList instanceof ListValue) {
                        List<Value> item = ((ListValue) itemList).getItems();
                        if (item.size() == 3) {
                            CompoundTag nbt = null;
                            Value nbtValue = item.get(2);
                            int count = (int)NumericValue.asNumber(((NumericValue)(item.get(1)))).getLong();
                            if (nbtValue instanceof NBTSerializableValue) {
                                nbt = ((NBTSerializableValue)nbtValue).getCompoundTag();
                            } else if (nbtValue instanceof NullValue) {
                                nbt = null;
                            } else {
                                nbt = (new NBTSerializableValue(nbtValue.getString())).getCompoundTag();
                            }
                            ItemStackArgument newitem = NBTSerializableValue.parseItem(item.get(0).getString(), nbt);
                            try {
                                inv.setStack(i, newitem.createStack(count, false));
                            } catch (CommandSyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
                return GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, inv);
            }, inventoryName));

            return (cc, tt) -> {
                return Value.TRUE;
            };
        });
    }
}
