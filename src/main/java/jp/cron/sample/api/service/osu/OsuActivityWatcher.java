package jp.cron.sample.api.service.osu;

import jp.cron.sample.api.data.UserEntity;
import jp.cron.sample.api.data.UserRepository;
import jp.cron.sample.api.service.osu.res.OsuEvent;
import jp.cron.sample.api.service.osu.res.OsuScore;
import jp.cron.sample.api.service.osu.res.OsuUser;
import jp.cron.sample.util.FormatUtil;
import jp.cron.sample.util.ILogger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@EnableScheduling
@Service
public class OsuActivityWatcher implements ILogger {

    @Autowired
    UserRepository userRepository;
    @Autowired
    OsuService osuService;

    Map<String, Date> lastActivity = new HashMap<>();

    public JDA jda;

    @Value("${bot.setting.channel}")
    String channelId;

    @Scheduled(fixedDelay = 1000 * 10)
    public void watch() {
        if (jda==null)
            return;

        for (UserEntity user : userRepository.findAll()) {
            List<OsuScore> scores = osuService.getRecentScores(user.osuId);
            if (scores.isEmpty())
                continue;
            Collections.reverse(scores);

            Date last = lastActivity.get(user.id);
            if (last==null) {
                lastActivity.put(user.id, new Date());
                continue;
            }

            for (OsuScore score : scores) {
                if (last.before(score.created_at)) {

                    lastActivity.put(user.id, score.created_at);
                    MessageEmbed embed = FormatUtil.generateOsuScoreEmbed(score);
                    jda.getTextChannelById(channelId).sendMessage(
                                    new MessageCreateBuilder()
                                            .setContent("@silent <@"+user.id+">")
                                            .addEmbeds(embed)
                                            .build()
                            ).queue();

                }
            }





//            List<OsuEvent> activities = osuService.getRankEvents(user.osuId);
//            if (activities.isEmpty())
//                continue;
//            Collections.reverse(activities);
//
//            getLogger().info("user: "+user.id+" "+user.osuId+" "+activities.size());
//
//            for (OsuEvent activity : activities) {
//                Date last = lastActivity.get(user.id);
//                if (last==null) {
//                    lastActivity.put(user.id, new Date());
//                    last = lastActivity.get(user.id);
//                }
//                getLogger().info(last.toString());
//                getLogger().info(activity.created_at.toString());
//                getLogger().info(String.valueOf(last.before(activity.created_at)));
//                if (last.before(activity.created_at)) {
//                    lastActivity.put(user.id, activity.created_at);
//                    OsuUser osuUser = osuService.getUser(user.osuId);
//                    MessageEmbed embed = FormatUtil.generateOsuRankEventEmbed(osuUser, activity);
//                    jda.getTextChannelById(channelId).sendMessage(
//                                    new MessageCreateBuilder()
//                                            .setContent("<@"+user.id+">")
//                                            .addEmbeds(embed)
//                                            .build()
//                            ).submit();
//                }
//            }
        }
    }
}
