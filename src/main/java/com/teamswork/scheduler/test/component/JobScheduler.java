package com.teamswork.scheduler.test.component;

import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class JobScheduler extends PluginStateListener {
    private static final Logger log = LoggerFactory.getLogger(JobScheduler.class);
    private final TimedJobRunner timedJobRunner;
    private final TimedJobRunner2 timedJobRunner2;
    private final TimedJobRunner3 timedJobRunner3;
    private final TimedJobRunner4 timedJobRunner4;
    private final TimedJobRunner5 timedJobRunner5;
    private final TimedJobRunner6 timedJobRunner6;

    private final SchedulerService schedulerService;

    public JobScheduler(@ComponentImport final EventPublisher eventPublisher,
                        final TimedJobRunner timedJobRunner,
                        final TimedJobRunner2 timedJobRunner2,
                        final TimedJobRunner3 timedJobRunner3,
                        final TimedJobRunner4 timedJobRunner4,
                        final TimedJobRunner5 timedJobRunner5,
                        final TimedJobRunner6 timedJobRunner6,
                        final SchedulerService schedulerService) {
        super(eventPublisher);
        this.timedJobRunner = timedJobRunner;
        this.timedJobRunner2 = timedJobRunner2;
        this.timedJobRunner3 = timedJobRunner3;
        this.timedJobRunner4 = timedJobRunner4;
        this.timedJobRunner5 = timedJobRunner5;
        this.timedJobRunner6 = timedJobRunner6;

        this.schedulerService = schedulerService;
    }

    /**
     * Use this method to register job runners and schedule periodic jobs.
     */
    @Override
    protected void onAppStart() {
        try{
            log.debug("Registering " + TimedJobRunner.NAME);
            schedulerService.registerJobRunner(TimedJobRunner.KEY, timedJobRunner);
            timedJobRunner.scheduleJob();

            log.debug("Registering " + TimedJobRunner2.NAME);
            schedulerService.registerJobRunner(TimedJobRunner2.KEY, timedJobRunner2);
            timedJobRunner2.scheduleJob();

            log.debug("Registering " + TimedJobRunner3.NAME);
            schedulerService.registerJobRunner(TimedJobRunner3.KEY, timedJobRunner3);
            timedJobRunner3.scheduleJob();

            log.debug("Registering " + TimedJobRunner4.NAME);
            schedulerService.registerJobRunner(TimedJobRunner4.KEY, timedJobRunner4);
            timedJobRunner4.scheduleJob();

            log.debug("Registering " + TimedJobRunner5.NAME);
            schedulerService.registerJobRunner(TimedJobRunner5.KEY, timedJobRunner5);
            timedJobRunner5.scheduleJob();

            log.debug("Registering " + TimedJobRunner6.NAME);
            schedulerService.registerJobRunner(TimedJobRunner6.KEY, timedJobRunner6);
            timedJobRunner6.scheduleJob();

        } catch( final Exception e){
            log.error("Error registering or running " + TimedJobRunner.NAME);
        }
    }

    /**
     * Use this method to unregister job runners.
     */
    @Override
    protected void onAppShutdown() {
        try {
            schedulerService.unregisterJobRunner(TimedJobRunner.KEY);
            schedulerService.unregisterJobRunner(TimedJobRunner2.KEY);
            schedulerService.unregisterJobRunner(TimedJobRunner3.KEY);
            schedulerService.unregisterJobRunner(TimedJobRunner4.KEY);
            schedulerService.unregisterJobRunner(TimedJobRunner5.KEY);
            schedulerService.unregisterJobRunner(TimedJobRunner6.KEY);
            log.debug("Unregistered " + TimedJobRunner.NAME);
        } catch (final Throwable t) {
            log.error("Unable to unregister job runners ", t);
        }
    }
}