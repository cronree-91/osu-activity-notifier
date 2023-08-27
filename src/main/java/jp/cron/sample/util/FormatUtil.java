package jp.cron.sample.util;

import jp.cron.sample.api.service.osu.res.OsuEvent;
import jp.cron.sample.api.service.osu.res.OsuScore;
import jp.cron.sample.api.service.osu.res.OsuUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.apache.logging.log4j.util.Strings;

public class FormatUtil {
    public static String formatUser(User user, boolean id) {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName()).append("#").append(user.getDiscriminator());
        if (id)
            sb.append(" (").append(user.getId()).append(")");
        return sb.toString();
    }

    public static MessageEmbed generateOsuUserEmbed(OsuUser user) {
        return new EmbedBuilder()
                .setTitle(user.username)
                .setThumbnail(user.avatar_url)
                .setDescription(user.id.toString())
                .build();
    }

    public static MessageEmbed generateOsuRankEventEmbed(OsuUser user, OsuEvent event) {
        return new EmbedBuilder()
                .setTitle(user.username+" が `"+event.beatmap.title+"` を "+event.scoreRank+" でクリアして "+event.rank+"位になりました！")
                .setThumbnail(user.avatar_url)
                .setDescription(
                        "[ユーザ]("+"https://osu.ppy.sh"+event.user.url+")\n"+
                        "[譜面]("+"https://osu.ppy.sh"+event.beatmap.url+")"
                )
                .addField("モード", event.mode, true)
                .build();
    }

    public static MessageEmbed generateOsuScoreEmbed(String userId, OsuScore score) {

        return new EmbedBuilder()
                .setTitle("`"+score.beatmapset.title+" ["+score.beatmap.version+"]`")
                .setDescription("<@"+userId+">\n[譜面]("+score.beatmap.url+")\n[ユーザ](https://osu.ppy.sh/u/"+score.user.id+")")
                .setThumbnail(score.beatmapset.covers.list2x)
                .setFooter(score.user.username, score.user.avatar_url)
                .addField("譜面☆", score.beatmap.difficulty_rating.toString(), true)
                .addField("BPM", score.beatmap.bpm.toString(), true)
                .addField("スコア", score.score, true)
                .addField("正確さ", String.format("%.2f", score.accuracy*100), true)
                .addField("最大コンボ", score.max_combo.toString(), true)
                .addField("ランク", score.rank, true)
                .addField("MOD", score.mods.isEmpty() ? "なし" : Strings.join(score.mods, ' '), true)
                .addField("PP", score.pp==null ? "なし" : String.format("%.2f", score.pp), true)
                .build();
    }
}