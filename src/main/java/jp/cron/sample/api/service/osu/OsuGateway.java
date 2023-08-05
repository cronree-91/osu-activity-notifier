package jp.cron.sample.api.service.osu;

import com.mongodb.lang.Nullable;
import jp.cron.sample.api.service.osu.res.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Map;

@Service
public class OsuGateway {

    @Autowired
    RestOperations restOperations;

    @Nullable
    public OsuUser getUser(String token, String osuUserId) {
        RequestEntity<Void> request = RequestEntity.get("https://osu.ppy.sh/api/v2/users/"+osuUserId)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();

        ResponseEntity<OsuUser> res = restOperations.exchange(request, OsuUser.class);

        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            return null;
        }
    }

    @Nullable
    public OsuBeatMap getBeatMap(String token, String beatMapId) {
        RequestEntity<Void> request = RequestEntity.get("https://osu.ppy.sh/api/v2/beatmap/"+beatMapId)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .build();

        ResponseEntity<OsuBeatMap> res = restOperations.exchange(request, OsuBeatMap.class);

        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            return null;
        }
    }

    @Value("${osu.client.id}")
    String clientId;
    @Value("${osu.client.secret}")
    String clientSecret;

    @Nullable
    public TokenResponse getToken() {

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("client_id", clientId);
        header.add("client_secret", clientSecret);
        header.add("grant_type", "client_credentials");
        header.add("scope", "public");

        RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post("https://osu.ppy.sh/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(header);

        ResponseEntity<TokenResponse> res = restOperations.exchange(request, TokenResponse.class);

        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            return null;
        }
    }

    @Nullable
    public List<OsuScore> getScores(String token, String osuUserId) {
        RequestEntity<Void> request = RequestEntity.get("https://osu.ppy.sh/api/v2/users/" + osuUserId + "/scores/recent")
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();

        ResponseEntity<List<OsuScore>> res = restOperations.exchange(request, new ParameterizedTypeReference<List<OsuScore>>() {});

        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            return null;
        }
    }

    @Nullable
    public List<OsuEvent> getRecentActivity(String token, String osuUserId, Integer limit) {
        RequestEntity<Void> request = RequestEntity.get("https://osu.ppy.sh/api/v2/users/" + osuUserId + "/recent_activity?limit=" + limit)
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .build();

        ResponseEntity<List<OsuEvent>> res = restOperations.exchange(request, new ParameterizedTypeReference<List<OsuEvent>>() {});

        if (res.getStatusCode().is2xxSuccessful()) {
            return res.getBody();
        } else {
            return null;
        }
    }

}
