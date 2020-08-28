package com.nst.yourname.miscelleneious.SpeedTestThread;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpUploadTest extends Thread {
    static int uploadedKByte;
    double elapsedTime = 0.0d;
    public String fileURL = "";
    double finalUploadRate = 0.0d;
    boolean finished = false;
    long startTime;
    double uploadElapsedTime = 0.0d;

    public HttpUploadTest(String str) {
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

    public boolean isFinished() {
        return this.finished;
    }

    public double getInstantUploadRate() {
        try {
            new BigDecimal(uploadedKByte);
            if (uploadedKByte < 0) {
                return 0.0d;
            }
            double currentTimeMillis = (double) (System.currentTimeMillis() - this.startTime);
            Double.isNaN(currentTimeMillis);
            this.elapsedTime = currentTimeMillis / 1000.0d;
            double d = (double) uploadedKByte;
            Double.isNaN(d);
            return round(Double.valueOf(((d / 1000.0d) * 8.0d) / this.elapsedTime).doubleValue(), 2);
        } catch (Exception unused) {
            return 0.0d;
        }
    }

    public double getFinalUploadRate() {
        return round(this.finalUploadRate, 2);
    }

    public void run() {
        try {
            URL url = new URL(this.fileURL);
            uploadedKByte = 0;
            this.startTime = System.currentTimeMillis();
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
            for (int i = 0; i < 4; i++) {
                newFixedThreadPool.execute(new HandlerUpload(url));
            }
            newFixedThreadPool.shutdown();
            while (!newFixedThreadPool.isTerminated()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException unused) {
                }
            }
            double currentTimeMillis = (double) (System.currentTimeMillis() - this.startTime);
            Double.isNaN(currentTimeMillis);
            this.uploadElapsedTime = currentTimeMillis / 1000.0d;
            double d = (double) uploadedKByte;
            Double.isNaN(d);
            this.finalUploadRate = Double.valueOf(((d / 1000.0d) * 8.0d) / this.uploadElapsedTime).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.finished = true;
    }
}
