package jp.cron.sample.bot.command.impl.user;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import jp.cron.sample.api.data.UserEntity;
import jp.cron.sample.api.data.UserRepository;
import jp.cron.sample.api.service.osu.OsuService;
import jp.cron.sample.bot.command.impl.UserSubCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserListCommand extends UserSubCommand {
    @Autowired
    public UserListCommand() {
        super("list", "ユーザー一覧を表示します。");
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    OsuService osuService;

    @Override
    protected void process(SlashCommandEvent event) {

        StringBuilder description = new StringBuilder();
        for (UserEntity userEntity : userRepository.findAll()) {
            description.append("<@").append(userEntity.id).append("> : ");
            description.append(osuService.getUsername(userEntity.osuId)).append("\n");
        }

        event.getHook().sendMessageEmbeds(
                new EmbedBuilder()
                        .setTitle("ユーザ一覧")
                        .setDescription(description.toString())
                        .build()
        ).submit();
    }
}
