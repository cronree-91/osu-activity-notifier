package jp.cron.sample.api.service.osu;

import jp.cron.sample.api.service.osu.res.OsuEvent;
import jp.cron.sample.api.service.osu.res.OsuScore;
import jp.cron.sample.api.service.osu.res.OsuUser;
import jp.cron.sample.api.service.osu.res.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OsuService {
    OsuGateway gateway;

    String token;
    LocalDateTime expiresIn;

    @Autowired
    public OsuService(OsuGateway gateway) {
        this.gateway = gateway;

        TokenResponse response = this.gateway.getToken();
        if (response==null)
            throw new RuntimeException("Failed to get token.");
        this.token = response.access_token;
        this.expiresIn = LocalDateTime.now().plusSeconds(response.expires_in);
    }

    public String getUsername(String osuId) {
        this.checkToken();
        OsuUser user = this.gateway.getUser(this.token, osuId);

        return user != null ? user.username : null;
    }

    public OsuUser getUser(String osuId) {
        this.checkToken();
        return this.gateway.getUser(this.token, osuId);
    }

    public List<OsuEvent> getRankEvents(String osuId) {
        this.checkToken();

        List<OsuEvent> events = this.gateway.getRecentActivity(this.token, osuId, 5);
        if (events==null)
            events = Collections.emptyList();

        return events.stream()
                .filter(e -> e.type.equals("rank"))
                .collect(Collectors.toList());
    }

    public List<OsuScore> getRecentScores(String osuId) {
        this.checkToken();

        List<OsuScore> scores = this.gateway.getScores(this.token, osuId);
        if (scores==null)
            scores = Collections.emptyList();

        return scores;
    }

    private void checkToken() {
        if (LocalDateTime.now().isAfter(this.expiresIn)) {
            TokenResponse response = this.gateway.getToken();
            if (response==null)
                throw new RuntimeException("Failed to get token.");
            this.token = response.access_token;
            this.expiresIn = LocalDateTime.now().plusSeconds(response.expires_in);
        }
    }

}
