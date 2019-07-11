package me.i509.brigwrapper.impl.v1_14_R1.arg;

import org.bukkit.ChatColor;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import me.i509.brigwrapper.arguments.ChatColorWrapper;
import net.minecraft.server.v1_14_R1.ArgumentChatFormat;
import net.minecraft.server.v1_14_R1.CommandListenerWrapper;

public class ChatColorWrapper_1_14_R1 extends ChatColorWrapper {

    @Override
    public ArgumentType<?> getNMSType() {
        return ArgumentChatFormat.a();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public ChatColor _getColor(CommandContext<?> cmdCtx, String str) throws CommandSyntaxException {
        return ChatColor.getByChar(ArgumentChatFormat.a((CommandContext<CommandListenerWrapper>) cmdCtx, str).character);
    }
    
}
