package me.i509.brigwrapper.arguments;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import me.i509.brigwrapper.CommandPermission;
import me.i509.brigwrapper.source.CommandSource;
import me.i509.brigwrapper.util.CommandUtils;

public class DynamicStringArgument {
    
    @FunctionalInterface
    public interface DynamicSuggestions {
        String[] getSuggestions();
    }

    private DynamicSuggestions suggestions;
    
    public static DynamicStringArgument dynamicString(DynamicSuggestions dsuggestions) {
        return new DynamicStringArgument(dsuggestions);
    }
    
    protected DynamicStringArgument(DynamicSuggestions suggestions) {
        this.suggestions = suggestions;
    }
    
    public DynamicSuggestions getDynamicSuggestions() {
        return suggestions;
    }
    
    public SuggestionProvider<?> suggestions() {
        SuggestionProvider<?> provider;
        
        provider  = (context, builder) -> {
            return getSuggestionsBuilder(builder, getDynamicSuggestions().getSuggestions());
        };
        
        return provider;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public RequiredArgumentBuilder build(String argumentName) {
        return RequiredArgumentBuilder.argument(argumentName, StringArgumentType.word())
                .suggests((SuggestionProvider<Object>) this.suggestions());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public RequiredArgumentBuilder buildWithPermission(String argumentName, CommandPermission perm) {
        return RequiredArgumentBuilder.argument(argumentName, StringArgumentType.word())
                .suggests((SuggestionProvider<Object>) this.suggestions())
                    .requires(csource -> CommandUtils.testSenderPerms(CommandSource.getSource(csource), perm));
    }

    private CompletableFuture<Suggestions> getSuggestionsBuilder(SuggestionsBuilder builder, String[] array) {
        String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);
        for (int i = 0; i < array.length; i++) {
            String str = array[i];
            if (str.toLowerCase(Locale.ROOT).startsWith(remaining)) {
                builder.suggest(str);
            }
        }
        return builder.buildFuture();
    }
}
