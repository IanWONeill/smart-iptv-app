package com.nst.yourname.view.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.SpeedTestThread.DownloadTest;
import com.nst.yourname.miscelleneious.SpeedTestThread.HttpUploadTest;
import com.nst.yourname.miscelleneious.SpeedTestThread.PingTest;
import com.nst.yourname.miscelleneious.common.GetSpeedTestHandler;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

@SuppressWarnings("all")
public class SpeedTestActivity extends AppCompatActivity {
    static int lastPosition;
    static int position;
    @BindView(R.id.bar_speed)
    ImageView bar_speed;
    Context context;
    @BindView(R.id.down_text)
    TextView down_text;
    GetSpeedTestHandler getSpeedTestHandler;
    @BindView(R.id.graph_chart)
    LinearLayout graph_chart;
    @BindView(R.id.ping_text)
    TextView ping_text;
    RotateAnimation rotate;
    HashSet<String> tempBlackList;
    @BindView(R.id.test_button)
    Button test_button;
    @BindView(R.id.upl_text)
    TextView upl_text;

    public int getPositionByRate(double d) {
        if (d <= 1.0d) {
            return (int) (d * 30.0d);
        }
        if (d <= 10.0d) {
            return ((int) (d * 6.0d)) + 30;
        }
        if (d <= 30.0d) {
            return ((int) ((d - 10.0d) * 3.0d)) + 90;
        }
        if (d <= 50.0d) {
            return ((int) ((d - 30.0d) * 1.5d)) + ModuleDescriptor.MODULE_VERSION;
        }
        if (d <= 100.0d) {
            return ((int) ((d - 50.0d) * 1.2d)) + 180;
        }
        return 0;
    }

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.SupportActivity, android.support.v4.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_speed_test);
        ButterKnife.bind(this);
        this.context = this;
        this.tempBlackList = new HashSet<>();
        this.getSpeedTestHandler = new GetSpeedTestHandler();
        this.getSpeedTestHandler.start();
        if (this.test_button != null) {
            this.test_button.setOnFocusChangeListener(new OnFocusChangeAccountListener(this.test_button));
        }
        initialize();
    }

    private void initialize() {
        this.test_button.setOnClickListener(new View.OnClickListener() {
            /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass1 */

            public void onClick(View view) {
                SpeedTestActivity.this.test_button.setEnabled(false);
                SpeedTestActivity.this.testCheckSpeed();
            }
        });
    }

    public void testCheckSpeed() {
        if (this.getSpeedTestHandler == null) {
            this.getSpeedTestHandler = new GetSpeedTestHandler();
            this.getSpeedTestHandler.start();
        }
        this.graph_chart.setVisibility(0);
        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
        try {
            new Thread(new Runnable() {
                /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2 */

                /* JADX WARNING: Removed duplicated region for block: B:55:0x0383  */
                /* JADX WARNING: Removed duplicated region for block: B:62:0x03d4  */
                /* JADX WARNING: Removed duplicated region for block: B:73:0x03f9  */
                /* JADX WARNING: Removed duplicated region for block: B:74:0x0401  */
                /* JADX WARNING: Removed duplicated region for block: B:77:0x0408  */
                /* JADX WARNING: Removed duplicated region for block: B:80:0x0414  */
                public void run() {
                    ArrayList arrayList;
                    XYMultipleSeriesRenderer xYMultipleSeriesRenderer;
                    boolean z;
                    SpeedTestActivity.this.runOnUiThread(new Runnable() {
                        /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass1 */

                        public void run() {
                            SpeedTestActivity.this.test_button.setText(SpeedTestActivity.this.getResources().getString(R.string.ping_based));
                        }
                    });
                    int i = IjkMediaCodecInfo.RANK_LAST_CHANCE;
                    while (!SpeedTestActivity.this.getSpeedTestHandler.isFinished()) {
                        i--;
                        try {
                            Thread.sleep(100);
                            continue;
                        } catch (InterruptedException unused) {
                        }
                        if (i <= 0) {
                            SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass2 */

                                public void run() {
                                    Toast.makeText(SpeedTestActivity.this.getApplicationContext(), "No Connection...", 1).show();
                                    SpeedTestActivity.this.test_button.setEnabled(true);
                                    SpeedTestActivity.this.test_button.setText(SpeedTestActivity.this.getResources().getString(R.string.restart_test));
                                }
                            });
                            SpeedTestActivity.this.getSpeedTestHandler = null;
                            return;
                        }
                    }
                    HashMap<Integer, String> keyMap = SpeedTestActivity.this.getSpeedTestHandler.getKeyMap();
                    HashMap<Integer, List<String>> valueMap = SpeedTestActivity.this.getSpeedTestHandler.getValueMap();
                    double latSelf = SpeedTestActivity.this.getSpeedTestHandler.getLatSelf();
                    double lonSelf = SpeedTestActivity.this.getSpeedTestHandler.getLonSelf();
                    double d = 1.9349458E7d;
                    double d2 = 0.0d;
                    int i2 = 0;
                    for (Integer num : keyMap.keySet()) {
                        int intValue = num.intValue();
                        if (!SpeedTestActivity.this.tempBlackList.contains(valueMap.get(Integer.valueOf(intValue)).get(5))) {
                            Location location = new Location("Source");
                            location.setLatitude(latSelf);
                            location.setLongitude(lonSelf);
                            List<String> list = valueMap.get(Integer.valueOf(intValue));
                            double d3 = latSelf;
                            Location location2 = new Location("Dest");
                            location2.setLatitude(Double.parseDouble(list.get(0)));
                            location2.setLongitude(Double.parseDouble(list.get(1)));
                            double distanceTo = (double) location.distanceTo(location2);
                            if (d > distanceTo) {
                                d2 = distanceTo;
                                d = d2;
                                i2 = intValue;
                            }
                            latSelf = d3;
                        }
                    }
                    String str = keyMap.get(Integer.valueOf(i2));
                    final List<String> list2 = valueMap.get(Integer.valueOf(i2));
                    if (list2 == null) {
                        SpeedTestActivity.this.runOnUiThread(new Runnable() {
                            /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass3 */

                            public void run() {
                                SpeedTestActivity.this.test_button.setText(SpeedTestActivity.this.getResources().getString(R.string.problem_host));
                            }
                        });
                    }
                    double finalD = d2;
                    SpeedTestActivity.this.runOnUiThread(new Runnable() {
                        /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass4 */

                        public void run() {
                            SpeedTestActivity.this.test_button.setText(String.format("Host Location: %s [Distance: %s km]", list2.get(2), new DecimalFormat("#.##").format(finalD / 1000.0d)));
                        }
                    });
                    final LinearLayout linearLayout = (LinearLayout) SpeedTestActivity.this.findViewById(R.id.ping);
                    XYSeriesRenderer xYSeriesRenderer = new XYSeriesRenderer();
                    XYSeriesRenderer.FillOutsideLine fillOutsideLine = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                    fillOutsideLine.setColor(Color.parseColor("#4d5a6a"));
                    xYSeriesRenderer.addFillOutsideLine(fillOutsideLine);
                    xYSeriesRenderer.setDisplayChartValues(false);
                    xYSeriesRenderer.setShowLegendItem(false);
                    xYSeriesRenderer.setColor(Color.parseColor("#04D9F5"));
                    xYSeriesRenderer.setLineWidth(5.0f);
                    XYMultipleSeriesRenderer xYMultipleSeriesRenderer2 = new XYMultipleSeriesRenderer();
                    xYMultipleSeriesRenderer2.setXLabels(0);
                    xYMultipleSeriesRenderer2.setYLabels(0);
                    xYMultipleSeriesRenderer2.setZoomEnabled(false);
                    xYMultipleSeriesRenderer2.setXAxisColor(Color.parseColor("#647488"));
                    xYMultipleSeriesRenderer2.setYAxisColor(Color.parseColor("#2F3C4C"));
                    xYMultipleSeriesRenderer2.setPanEnabled(true, true);
                    xYMultipleSeriesRenderer2.setZoomButtonsVisible(false);
                    xYMultipleSeriesRenderer2.setMarginsColor(Color.argb(0, 255, 0, 0));
                    xYMultipleSeriesRenderer2.addSeriesRenderer(xYSeriesRenderer);
                    final LinearLayout linearLayout2 = (LinearLayout) SpeedTestActivity.this.findViewById(R.id.download);
                    XYSeriesRenderer xYSeriesRenderer2 = new XYSeriesRenderer();
                    XYSeriesRenderer.FillOutsideLine fillOutsideLine2 = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                    fillOutsideLine2.setColor(Color.parseColor("#4d5a6a"));
                    xYSeriesRenderer2.addFillOutsideLine(fillOutsideLine2);
                    xYSeriesRenderer2.setDisplayChartValues(false);
                    xYSeriesRenderer2.setColor(Color.parseColor("#04D9F5"));
                    xYSeriesRenderer2.setShowLegendItem(false);
                    xYSeriesRenderer2.setLineWidth(5.0f);
                    final XYMultipleSeriesRenderer xYMultipleSeriesRenderer3 = new XYMultipleSeriesRenderer();
                    xYMultipleSeriesRenderer3.setXLabels(0);
                    xYMultipleSeriesRenderer3.setYLabels(0);
                    xYMultipleSeriesRenderer3.setZoomEnabled(false);
                    xYMultipleSeriesRenderer3.setXAxisColor(Color.parseColor("#647488"));
                    xYMultipleSeriesRenderer3.setYAxisColor(Color.parseColor("#2F3C4C"));
                    xYMultipleSeriesRenderer3.setPanEnabled(false, false);
                    xYMultipleSeriesRenderer3.setZoomButtonsVisible(false);
                    xYMultipleSeriesRenderer3.setMarginsColor(Color.argb(0, 255, 0, 0));
                    xYMultipleSeriesRenderer3.addSeriesRenderer(xYSeriesRenderer2);
                    final LinearLayout linearLayout3 = (LinearLayout) SpeedTestActivity.this.findViewById(R.id.upload);
                    XYSeriesRenderer xYSeriesRenderer3 = new XYSeriesRenderer();
                    XYSeriesRenderer.FillOutsideLine fillOutsideLine3 = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                    fillOutsideLine3.setColor(Color.parseColor("#4d5a6a"));
                    xYSeriesRenderer3.addFillOutsideLine(fillOutsideLine3);
                    xYSeriesRenderer3.setDisplayChartValues(false);
                    xYSeriesRenderer3.setColor(Color.parseColor("#04D9F5"));
                    xYSeriesRenderer3.setShowLegendItem(false);
                    xYSeriesRenderer3.setLineWidth(5.0f);
                    final XYMultipleSeriesRenderer xYMultipleSeriesRenderer4 = new XYMultipleSeriesRenderer();
                    xYMultipleSeriesRenderer4.setXLabels(0);
                    xYMultipleSeriesRenderer4.setYLabels(0);
                    xYMultipleSeriesRenderer4.setZoomEnabled(false);
                    xYMultipleSeriesRenderer4.setXAxisColor(Color.parseColor("#647488"));
                    xYMultipleSeriesRenderer4.setYAxisColor(Color.parseColor("#2F3C4C"));
                    xYMultipleSeriesRenderer4.setPanEnabled(false, false);
                    xYMultipleSeriesRenderer4.setZoomButtonsVisible(false);
                    xYMultipleSeriesRenderer4.setMarginsColor(Color.argb(0, 255, 0, 0));
                    xYMultipleSeriesRenderer4.addSeriesRenderer(xYSeriesRenderer3);
                    SpeedTestActivity.this.runOnUiThread(new Runnable() {
                        /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass5 */

                        public void run() {
                            SpeedTestActivity.this.ping_text.setText("0 ms");
                            linearLayout.removeAllViews();
                            SpeedTestActivity.this.down_text.setText("0 Mbps");
                            linearLayout2.removeAllViews();
                            SpeedTestActivity.this.upl_text.setText("0 Mbps");
                            linearLayout3.removeAllViews();
                        }
                    });
                    ArrayList arrayList2 = new ArrayList();
                    final ArrayList arrayList3 = new ArrayList();
                    final ArrayList arrayList4 = new ArrayList();
                    Boolean bool = false;
                    Boolean bool2 = false;
                    Boolean bool3 = false;
                    Boolean bool4 = false;
                    Boolean bool5 = false;
                    Boolean bool6 = false;
                    final PingTest pingTest = new PingTest(list2.get(6).replace(":8080", ""), 6);
                    boolean z2 = true;
                    final DownloadTest downloadTest = new DownloadTest(str.replace(str.split("/")[str.split("/").length - 1], ""));
                    final HttpUploadTest httpUploadTest = new HttpUploadTest(str);
                    while (true) {
                        if (!bool4.booleanValue()) {
                            pingTest.start();
                            bool4 = Boolean.valueOf(z2);
                        }
                        if (bool5.booleanValue() && !bool.booleanValue()) {
                            downloadTest.start();
                            bool = Boolean.valueOf(z2);
                        }
                        if (bool2.booleanValue() && !bool3.booleanValue()) {
                            httpUploadTest.start();
                            bool3 = Boolean.valueOf(z2);
                        }
                        if (!bool5.booleanValue()) {
                            arrayList2.add(Double.valueOf(pingTest.getInstantRtt()));
                            SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass7 */

                                public void run() {
                                    TextView textView = SpeedTestActivity.this.ping_text;
                                    textView.setText(decimalFormat.format(pingTest.getInstantRtt()) + " ms");
                                }
                            });
                            ArrayList finalArrayList = arrayList2;
                            XYMultipleSeriesRenderer finalXYMultipleSeriesRenderer = xYMultipleSeriesRenderer2;
                            SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass8 */

                                public void run() {
                                    XYSeries xYSeries = new XYSeries("");
                                    xYSeries.setTitle("");
                                    int i = 0;
                                    List<Double> doubles = new ArrayList(finalArrayList);
                                    for (Double d : /*new ArrayList(finalArrayList)*/doubles) {
                                        xYSeries.add((double) i, d.doubleValue());
                                        i++;
                                    }
                                    XYMultipleSeriesDataset xYMultipleSeriesDataset = new XYMultipleSeriesDataset();
                                    xYMultipleSeriesDataset.addSeries(xYSeries);
                                    linearLayout.addView(ChartFactory.getLineChartView(SpeedTestActivity.this.getBaseContext(), xYMultipleSeriesDataset, finalXYMultipleSeriesRenderer), 0);
                                }
                            });
                        } else if (pingTest.getAvgRtt() == 0.0d) {
                            System.out.println("Ping error...");
                        } else {
                            SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass6 */

                                public void run() {
                                    TextView textView = SpeedTestActivity.this.ping_text;
                                    textView.setText(decimalFormat.format(pingTest.getAvgRtt()) + " ms");
                                }
                            });
                        }
                        if (bool5.booleanValue()) {
                            if (!bool2.booleanValue()) {
                                xYMultipleSeriesRenderer = xYMultipleSeriesRenderer2;
                                arrayList = arrayList2;
                                double instantDownloadRate = downloadTest.getInstantDownloadRate();
                                arrayList3.add(Double.valueOf(instantDownloadRate));
                                SpeedTestActivity.position = SpeedTestActivity.this.getPositionByRate(instantDownloadRate);
                                SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                    /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass10 */

                                    public void run() {
                                        SpeedTestActivity.this.rotate = new RotateAnimation((float) SpeedTestActivity.lastPosition, (float) SpeedTestActivity.position, 1, 0.5f, 1, 0.5f);
                                        SpeedTestActivity.this.rotate.setInterpolator(new LinearInterpolator());
                                        SpeedTestActivity.this.rotate.setDuration(100);
                                        SpeedTestActivity.this.bar_speed.startAnimation(SpeedTestActivity.this.rotate);
                                        TextView textView = SpeedTestActivity.this.down_text;
                                        textView.setText(decimalFormat.format(downloadTest.getInstantDownloadRate()) + " Mbps");
                                    }
                                });
                                SpeedTestActivity.lastPosition = SpeedTestActivity.position;
                                SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                    /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass11 */

                                    public void run() {
                                        XYSeries xYSeries = new XYSeries("");
                                        xYSeries.setTitle("");
                                        int i = 0;
                                        List<Double> doubles = new ArrayList(arrayList3);
                                        for (Double d : /*new ArrayList(arrayList3)*/doubles) {
                                            xYSeries.add((double) i, d.doubleValue());
                                            i++;
                                        }
                                        XYMultipleSeriesDataset xYMultipleSeriesDataset = new XYMultipleSeriesDataset();
                                        xYMultipleSeriesDataset.addSeries(xYSeries);
                                        linearLayout2.addView(ChartFactory.getLineChartView(SpeedTestActivity.this.getBaseContext(), xYMultipleSeriesDataset, xYMultipleSeriesRenderer3), 0);
                                    }
                                });
                                if (!bool2.booleanValue()) {
                                    if (!bool6.booleanValue()) {
                                        double instantUploadRate = httpUploadTest.getInstantUploadRate();
                                        arrayList4.add(Double.valueOf(instantUploadRate));
                                        SpeedTestActivity.position = SpeedTestActivity.this.getPositionByRate(instantUploadRate);
                                        SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                            /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass13 */

                                            public void run() {
                                                SpeedTestActivity.this.rotate = new RotateAnimation((float) SpeedTestActivity.lastPosition, (float) SpeedTestActivity.position, 1, 0.5f, 1, 0.5f);
                                                SpeedTestActivity.this.rotate.setInterpolator(new LinearInterpolator());
                                                SpeedTestActivity.this.rotate.setDuration(100);
                                                SpeedTestActivity.this.bar_speed.startAnimation(SpeedTestActivity.this.rotate);
                                                TextView textView = SpeedTestActivity.this.upl_text;
                                                textView.setText(decimalFormat.format(httpUploadTest.getInstantUploadRate()) + " Mbps");
                                            }
                                        });
                                        SpeedTestActivity.lastPosition = SpeedTestActivity.position;
                                        SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                            /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass14 */

                                            public void run() {
                                                XYSeries xYSeries = new XYSeries("");
                                                xYSeries.setTitle("");
                                                int i = 0;
                                                List<Double> doubles = new ArrayList(arrayList4);
                                                for (Double d : /*new ArrayList(arrayList4)*/doubles) {
                                                    if (i == 0) {
                                                        d = Double.valueOf(0.0d);
                                                    }
                                                    xYSeries.add((double) i, d.doubleValue());
                                                    i++;
                                                }
                                                XYMultipleSeriesDataset xYMultipleSeriesDataset = new XYMultipleSeriesDataset();
                                                xYMultipleSeriesDataset.addSeries(xYSeries);
                                                linearLayout3.addView(ChartFactory.getLineChartView(SpeedTestActivity.this.getBaseContext(), xYMultipleSeriesDataset, xYMultipleSeriesRenderer4), 0);
                                            }
                                        });
                                    } else if (httpUploadTest.getFinalUploadRate() == 0.0d) {
                                        System.out.println("Upload error...");
                                    } else {
                                        SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                            /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass12 */

                                            public void run() {
                                                TextView textView = SpeedTestActivity.this.upl_text;
                                                textView.setText(decimalFormat.format(httpUploadTest.getFinalUploadRate()) + " Mbps");
                                            }
                                        });
                                    }
                                }
                                if (bool5.booleanValue() || !bool2.booleanValue() || !httpUploadTest.isFinished()) {
                                    if (!pingTest.isFinished()) {
                                        z = true;
                                        bool5 = true;
                                    } else {
                                        z = true;
                                    }
                                    if (downloadTest.isFinished()) {
                                        bool2 = Boolean.valueOf(z);
                                    }
                                    if (httpUploadTest.isFinished()) {
                                        bool6 = Boolean.valueOf(z);
                                    }
                                    if (bool4.booleanValue() || bool5.booleanValue()) {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            Thread.sleep(300);
                                        } catch (InterruptedException unused2) {
                                        }
                                    }
                                    xYMultipleSeriesRenderer2 = xYMultipleSeriesRenderer;
                                    arrayList2 = arrayList;
                                    z2 = true;
                                } else {
                                    SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                        /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass15 */

                                        public void run() {
                                            SpeedTestActivity.this.test_button.setEnabled(true);
                                            SpeedTestActivity.this.test_button.setText(SpeedTestActivity.this.getResources().getString(R.string.restart_test));
                                        }
                                    });
                                    return;
                                }
                            } else if (downloadTest.getFinalDownloadRate() == 0.0d) {
                                System.out.println("Download error...");
                            } else {
                                SpeedTestActivity.this.runOnUiThread(new Runnable() {
                                    /* class com.nst.yourname.view.activity.SpeedTestActivity.AnonymousClass2.AnonymousClass9 */

                                    public void run() {
                                        TextView textView = SpeedTestActivity.this.down_text;
                                        textView.setText(decimalFormat.format(downloadTest.getFinalDownloadRate()) + " Mbps");
                                    }
                                });
                            }
                        }
                        xYMultipleSeriesRenderer = xYMultipleSeriesRenderer2;
                        arrayList = arrayList2;
                        if (!bool2.booleanValue()) {
                        }
                        if (bool5.booleanValue()) {
                        }
                        if (!pingTest.isFinished()) {
                        }
                        if (downloadTest.isFinished()) {
                        }
                        if (httpUploadTest.isFinished()) {
                        }
                        if (bool4.booleanValue()) {
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException unused3) {
                        }
                        xYMultipleSeriesRenderer2 = xYMultipleSeriesRenderer;
                        arrayList2 = arrayList;
                        z2 = true;
                    }
                }
            }).start();
        } catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), 0).show();
        }
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.12f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                if (this.view.getTag() != null && this.view.getTag().equals("1")) {
                    SpeedTestActivity.this.test_button.setBackgroundResource(R.drawable.blue_btn_effect);
                }
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                if (this.view != null && this.view.getTag() != null && this.view.getTag().equals("1")) {
                    SpeedTestActivity.this.test_button.setBackgroundResource(R.drawable.black_button_dark);
                }
            }
        }

        private void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }

    @Override // android.support.v4.app.FragmentActivity
    public void onResume() {
        super.onResume();
        this.getSpeedTestHandler = new GetSpeedTestHandler();
        this.getSpeedTestHandler.start();
    }
}
