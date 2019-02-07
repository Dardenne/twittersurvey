package com.mkyong.quartz;

import net.sopho.raddiorecorder.Job;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggerExample {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static void main(String[] args) throws Exception {
        CronTriggerExample app = new CronTriggerExample();
        app.run(args);

    }

    public void run(String[] args) throws Exception {
        JobDetail job = JobBuilder.newJob(Job.class)
                .withIdentity("dummyJobName", "group1").build();
        job.getJobDataMap().put("duration", "60");
        job.getJobDataMap().put("url", "http://e1-live-mp3-128.scdn.arkena.com/europe1.mp3");
        job.getJobDataMap().put("fileName", isWindows() ? "d:/Musique/europe1" : "/home/dardenne/Musique/europe1");
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0 59 0 ? * * *"))
                .build();

        //schedule it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }

    private static boolean isWindows() {
        return (OS.contains("win"));
    }
}
