package com.nst.yourname.miscelleneious.SpeedTestThread;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: HttpUploadTest */
class HandlerUpload extends Thread {
    URL url;

    public HandlerUpload(URL url2) {
        this.url = url2;
    }

    public void run() {
        byte[] bArr = new byte[153600];
        long currentTimeMillis = System.currentTimeMillis();
        while (true) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) this.url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.write(bArr, 0, bArr.length);
                dataOutputStream.flush();
                httpURLConnection.getResponseCode();
                double d = (double) HttpUploadTest.uploadedKByte;
                double length = (double) bArr.length;
                Double.isNaN(length);
                Double.isNaN(d);
                HttpUploadTest.uploadedKByte = (int) (d + (length / 1024.0d));
                double currentTimeMillis2 = (double) (System.currentTimeMillis() - currentTimeMillis);
                Double.isNaN(currentTimeMillis2);
                if (currentTimeMillis2 / 1000.0d < ((double) 10)) {
                    dataOutputStream.close();
                    httpURLConnection.disconnect();
                } else {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
