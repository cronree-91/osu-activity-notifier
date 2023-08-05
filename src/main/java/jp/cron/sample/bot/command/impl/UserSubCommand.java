package jp.cron.sample.bot.command.impl;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public abstract class UserSubCommand extends SlashCommand {
    public UserSubCommand(String name, String help, String... aliases) {
        this.name = name;
        this.help = help;
        this.aliases = aliases;
    }

    @Override
    protected void execute(SlashCommandEvent event) {
        event.deferReply().submit();
        process(event);
    }

    protected abstract void process(SlashCommandEvent event);
}
