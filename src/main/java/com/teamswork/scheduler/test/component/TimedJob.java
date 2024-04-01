package com.teamswork.scheduler.test.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;

@Named
public class TimedJob {
    private static final Logger log = LoggerFactory.getLogger(TimedJob.class);

    public TimedJob() {
        log.info("TimedJob instantiated.");
    }

    public void execute() {
        final long start = System.currentTimeMillis();
        log.info("Execution of TimedJob started at:" + start);

        // Wait for a number of seconds between 20 and 40
        final int waitTime = (int) (Math.random() * 20) + 20;
        log.info("Waiting for " + waitTime + " seconds.");
        try {
            Thread.sleep(waitTime * 1000);
        } catch (InterruptedException e) {
            log.error("Error while waiting: " + e.getMessage());
        }

        final long end = System.currentTimeMillis();
        log.info("Execution of TimedJob finished at:" + end);
        log.info("TimedJob took: " + (end - start ) + " milliseconds or " + (end - start) / 1000 + " seconds.");
    }
}
