package com.nst.yourname.miscelleneious.SpeedTestThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PingTest extends Thread {
    double avgRtt = 0.0d;
    int count;
    boolean finished = false;
    double instantRtt = 0.0d;
    HashMap<String, Object> result = new HashMap<>();
    String server = "";
    boolean started = false;

    public PingTest(String str, int i) {
        this.server = str;
        this.count = i;
    }

    public HashMap<String, Object> getResult() {
        return this.result;
    }

    public String getServer() {
        return this.server;
    }

    public int getCount() {
        return this.count;
    }

    public double getInstantRtt() {
        return this.instantRtt;
    }

    public double getAvgRtt() {
        return this.avgRtt;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void run() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ping", "-c" + this.count, this.server);
            processBuilder.redirectErrorStream(true);
            Process start = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(start.getInputStream()));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    if (readLine.contains("icmp_seq")) {
                        this.instantRtt = Double.parseDouble(readLine.split(" ")[readLine.split(" ").length - 2].replace("time=", ""));
                    }
                    if (readLine.startsWith("rtt ")) {
                        this.avgRtt = Double.parseDouble(readLine.split("/")[4]);
                        break;
                    }
                } else {
                    break;
                }
            }
            start.waitFor();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        this.finished = true;
    }
}
