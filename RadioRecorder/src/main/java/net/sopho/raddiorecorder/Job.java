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
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 *
 * @author Dardenne
 */
public class Job implements org.quartz.Job {

    static String url;
    static String fileName;
    static String duration;
    private Recorder recorder = null;
    private long startTime = 0;
    private long maxTime = 0;
    private Calendar calendar;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY.MM.DD-HH.mm");

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        Logger.getLogger("SimpleJob says: " + jobKey + " executing at " + new Date());
        calendar = Calendar.getInstance();
        recorder = new Recorder(url, fileName + dateFormat.format(new Date()));
        recorder.start();
        startTime = calendar.getTimeInMillis();
        maxTime = startTime + (Long.valueOf(duration) * 60000);
        while (true) {
            try {
                Thread.sleep(1000);
                if (calendar.getTimeInMillis() > maxTime) {
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
