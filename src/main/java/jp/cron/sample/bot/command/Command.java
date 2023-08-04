package jp.cron.sample.bot.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;

public abstract class Command extends SlashCommand {
    public Command(String name, String help, String... aliases) {
        this.name = name;
        this.help = help;
        this.aliases = aliases;
    }
}
