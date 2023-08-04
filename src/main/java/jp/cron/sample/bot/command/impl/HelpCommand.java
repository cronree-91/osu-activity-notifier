package jp.cron.sample.bot.command.impl;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import jp.cron.sample.bot.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends Command {
    @Autowired
    public HelpCommand() {
        super("help", "ヘルプを表示します。");
    }

    @Override
    protected void execute(SlashCommandEvent event) {

    }
}
