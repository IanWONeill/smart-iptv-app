package com.nst.yourname.miscelleneious.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GetSpeedTestHandler extends Thread {
    boolean finished = false;
    HashMap<Integer, String> keyMap = new HashMap<>();
    double latSelf = 0.0d;
    double lonSelf = 0.0d;
    HashMap<Integer, List<String>> valueMap = new HashMap<>();

    public HashMap<Integer, String> getKeyMap() {
        return this.keyMap;
    }

    public HashMap<Integer, List<String>> getValueMap() {
        return this.valueMap;
    }

    public double getLatSelf() {
        return this.latSelf;
    }

    public double getLonSelf() {
        return this.lonSelf;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void run() {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://www.speedtest.net/speedtest-config.php").openConnection();
            httpURLConnection.getContentLength();
            if (httpURLConnection.getResponseCode() == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (readLine.contains("isp=")) {
                            this.latSelf = Double.parseDouble(readLine.split("lat=\"")[1].split(" ")[0].replace("\"", ""));
                            this.lonSelf = Double.parseDouble(readLine.split("lon=\"")[1].split(" ")[0].replace("\"", ""));
                            break;
                        }
                    } else {
                        break;
                    }
                }
                bufferedReader.close();
            }
            try {
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL("https://www.speedtest.net/speedtest-servers-static.php").openConnection();
                if (httpURLConnection2.getResponseCode() == 200) {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection2.getInputStream()));
                    int i = 0;
                    while (true) {
                        String readLine2 = bufferedReader2.readLine();
                        if (readLine2 == null) {
                            break;
                        } else if (readLine2.contains("<server url")) {
                            String str = readLine2.split("server url=\"")[1].split("\"")[0];
                            List<String> asList = Arrays.asList(readLine2.split("lat=\"")[1].split("\"")[0], readLine2.split("lon=\"")[1].split("\"")[0], readLine2.split("name=\"")[1].split("\"")[0], readLine2.split("country=\"")[1].split("\"")[0], readLine2.split("cc=\"")[1].split("\"")[0], readLine2.split("sponsor=\"")[1].split("\"")[0], readLine2.split("host=\"")[1].split("\"")[0]);
                            this.keyMap.put(Integer.valueOf(i), str);
                            this.valueMap.put(Integer.valueOf(i), asList);
                            i++;
                        }
                    }
                    bufferedReader2.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.finished = true;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
