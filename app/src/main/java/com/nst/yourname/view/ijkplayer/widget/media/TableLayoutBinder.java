package com.nst.yourname.view.ijkplayer.widget.media;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import com.nst.yourname.R;

public class TableLayoutBinder {
    private Context mContext;
    public TableLayout mTableLayout;
    public ViewGroup mTableView;

    public static String aW5nIGl() {
        return "V";
    }

    public static String mu() {
        return "GhlIGFwcCB5b3UgYXJlIHVzaW5nIGlzIG5vdCBvcmlnaW5hbC4=";
    }

    public TableLayoutBinder(Context context) {
        this(context, (int) R.layout.table_media_info);
    }

    public TableLayoutBinder(Context context, int i) {
        this.mContext = context;
        this.mTableView = (ViewGroup) LayoutInflater.from(this.mContext).inflate(i, (ViewGroup) null);
        this.mTableLayout = (TableLayout) this.mTableView.findViewById(R.id.table);
    }

    public TableLayoutBinder(Context context, TableLayout tableLayout) {
        this.mContext = context;
        this.mTableView = tableLayout;
        this.mTableLayout = tableLayout;
    }

    public View appendRow1(String str, String str2) {
        return appendRow(R.layout.table_media_info_row1, str, str2);
    }

    public View appendRow1(int i, String str) {
        return appendRow1(this.mContext.getString(i), str);
    }

    public View appendRow2(String str, String str2) {
        return appendRow(R.layout.table_media_info_row2, str, str2);
    }

    public View appendRow2(int i, String str) {
        return appendRow2(this.mContext.getString(i), str);
    }

    public View appendSection(String str) {
        return appendRow(R.layout.table_media_info_section, str, null);
    }

    public View appendSection(int i) {
        return appendSection(this.mContext.getString(i));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, android.widget.TableLayout, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public View appendRow(int i, String str, String str2) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.mContext).inflate(i, (ViewGroup) this.mTableLayout, false);
        setNameValueText(viewGroup, str, str2);
        this.mTableLayout.addView(viewGroup);
        return viewGroup;
    }

    public ViewHolder obtainViewHolder(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder != null) {
            return viewHolder;
        }
        ViewHolder viewHolder2 = new ViewHolder();
        viewHolder2.mNameTextView = (TextView) view.findViewById(R.id.name);
        viewHolder2.mValueTextView = (TextView) view.findViewById(R.id.value);
        view.setTag(viewHolder2);
        return viewHolder2;
    }

    public void setNameValueText(View view, String str, String str2) {
        ViewHolder obtainViewHolder = obtainViewHolder(view);
        obtainViewHolder.setName(str);
        obtainViewHolder.setValue(str2);
    }

    public void setValueText(View view, String str) {
        obtainViewHolder(view).setValue(str);
    }

    public ViewGroup buildLayout() {
        return this.mTableView;
    }

    public AlertDialog.Builder buildAlertDialogBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setView(buildLayout());
        return builder;
    }

    private static class ViewHolder {
        public TextView mNameTextView;
        public TextView mValueTextView;

        private ViewHolder() {
        }

        public void setName(String str) {
            if (this.mNameTextView != null) {
                this.mNameTextView.setText(str);
            }
        }

        public void setValue(String str) {
            if (this.mValueTextView != null) {
                this.mValueTextView.setText(str);
            }
        }
    }
}
