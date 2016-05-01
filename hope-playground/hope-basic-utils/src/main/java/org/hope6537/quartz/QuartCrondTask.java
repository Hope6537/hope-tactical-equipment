package org.hope6537.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class QuartCrondTask implements Job {
    private static Logger _log = LoggerFactory.getLogger(QuartCrondTask.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        _log.info("nanoTime" + System.nanoTime());
        _log.info(String.valueOf(jobExecutionContext.getJobDetail()));
    }
}