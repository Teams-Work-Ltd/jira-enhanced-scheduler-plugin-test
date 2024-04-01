package com.teamswork.scheduler.test.component;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.scheduler.*;
import com.atlassian.scheduler.config.JobConfig;
import com.atlassian.scheduler.config.JobRunnerKey;
import com.atlassian.scheduler.config.Schedule;
import com.atlassian.scheduler.status.JobDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static com.atlassian.scheduler.config.RunMode.RUN_ONCE_PER_CLUSTER;

@SuppressWarnings("unused")
@Component
public class TimedJobRunner2 implements JobRunner {
    private static final Logger log = LoggerFactory.getLogger(TimedJobRunner2.class);
    public static final String NAME = "timed-job-runner2";
    public static final JobRunnerKey KEY = JobRunnerKey.of(TimedJobRunner2.class.getName());

    private final SchedulerService schedulerService;
    private final TimedJob timedJob;

    public TimedJobRunner2(@ComponentImport final SchedulerService schedulerService,
                           final TimedJob timedJob) {
        this.schedulerService = schedulerService;
        this.timedJob = timedJob;
    }

    public void scheduleJob() throws SchedulerServiceException {
        for (final JobDetails jobDetails : schedulerService.getJobsByJobRunnerKey(KEY)) {
            log.debug("Un-scheduling job: " + jobDetails.getJobId());
            schedulerService.unscheduleJob(jobDetails.getJobId());
        }

        // Run every minute
        String runSchedule = "0 * * ? * *";
        log.debug("Scheduling timed job runner 2 with schedule: " + runSchedule);
        final JobConfig jobConfig = JobConfig
                .forJobRunnerKey(KEY)
                .withRunMode(RUN_ONCE_PER_CLUSTER)
                .withSchedule(Schedule.forCronExpression(runSchedule));
        schedulerService.scheduleJobWithGeneratedId(jobConfig);
    }

    @Override
    public JobRunnerResponse runJob(@Nonnull final JobRunnerRequest request) {
        try {
            timedJob.execute();
            return JobRunnerResponse.success();
        } catch (final Exception e) {
            log.error(">>>>> Error running job : " + NAME, e);
            return JobRunnerResponse.failed(e);
        }
    }
}