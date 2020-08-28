package com.nst.yourname.view.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Paint;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class UtilsMethods {
    public static int CHECK_CONNECTION;
    public static InputFilter EMOJI_FILTER = new InputFilter() {
        /* class com.nst.yourname.view.utility.UtilsMethods.AnonymousClass3 */

        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            while (i < i2) {
                if (Character.getType(charSequence.charAt(i)) == 19) {
                    return "";
                }
                i++;
            }
            return null;
        }
    };
    public static DatePickerDialog datePicker;
    static ImageView imgLeft;
    static ImageView imgRight;
    public static RelativeLayout relLeft;
    public static RelativeLayout relRight;
    public static SimpleDateFormat sdf;
    public static TimePickerDialog timePicker;
    public static Toolbar toolbar;
    public static TextView txtRight;

    public static String getCurrentDate(String str) {
        Calendar instance = Calendar.getInstance();
        PrintStream printStream = System.out;
        printStream.println("Current time => " + instance.getTime());
        return new SimpleDateFormat(str).format(instance.getTime());
    }

    public static String getCurrentDatefromMilli(String str, String str2) {
        Calendar instance = Calendar.getInstance();
        PrintStream printStream = System.out;
        printStream.println("Current time => " + instance.getTime());
        return new SimpleDateFormat(str2).format(new Date(Long.parseLong(str)));
    }

    public static boolean CheckPermissions(Context context, String str, int i) {
        if (ContextCompat.checkSelfPermission(context, str) == 0) {
            return true;
        }
        Log.e("checkSelfPermission", "PERMISSION_GRANTED");
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, str)) {
            Log.e("checkSelfPermission", "shouldShowRequestPermissionRationale");
            if (context instanceof AppCompatActivity) {
                Log.e("AppCompatActivity", "true");
                ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{str}, i);
            } else if (context instanceof FragmentActivity) {
                Log.e("FragmentActivity", "true");
                ActivityCompat.requestPermissions((FragmentActivity) context, new String[]{str}, i);
            }
        } else {
            if (context instanceof AppCompatActivity) {
                ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{str}, i);
            } else if (context instanceof FragmentActivity) {
                ActivityCompat.requestPermissions((FragmentActivity) context, new String[]{str}, i);
            }
            Log.e("checkSelfPermission", "no shouldShowRequestPermissionRationale");
        }
        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void ShakeEditText(View view) {
        view.requestFocus(33);
    }

    public static void ViewAnimate(View view) {
        view.requestFocus(33);
    }

    public static boolean compareDates(String str, String str2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date parse = simpleDateFormat.parse(str);
            Date parse2 = simpleDateFormat.parse(str2);
            PrintStream printStream = System.out;
            printStream.println("Date1" + simpleDateFormat.format(parse));
            PrintStream printStream2 = System.out;
            printStream2.println("Date2" + simpleDateFormat.format(parse2));
            System.out.println();
            if (parse.after(parse2)) {
                System.out.println("Date1 is after Date2");
                return false;
            } else if (parse.before(parse2)) {
                System.out.println("Date1 is before Date2");
                return true;
            } else if (parse.equals(parse2)) {
                System.out.println("Date1 is equal Date2");
                return false;
            } else {
                System.out.println();
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //ToDo: return statement...
        return false;
    }

    public static String ValidateString(String str) {
        return (str.equals("") || str == null || str.equals("null")) ? "" : str;
    }

    public static boolean isMyServiceRunning(Context context, Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String ConvertTimeZone(String str, String str2, String str3) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str3);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(str2));
            Date parse = simpleDateFormat.parse(str);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(str3);
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            String format = simpleDateFormat2.format(parse);
            Log.e("OurDate", format);
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return "00-00-0000 00:00";
        }
    }

    public static boolean CheckPermissions(Context context, Fragment fragment, String str, int i) {
        if (Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, str) == 0) {
            return true;
        }
        Log.e("checkSelfPermission", "PERMISSION_GRANTED");
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, str)) {
            Log.e("checkSelfPermission", "shouldShowRequestPermissionRationale");
            fragment.requestPermissions(new String[]{str}, i);
        } else {
            fragment.requestPermissions(new String[]{str}, i);
            Log.e("checkSelfPermission", "no shouldShowRequestPermissionRationale");
        }
        return false;
    }

    public static boolean IsLocationEnabled(Context context) {
        boolean z;
        boolean z2;
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        try {
            z = locationManager.isProviderEnabled("gps");
        } catch (Exception unused) {
            z = false;
        }
        try {
            z2 = locationManager.isProviderEnabled("network");
        } catch (Exception unused2) {
            z2 = false;
        }
        if (z || z2) {
            return true;
        }
        return false;
    }

    public static Snackbar ShowSnackBar(View view, String str) {
        Snackbar make = Snackbar.make(view, str, 0);
        View view2 = make.getView();
        ((TextView) view2.findViewById(R.id.snackbar_text)).setTextColor(-1);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view2.getLayoutParams();
        layoutParams.gravity = 48;
        view2.setLayoutParams(layoutParams);
        make.show();
        return make;
    }

    public static void printHashKey(Context context) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo("com.ulawn_customer", 64).signatures;
            for (Signature signature : signatureArr) {
                MessageDigest instance = MessageDigest.getInstance("SHA");
                instance.update(signature.toByteArray());
                Log.e("TEMPTAGHASH KEY:", Base64.encodeToString(instance.digest(), 0));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException unused) {
        }
    }

    public static String getDateFormat(String str, String str2, String str3) throws ParseException {
        String str4;
        try {
            Date parse = new SimpleDateFormat(str, Locale.ENGLISH).parse(str2);
            sdf = new SimpleDateFormat(str3, Locale.ENGLISH);
            str4 = sdf.format(parse);
            Log.e("New Date", "Date==  " + str4);
        } catch (ParseException e2) {
            //e = e2;
            str4 = "";
            e2.printStackTrace();
            return str4;
        }
        return str4;
    }

    public static long getDateFormatinMilli(String str, String str2, String str3) throws ParseException {
        long j;
        try {
            j = new SimpleDateFormat(str, Locale.ENGLISH).parse(str2).getTime();
            Log.e("New Date", "Date==  " + j);
        } catch (ParseException e2) {
            //e = e2;
            j = 0;
            e2.printStackTrace();
            return j;
        }
        return j;
    }

    public static void setupParent(final Context context, View view) {
        try {
            if (!(view instanceof EditText)) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    /* class com.nst.yourname.view.utility.UtilsMethods.AnonymousClass1 */

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        UtilsMethods.hideSoftKeyboard(context);
                        return false;
                    }
                });
            }
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    setupParent(context, ((ViewGroup) view).getChildAt(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideSoftKeyboard(Context context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
            if (inputMethodManager != null && ((AppCompatActivity) context).getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(((AppCompatActivity) context).getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validateEmailId(String str) {
        return Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+").matcher(str).matches();
    }

    public static void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(5);
        }
    }

    public static String formatToYesterdayOrToday(long j, boolean z) throws ParseException {
        Date date = new Date();
        date.setTime(j);
        String format = new SimpleDateFormat("MMM dd, yyyy").format(date);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        Calendar.getInstance();
        Calendar.getInstance().add(5, -1);
        if (z) {
            String format2 = new SimpleDateFormat("EEE, MMM dd, yyyy").format(instance.getTime());
            Log.e("TIMEE>", "timeFormatter  >>   " + format2);
            return format2;
        }
        Log.e("TIMEE>", ">>   " + format);
        return format;
    }

    public static void colorStatusBar(Activity activity, int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(i);
        }
    }

    public static void setFadeAnimation(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(500);
        view.startAnimation(alphaAnimation);
    }

    public static void setScaleAnimation(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(500);
        view.startAnimation(scaleAnimation);
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int i) {
        int childCount = numberPicker.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = numberPicker.getChildAt(i2);
            if (childAt instanceof EditText) {
                try {
                    Field declaredField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    declaredField.setAccessible(true);
                    ((Paint) declaredField.get(numberPicker)).setColor(i);
                    ((EditText) childAt).setTextColor(i);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    Log.w("setNumbPickerTextColor", e);
                } catch (IllegalAccessException e2) {
                    Log.w("setNuerPickerTextColor", e2);
                } catch (IllegalArgumentException e3) {
                    Log.w("setNuerPickerTextColor", e3);
                }
            } else {
                i2++;
            }
        }
        return false;
    }

    public static String formatTime(long j) {
        long j2 = j / 1000;
        long j3 = j2 / 60;
        long j4 = j2 % 60;
        String valueOf = String.valueOf(j4);
        String valueOf2 = String.valueOf(j3);
        String.valueOf((j3 / 60) % 60);
        if (j4 < 10) {
            valueOf = AppConst.PASSWORD_UNSET + j4;
        }
        if (j3 < 10) {
            valueOf2 = AppConst.PASSWORD_UNSET + j3;
        }
        return valueOf2 + " : " + valueOf;
    }

    public static String GetString(EditText editText) {
        return editText.getText().toString();
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0039  */
    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                        if (cursor != null) {
                            cursor.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            //th = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            try {
                throw th2;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        //ToDo: initialized....
        return str;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static void hideKeyboard(Activity activity, View view) {
        try {
            ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getListViewSize(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter != null) {
            Log.e("length are---->", String.valueOf(adapter.getCount()));
            int count = adapter.getCount();
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), Integer.MIN_VALUE);
            View view = null;
            int i = 0;
            for (int i2 = 0; i2 < count; i2++) {
                view = adapter.getView(i2, view, listView);
                if (i2 == 0) {
                    view.setLayoutParams(new ViewGroup.LayoutParams(makeMeasureSpec, -2));
                }
                view.measure(makeMeasureSpec, 0);
                i += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
            layoutParams.height = (listView.getDividerHeight() * (adapter.getCount() - 1)) + i;
            listView.setLayoutParams(layoutParams);
            Log.i("height of listItem:", String.valueOf(i));
        }
    }

    public static boolean isPackageExisted(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 128);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void Block_SpaceInEditText(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter() {
            /* class com.nst.yourname.view.utility.UtilsMethods.AnonymousClass2 */

            public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                while (i < i2) {
                    if (Character.isWhitespace(charSequence.charAt(i))) {
                        return "";
                    }
                    i++;
                }
                return null;
            }
        }});
    }
}
