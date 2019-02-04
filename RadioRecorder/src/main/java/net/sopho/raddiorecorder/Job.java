/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sopho.raddiorecorder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dardenne
 */
public class Job implements org.quartz.Job {

    public String url;
    private String fileName;
    private String duration;
    private Recorder recorder = null;
    private long startTime = 0;
    private long maxTime = 0;
    private Calendar calendar;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY.MM.DD-HH.mm");
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Job.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        url = dataMap.getString("url");
        fileName = dataMap.getString("fileName");
        duration = dataMap.getString("duration");
        System.out.println("Start");
        startJob(context);
        return;
    }

    private void startJob(JobExecutionContext context) {
        JobKey jobKey = context.getJobDetail().getKey();
        Logger.getLogger("SimpleJob says: " + jobKey + " executing at " + new Date());
        calendar = Calendar.getInstance();
        recorder = new Recorder(url, fileName + '.' + dateFormat.format(new Date()) + ".mp3");
        recorder.start();
        startTime = calendar.getTimeInMillis();
        maxTime = startTime + ((Long.valueOf(duration) * 60000));
        while (true) {
            try {
                Thread.sleep(1000);
                long actual = Calendar.getInstance().getTimeInMillis();
                LOG.info("Max: '{}', actual: '{}'.", maxTime, actual);
                if (actual > maxTime) {
                    break;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Job.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        recorder.close();
        recorder.stop();
    }
}
