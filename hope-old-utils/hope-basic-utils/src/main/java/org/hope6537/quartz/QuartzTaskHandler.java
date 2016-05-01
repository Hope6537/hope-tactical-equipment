package org.hope6537.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by hope6537 on 15/11/27.
 * Any Question sent to hope6537@qq.com
 */
public class QuartzTaskHandler {

    public static void main(String[] args) throws SchedulerException, ParseException {
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse("2015-11-22");
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        JobDetail job = newJob(QuartDemoTask.class)
                .withIdentity("job1", "group1")
                .build();
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();
        JobDetail crondJob = newJob(QuartCrondTask.class)
                .withIdentity("crondJob", "group2")
                .build();
        CronTrigger cronTrigger = newTrigger()
                .withIdentity("trigger2", "group2")
                .withSchedule(cronSchedule("0/20 * * * * ?"))
                .build();

        JobDetail delayJob = newJob(StatefulDumbJob.class)
                .withIdentity("statefulJob1", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
                .build();
        SimpleTrigger delayJobTrigger1 = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(date)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever())
                .build();
        SimpleTrigger delayJobTrigger2 = newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(date)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever()
                        .withMisfireHandlingInstructionNowWithExistingCount()) // set
                        // misfire instruction
                .build();


        // Tell quartz to schedule the job using our trigger
        defaultScheduler.scheduleJob(job, trigger);
        defaultScheduler.scheduleJob(crondJob, cronTrigger);
        //defaultScheduler.scheduleJob(delayJob, delayJobTrigger1);
        defaultScheduler.scheduleJob(delayJob, delayJobTrigger2);
        defaultScheduler.start();
    }
}