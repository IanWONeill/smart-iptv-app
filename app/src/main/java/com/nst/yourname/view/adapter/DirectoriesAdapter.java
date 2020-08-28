package com.nst.yourname.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nst.yourname.R;
import com.nst.yourname.model.database.DatabaseHandler;
import java.util.List;

public class DirectoriesAdapter extends BaseAdapter {
    static final boolean $assertionsDisabled = false;
    private Context context;
    private String currentPlayingNum;
    private DatabaseHandler database;
    private List<String> filteredData = null;
    public ViewHolder holder;
    private LayoutInflater mInflater;
    TextView noChannelFound;
    public List<String> originalData;

    public long getItemId(int i) {
        return (long) i;
    }

    public DirectoriesAdapter(Context context2, List<String> list) {
        this.filteredData = list;
        this.originalData = list;
        this.context = context2;
        this.mInflater = LayoutInflater.from(context2);
        this.database = new DatabaseHandler(context2);
    }

    public int getCount() {
        return this.filteredData.size();
    }

    public Object getItem(int i) {
        return this.filteredData.get(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x006b A[Catch:{ Exception -> 0x00a1 }] */
    @SuppressLint({"InflateParams"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        Exception e;
        if (view == null) {
            try {
                view2 = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.select_dialog_item_folder, (ViewGroup) null);
                try {
                    this.holder = new ViewHolder();
                    this.holder.text = (TextView) view2.findViewById(R.id.list_view);
                    this.holder.image = (ImageView) view2.findViewById(R.id.tv_logo);
                    this.holder.ll_list_view = (LinearLayout) view2.findViewById(R.id.ll_list_view);
                    view2.setTag(this.holder);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                view2 = view;
                e = e3;
                e.printStackTrace();
                if (!this.filteredData.get(i).endsWith(".m3u")) {
                }
                this.holder.image.setBackgroundResource(R.drawable.alert_icon);
                this.holder.text.setText(this.filteredData.get(i));
                return view2;
            }
        } else {
            this.holder = (ViewHolder) view.getTag();
            view2 = view;
        }
        try {
            if (!this.filteredData.get(i).endsWith(".m3u")) {
                if (!this.filteredData.get(i).endsWith(".m3u8")) {
                    this.holder.image.setBackgroundResource(R.drawable.folder_icon);
                    this.holder.text.setText(this.filteredData.get(i));
                    return view2;
                }
            }
            this.holder.image.setBackgroundResource(R.drawable.alert_icon);
            this.holder.text.setText(this.filteredData.get(i));
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return view2;
    }

    static class ViewHolder {
        TextView channel_number;
        ImageView favourite;
        ImageView image;
        LinearLayout ll_list_view;
        TextView text;
        ImageView tv_currently_playing;

        ViewHolder() {
        }
    }
}
