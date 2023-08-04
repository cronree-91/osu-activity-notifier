package jp.cron.sample.util;

import net.dv8tion.jda.api.entities.User;

public class FormatUtil {
    public static String formatUser(User user, boolean id) {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getName()).append("#").append(user.getDiscriminator());
        if (id)
            sb.append(" (").append(user.getId()).append(")");
        return sb.toString();
    }

}