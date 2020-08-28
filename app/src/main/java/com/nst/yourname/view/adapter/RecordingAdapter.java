package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.database.ExternalPlayerDataBase;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.nst.yourname.model.pojo.ExternalPlayerModelClass;
import com.nst.yourname.view.activity.MxPlayerRecordingActivity;
import com.nst.yourname.view.activity.PlayExternalPlayerActivity;
import com.nst.yourname.view.activity.PlayRecordingExternalPlayerActivity;
import com.nst.yourname.view.activity.RecordingActivity;
import com.nst.yourname.view.activity.VLCPlayerRecordingActivity;
import com.nst.yourname.view.exoplayer.NSTEXOPlayerVODActivity;
import com.nst.yourname.view.ijkplayer.activities.NSTIJKPlayerVODActivity;
import com.nst.yourname.view.ijkplayer.application.Settings;
import com.nst.yourname.view.ijkplayer.widget.media.FileMediaDataSource;
import com.nst.yourname.view.ijkplayer.widget.media.InfoHudViewHolder;
import com.nst.yourname.view.ijkplayer.widget.media.MeasureHelper;
import com.nst.yourname.view.ijkplayer.widget.media.MediaPlayerCompat;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RecordingAdapter extends RecyclerView.Adapter<RecordingAdapter.MyViewHolder> {
    public static Settings mSettings;
    private static String uk;
    private static String una;
    public RecordingActivity context;
    public ArrayList<File> data = new ArrayList<>();
    private DateFormat df;
    private Date dt;
    public ArrayList<ExternalPlayerModelClass> externalPlayerList;
    private SimpleDateFormat fr;
    private SharedPreferences loginPreferencesSharedPref;
    public Boolean rq = true;
    private String ukd;
    private String unad;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.tv_recordings = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_recordings, "field 'tv_recordings'", TextView.class);
            myViewHolder.tv_file_size = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_file_size, "field 'tv_file_size'", TextView.class);
            myViewHolder.tv_date = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_date, "field 'tv_date'", TextView.class);
            myViewHolder.tv_time = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_time, "field 'tv_time'", TextView.class);
            myViewHolder.iv_delete_recording = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_delete_recording, "field 'iv_delete_recording'", ImageView.class);
            myViewHolder.ll_list_view = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ll_list_view, "field 'll_list_view'", LinearLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.tv_recordings = null;
                myViewHolder.tv_file_size = null;
                myViewHolder.tv_date = null;
                myViewHolder.tv_time = null;
                myViewHolder.iv_delete_recording = null;
                myViewHolder.ll_list_view = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_delete_recording)
        ImageView iv_delete_recording;
        @BindView(R.id.ll_list_view)
        LinearLayout ll_list_view;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_file_size)
        TextView tv_file_size;
        @BindView(R.id.tv_recordings)
        TextView tv_recordings;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.recording_list, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        File file;
        if (this.data != null && (file = this.data.get(i)) != null) {
            String string = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0).getString(AppConst.LOGIN_PREF_TIME_FORMAT, "");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(string, Locale.US);
            Date date = new Date(file.lastModified());
            Date date2 = new Date(file.lastModified());
            float length = (((float) file.length()) / 1024.0f) / 1024.0f;
            String name = file.getName();
            myViewHolder.tv_recordings.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
            myViewHolder.tv_recordings.setPaintFlags(myViewHolder.tv_recordings.getPaintFlags() | 8);
            if (length > 1024.0f) {
                double d = (double) (length / 1024.0f);
                Double.isNaN(d);
                double round = (double) Math.round(d * 100.0d);
                Double.isNaN(round);
                TextView textView = myViewHolder.tv_file_size;
                textView.setText(((float) (round / 100.0d)) + " GB");
            } else {
                double d2 = (double) length;
                Double.isNaN(d2);
                double round2 = (double) Math.round(d2 * 100.0d);
                Double.isNaN(round2);
                TextView textView2 = myViewHolder.tv_file_size;
                textView2.setText(((float) (round2 / 100.0d)) + " MB");
            }
            myViewHolder.tv_date.setText(simpleDateFormat.format(date));
            myViewHolder.tv_time.setText(simpleDateFormat2.format(date2));
            myViewHolder.ll_list_view.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.ll_list_view));
            myViewHolder.ll_list_view.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.RecordingAdapter.AnonymousClass1 */

                public void onClick(View view) {
                    RecordingAdapter.this.showPopUP(view, i, RecordingAdapter.this.data);
                }
            });
            myViewHolder.iv_delete_recording.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.RecordingAdapter.AnonymousClass2 */

                public void onClick(View view) {
                }
            });
        }
    }

    public void showPopUP(View view, final int i, final ArrayList<File> arrayList) {
        PopupMenu popupMenu = new PopupMenu(this.context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_recordings, popupMenu.getMenu());
        this.externalPlayerList = new ArrayList<>();
        this.externalPlayerList = new ExternalPlayerDataBase(this.context).getExternalPlayer();
        if (this.externalPlayerList != null && this.externalPlayerList.size() > 0) {
            for (int i2 = 0; i2 < this.externalPlayerList.size(); i2++) {
                try {
                    popupMenu.getMenu().add(0, i2, i2, this.externalPlayerList.get(i2).getAppname());
                } catch (Exception unused) {
                }
            }
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            /* class com.nst.yourname.view.adapter.RecordingAdapter.AnonymousClass3 */

            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent;
                try {
                    if (RecordingAdapter.this.rq.booleanValue() && RecordingAdapter.this.externalPlayerList != null && RecordingAdapter.this.externalPlayerList.size() > 0) {
                        int i = 0;
                        while (true) {
                            if (i >= RecordingAdapter.this.externalPlayerList.size()) {
                                break;
                            } else if (menuItem.getItemId() == i) {
                                Intent intent2 = new Intent(RecordingAdapter.this.context, PlayRecordingExternalPlayerActivity.class);
                                intent2.putExtra("url", ((File) arrayList.get(i)).getAbsolutePath());
                                intent2.putExtra(AppConst.APP_NAME, ((ExternalPlayerModelClass) RecordingAdapter.this.externalPlayerList.get(i)).getAppname());
                                intent2.putExtra(AppConst.PACKAGE_NAME, ((ExternalPlayerModelClass) RecordingAdapter.this.externalPlayerList.get(i)).getPackagename());
                                RecordingAdapter.this.context.startActivity(intent2);
                                break;
                            } else {
                                i++;
                            }
                        }
                    }
                } catch (Exception unused) {
                }
                switch (menuItem.getItemId()) {
                    case R.id.nav_delete:
                        if (RecordingAdapter.this.context != null) {
                            RecordingAdapter.this.context.showDeleteRecording(new File(((File) arrayList.get(i)).getAbsolutePath()), RecordingAdapter.this.context);
                            break;
                        }
                        break;
                    case R.id.nav_play:
                        if (RecordingAdapter.this.context != null && RecordingAdapter.this.rq.booleanValue()) {
                            String recordingsPlayerAppName = SharepreferenceDBHandler.getRecordingsPlayerAppName(RecordingAdapter.this.context);
                            if (!SharepreferenceDBHandler.getRecordingsPlayerPkgName(RecordingAdapter.this.context).equals("default") && !new ExternalPlayerDataBase(RecordingAdapter.this.context).CheckPlayerExistense(recordingsPlayerAppName)) {
                                SharepreferenceDBHandler.setRecordingsPlayer("default", "default", RecordingAdapter.this.context);
                            }
                            String recordingsPlayerPkgName = SharepreferenceDBHandler.getRecordingsPlayerPkgName(RecordingAdapter.this.context);
                            if (recordingsPlayerPkgName != null && !recordingsPlayerPkgName.equalsIgnoreCase("default")) {
                                String recordingsPlayerAppName2 = SharepreferenceDBHandler.getRecordingsPlayerAppName(RecordingAdapter.this.context);
                                Intent intent3 = new Intent(RecordingAdapter.this.context, PlayExternalPlayerActivity.class);
                                intent3.putExtra("url", ((File) arrayList.get(i)).getAbsolutePath());
                                intent3.putExtra(AppConst.PACKAGE_NAME, recordingsPlayerPkgName);
                                intent3.putExtra(AppConst.APP_NAME, recordingsPlayerAppName2);
                                RecordingAdapter.this.context.startActivity(intent3);
                                break;
                            } else {
                                Settings unused2 = RecordingAdapter.mSettings = new Settings(RecordingAdapter.this.context);
                                if (SharepreferenceDBHandler.getCatchUpPlayerPkgName(RecordingAdapter.this.context).equals("default")) {
                                    if (RecordingAdapter.mSettings.getPlayer() == 3) {
                                        intent = new Intent(RecordingAdapter.this.context, NSTEXOPlayerVODActivity.class);
                                    } else {
                                        intent = new Intent(RecordingAdapter.this.context, NSTIJKPlayerVODActivity.class);
                                    }
                                    intent.putExtra("type", "recording");
                                    intent.putExtra("VIDEO_NUM", i);
                                    intent.putExtra("VIDEO_PATH", ((File) arrayList.get(i)).getAbsolutePath());
                                    RecordingAdapter.this.context.startActivity(intent);
                                    break;
                                }
                            }
                        }
                        break;
                    case R.id.nav_play_with_mx:
                        if (RecordingAdapter.this.context != null) {
                            Intent intent4 = new Intent(RecordingAdapter.this.context, MxPlayerRecordingActivity.class);
                            intent4.putExtra("VIDEO_PATH", ((File) arrayList.get(i)).getAbsolutePath());
                            RecordingAdapter.this.context.startActivity(intent4);
                            break;
                        }
                        break;
                    case R.id.nav_play_with_vlc:
                        if (RecordingAdapter.this.context != null) {
                            Intent intent5 = new Intent(RecordingAdapter.this.context, VLCPlayerRecordingActivity.class);
                            intent5.putExtra("VIDEO_PATH", ((File) arrayList.get(i)).getAbsolutePath());
                            RecordingAdapter.this.context.startActivity(intent5);
                            break;
                        }
                        break;
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            /* class com.nst.yourname.view.adapter.RecordingAdapter.AnonymousClass4 */

            public void onDismiss(PopupMenu popupMenu) {
            }
        });
        popupMenu.show();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.data.size();
    }

    public RecordingAdapter(RecordingActivity recordingActivity, ArrayList<File> arrayList) {
        this.data = arrayList;
        this.context = recordingActivity;
        this.ukd = com.nst.yourname.miscelleneious.common.Utils.ukde(FileMediaDataSource.apn());
        una = recordingActivity.getApplicationContext().getPackageName();
        uk = getApplicationName(recordingActivity);
        this.unad = com.nst.yourname.miscelleneious.common.Utils.ukde(MeasureHelper.pnm());
        this.fr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        this.dt = new Date();
        if (df(this.fr, this.fr.format(new Date(MediaPlayerCompat.cit(recordingActivity))), this.df.format(this.dt)) >= ((long) InfoHudViewHolder.ux()) && this.ukd != null && this.unad != null) {
            if (!uk.equals(this.ukd) || !(this.ukd == null || this.unad == null || una.equals(this.unad))) {
                this.rq = false;
            }
        }
    }

    public static long df(SimpleDateFormat simpleDateFormat, String str, String str2) {
        try {
            return TimeUnit.DAYS.convert(simpleDateFormat.parse(str2).getTime() - simpleDateFormat.parse(str).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getApplicationName(Context context2) {
        return String.valueOf(context2.getApplicationInfo().loadLabel(context2.getPackageManager()));
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
                    f = 1.05f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_list_categories_focused);
            } else if (!z) {
                if (z) {
                    f = 1.05f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_list_categories);
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
}
