/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sopho.raddiorecorder;

/**
 *
 * @author Dardenne
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
https://r8---sn-vg5obxcx-cg0e.googlevideo.com/videoplayback?lmt=1551687941672234&c=WEB&initcwndbps=1322500&ei=bxWAXKbCNYzB-gbBnZLYCg&fvip=6&txp=2216222&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Cratebypass%2Crequiressl%2Csource%2Cexpire&expire=1551919568&ipbits=0&ratebypass=yes&itag=22&mn=sn-vg5obxcx-cg0e%2Csn-5hne6n7e&pl=16&mm=31%2C29&requiressl=yes&dur=28922.612&id=o-AOx43lTUcSspYUAi8Qfg_a1SVTvhvXhc4GKRCFRbLBxL&source=youtube&key=yt6&ip=94.109.184.81&mime=video%2Fmp4&mv=m&mt=1551897818&ms=au%2Crdu&signature=undefined
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pete Smith
 */
public class Recorder extends Thread {

    private final String fileName;
    private final String url;
    private boolean stop;

    public Recorder(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    @Override
    public void run() {

        URLConnection conn;
        try {
            conn = new URL(url).openConnection(); // fill in the correct http address
            try (var is = conn.getInputStream()) {
                OutputStream outstream = new FileOutputStream(new File(fileName)); // fill in correct file name.
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    outstream.write(buffer, 0, len);
                    // System.out.println(len);
                    outstream.flush();
                    if (stop) {
                        break;
                    }
                }
                outstream.close(); // in a finally block of course
            } // fill in correct file name.
            System.out.println("DONE!");
        } catch (IOException e) {
            Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    void close() {
        try {
            this.stop = true;
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
