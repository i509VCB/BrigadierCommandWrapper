package me.i509.brigwrapper.impl.v1_14_R1.arg.decoder;

import org.bukkit.ChatColor;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.ChatColorWrapper.ChatColorArgumentDecoder;
import net.minecraft.server.v1_14_R1.ArgumentChatFormat;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;

public class ChatColorDecoder_1_14_R1 extends ChatColorArgumentDecoder {

    @SuppressWarnings("unchecked")
    @Override
    protected ChatColor _getColor(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return ChatColor.getByChar(ArgumentChatFormat.a((CommandContext<CommandListenerWrapper>) cmdCtx, str).character);
    }

}
