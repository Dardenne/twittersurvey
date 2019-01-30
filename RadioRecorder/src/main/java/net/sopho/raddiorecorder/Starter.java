/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sopho.raddiorecorder;

import java.util.Date;
import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Dardenne
 */
public class Starter {

    public void run() throws SchedulerException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        JobDetail job = newJob(Job.class)
                .withIdentity("job1", "group1")
                .build();
        /*
        job.getJobDataMap().put(Job.duration, 60L);
        job.getJobDataMap().put(Job.url, "http://e1-live-mp3-128.scdn.arkena.com/europe1.mp3");
        job.getJobDataMap().put(Job.fileName, "europe1");
        */
        /*
        var trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0 * * ? * * *"))
                .build();
         */
        // compute a time that is on the next round minute
        Date runTime = evenMinuteDate(new Date());

// Trigger the job to run on the next round minute
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(runTime)
                .build();
        sched.scheduleJob(job, trigger);
    }

}
