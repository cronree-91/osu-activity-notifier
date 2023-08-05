package jp.cron.sample.api.service.osu.res;

import java.time.LocalDateTime;
import java.util.Date;

public class OsuEvent {

    public Date created_at;
    public Integer id;
    public String type;

    public OsuBeatMap beatmap;
    public OsuUser user;

    public String scoreRank;
    public Integer rank;
    public String mode;

    public static class OsuBeatMap {
        public String title;
        public String url;
    }
    public static class OsuUser {
        public String username;
        public String url;
    }

}
