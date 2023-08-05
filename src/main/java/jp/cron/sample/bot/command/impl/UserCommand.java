package jp.cron.sample.bot.command.impl;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import jp.cron.sample.bot.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCommand extends Command {
    @Autowired
    public UserCommand(List<UserSubCommand> commands) {
        super("user", "ユーザーを追加/削除します。");

        this.children = commands.toArray(new UserSubCommand[0]);
    }

    @Override
    protected void execute(SlashCommandEvent event) {
    }
}
