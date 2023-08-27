package jp.cron.sample.bot.command.impl.user;

import com.jagrosh.jdautilities.command.SlashCommandEvent;
import jp.cron.sample.api.data.UserEntity;
import jp.cron.sample.api.data.UserRepository;
import jp.cron.sample.api.service.osu.OsuService;
import jp.cron.sample.api.service.osu.res.OsuUser;
import jp.cron.sample.bot.command.impl.UserSubCommand;
import jp.cron.sample.util.FormatUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserAddCommand extends UserSubCommand {
    @Autowired
    public UserAddCommand() {
        super("add", "ユーザーを追加します。");

        this.options = List.of(
                new OptionData(OptionType.USER, "user", "Discordユーザ", true),
                new OptionData(OptionType.STRING, "osu-user", "osuユーザID", true)
        );
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    OsuService osuService;

    @Override
    protected void process(SlashCommandEvent event) {
        User user = event.getOption("user").getAsUser();
        String osuId = event.getOption("osu-user").getAsString();

        OsuUser osuUser  = osuService.getUser(osuId);
        if (osuUser==null) {
            event.reply("osuユーザが見つかりませんでした。").submit();
            return;
        }

        UserEntity userE = new UserEntity();
        userE.id = user.getId();
        userE.osuId = String.valueOf(osuUser.id);

        userRepository.save(userE);

        event.getHook().sendMessage(
                new MessageCreateBuilder()
                        .setContent("登録しました。")
                        .addEmbeds(
                                FormatUtil.generateOsuUserEmbed(osuUser)
                        )
                        .build()
        ).submit();
    }
}
