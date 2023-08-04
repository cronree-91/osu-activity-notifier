package jp.cron.sample.api.service;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class EventWaiterProvider {
    @Bean
    EventWaiter eventWaiter() {
        return new EventWaiter();
    }
}
