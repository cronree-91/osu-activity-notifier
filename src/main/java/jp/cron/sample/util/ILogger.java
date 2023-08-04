package jp.cron.sample.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface ILogger {

    default Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass().getName());
    }
}
