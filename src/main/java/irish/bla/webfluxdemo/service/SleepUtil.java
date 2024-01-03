package irish.bla.webfluxdemo.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SleepUtil {

    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error("sleep interrupted", e);
        }
    }
}
