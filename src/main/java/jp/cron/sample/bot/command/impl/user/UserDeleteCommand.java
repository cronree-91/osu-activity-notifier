package jp.cron.sample.bot.command.impl.user;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import jp.cron.sample.api.data.UserEntity;
import jp.cron.sample.api.data.UserRepository;
import jp.cron.sample.bot.command.impl.UserSubCommand;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserDeleteCommand extends UserSubCommand {
    @Autowired
    public UserDeleteCommand() {
        super("remove", "ユーザーを削除します。");

        this.options = List.of(
                new OptionData(OptionType.USER, "user", "Discordユーザ", true)
        );
        this.ownerCommand = true;
    }

    @Autowired
    UserRepository userRepository;

    @Override
    protected void process(SlashCommandEvent event) {
        User user = event.getOption("user").getAsUser();

        userRepository.deleteById(user.getId());

        event.getHook().sendMessage("削除しました。").submit();
    }
}
