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
