package jp.cron.sample.api.data;

import org.springframework.data.annotation.Id;

public class UserEntity {

    @Id
    public String id;

    public String osuId;
}
