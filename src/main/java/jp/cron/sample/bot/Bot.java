package jp.cron.sample.bot;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.vdurmont.emoji.EmojiParser;
import jp.cron.sample.bot.command.Command;
import jp.cron.sample.bot.command.CommandManager;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Bot {

    @Autowired
    CommandManager commandManager;

    @Autowired
    List<Listener> listeners;

    @Value("${bot.setting.ownerId}")
    String ownerId;
    @Value("${bot.setting.token}")
    String token;

    @Autowired
    EventWaiter eventWaiter;

    public Bot() {
    }

    @Bean
    public CommandClientBuilder commandClientBuilder() {
        return new CommandClientBuilder()
                .setOwnerId(ownerId)
//                .setCoOwnerIds(coOwnerIds.split(","))
                .setEmojis(EmojiParser.parseToUnicode("o"), null, EmojiParser.parseToUnicode("x"))
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(null)
                .addCommands(commandManager.getCommands().toArray(new Command[0]))
                .addSlashCommands(commandManager.getCommands().toArray(new Command[0]))
                .useHelpBuilder(false);
    }

    @Bean
    public JDABuilder jdaBuilder() {
        return JDABuilder.create(token, Arrays.asList(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.DIRECT_MESSAGES))
                .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS)
                .setBulkDeleteSplittingEnabled(true)
                .setAutoReconnect(true)
                .setEnableShutdownHook(true)
                .addEventListeners(eventWaiter)
                .setActivity(Activity.playing("準備中... / Loading..."))
                .addEventListeners(listeners.toArray(new Listener[0]));
    }
}
