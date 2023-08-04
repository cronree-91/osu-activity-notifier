package jp.cron.sample.bot;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneralListener extends Listener {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        logger.info("Bot is ready");
        logger.info("BOT NAME: " + event.getJDA().getSelfUser().getName());

        event.getJDA().getPresence().setPresence(Activity.playing("Ready!"), false);
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
    }

    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        if (event.getChannelLeft() != null && event.getChannelLeft().getType()== ChannelType.VOICE)
            onGuildVoiceLeave(event.getGuild(), event.getMember(), event.getChannelLeft().asVoiceChannel());
        if (event.getChannelJoined() != null && event.getChannelJoined().getType()==ChannelType.VOICE)
            onGuildVoiceJoin(event.getGuild(), event.getMember(), event.getChannelJoined().asVoiceChannel());
    }

    private void onGuildVoiceJoin(Guild guild, Member target, VoiceChannel ch) {
    }

    private void onGuildVoiceLeave(Guild guild, Member target, VoiceChannel ch) {
    }

}


