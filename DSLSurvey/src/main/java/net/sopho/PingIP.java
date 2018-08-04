package net.sopho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingIP {

    public static void runSystemCommand(String command) {

        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String s = "";
            // reading output stream of the command
            while ((s = inputStream.readLine()) != null) {
                System.out.println("----->" + s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String ip = "google.com";
        runSystemCommand("ping -n 1 " + ip);

    }
}
