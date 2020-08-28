package com.nst.yourname.miscelleneious.SpeedTestThread;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class DownloadTest extends Thread {
    double downloadElapsedTime = 0.0d;
    int downloadedByte = 0;
    long endTime = 0;
    public String fileURL = "";
    double finalDownloadRate = 0.0d;
    boolean finished = false;
    HttpURLConnection httpConn = null;
    double instantDownloadRate = 0.0d;
    long startTime = 0;
    int timeout = 15;

    public DownloadTest(String str) {
        this.fileURL = str;
    }

    private double round(double d, int i) {
        if (i >= 0) {
            try {
                return new BigDecimal(d).setScale(i, RoundingMode.HALF_UP).doubleValue();
            } catch (Exception unused) {
                return 0.0d;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getInstantDownloadRate() {
        return this.instantDownloadRate;
    }

    public void setInstantDownloadRate(int i, double d) {
        if (i >= 0) {
            double d2 = (double) ((i * 8) / 1000000);
            Double.isNaN(d2);
            this.instantDownloadRate = round(Double.valueOf(d2 / d).doubleValue(), 2);
            return;
        }
        this.instantDownloadRate = 0.0d;
    }

    public double getFinalDownloadRate() {
        return round(this.finalDownloadRate, 2);
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void run() {
        int i = 0;
        this.downloadedByte = 0;
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.fileURL + "random4000x4000.jpg");
        arrayList.add(this.fileURL + "random3000x3000.jpg");
        this.startTime = System.currentTimeMillis();
        Iterator it = arrayList.iterator();
        loop0:
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            try {
                this.httpConn = (HttpURLConnection) new URL((String) it.next()).openConnection();
                i = this.httpConn.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i == 200) {
                try {
                    byte[] bArr = new byte[10240];
                    InputStream inputStream = this.httpConn.getInputStream();
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            inputStream.close();
                            this.httpConn.disconnect();
                            break;
                        }
                        this.downloadedByte += read;
                        this.endTime = System.currentTimeMillis();
                        double d = (double) (this.endTime - this.startTime);
                        Double.isNaN(d);
                        this.downloadElapsedTime = d / 1000.0d;
                        setInstantDownloadRate(this.downloadedByte, this.downloadElapsedTime);
                        if (this.downloadElapsedTime >= ((double) this.timeout)) {
                            break loop0;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                System.out.println("Link not found...");
            }
        }
        this.endTime = System.currentTimeMillis();
        double d2 = (double) (this.endTime - this.startTime);
        Double.isNaN(d2);
        this.downloadElapsedTime = d2 / 1000.0d;
        double d3 = (double) (this.downloadedByte * 8);
        Double.isNaN(d3);
        this.finalDownloadRate = (d3 / 1000000.0d) / this.downloadElapsedTime;
        this.finished = true;
    }
}
