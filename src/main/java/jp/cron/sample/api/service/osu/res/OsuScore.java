package jp.cron.sample.api.service.osu.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

public class OsuScore {

    public Double accuracy;
    public Date created_at;
    public Integer max_combo;
    public List<String> mods;
    public Boolean passed;
    public Boolean perfect;
    public Double pp;
    public String rank;
    public String score;
    public OsuBeatMap beatmap;
    public OsuBeatMapSet beatmapset;
    public OsuUser user;


    public static class OsuBeatMap {
        public Double difficulty_rating;
        public Integer id;
        public Integer bpm;
        public Integer total_length;
        public String url;
    }
    public static class OsuBeatMapSet {
        public Integer id;
        public String title;
        public String artist;
        public OsuBeatMapSetCovers covers;

        public static class OsuBeatMapSetCovers {
            @JsonProperty("list@2x")
            public String list2x;
        }
    }

    public static class OsuUser {
        public Integer id;
        public String username;
        public String avatar_url;
    }


}
