package com.nst.yourname.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.FavouriteM3UModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

@SuppressWarnings("all")
public class SearchableAdapter extends BaseAdapter implements Filterable {
    static final boolean $assertionsDisabled = false;
    private Context context;
    private String currentPlayingNum;
    private String currentPlayingNumM3U;
    private DatabaseHandler database;
    public ArrayList<LiveStreamsDBModel> filteredData = null;
    public ViewHolder holder;
    private LiveStreamDBHandler liveStreamDBHandler;
    private ItemFilter mFilter = new ItemFilter();
    private LayoutInflater mInflater;
    TextView noChannelFound;
    public ArrayList<LiveStreamsDBModel> originalData;

    public long getItemId(int i) {
        return (long) i;
    }

    public SearchableAdapter(Context context2, ArrayList<LiveStreamsDBModel> arrayList) {
        this.filteredData = arrayList;
        this.originalData = arrayList;
        this.context = context2;
        this.mInflater = LayoutInflater.from(context2);
        this.database = new DatabaseHandler(context2);
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
    }

    private void fetchCurrentlyPlayingChannel() {
        this.currentPlayingNum = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, 0).getString(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, "");
    }

    private void fetchCurrentlyPlayingChannelM3U() {
        this.currentPlayingNum = this.context.getSharedPreferences(AppConst.LOGIN_PREF_CURRENTLY_PLAYING_VIDEO, 0).getString("LOGIN_PREF_CURRENTLY_PLAYING_VIDEO_M3U", "");
    }

    public int getCount() {
        return this.filteredData.size();
    }

    public Object getItem(int i) {
        return this.filteredData.get(i);
    }

    public ArrayList<LiveStreamsDBModel> getFilteredData() {
        return this.filteredData;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00c6 A[Catch:{ Exception -> 0x0252 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0153 A[Catch:{ Exception -> 0x0252 }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0240 A[Catch:{ Exception -> 0x0252 }] */
    @SuppressLint({"InflateParams"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        int i2;
        Exception e;
        if (view == null) {
            try {
                view2 = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate((int) R.layout.channel_list, (ViewGroup) null);
                try {
                    this.holder = new ViewHolder();
                    this.holder.channel_number = (TextView) view2.findViewById(R.id.tv_channel_number);
                    this.holder.text = (TextView) view2.findViewById(R.id.list_view);
                    this.holder.image = (ImageView) view2.findViewById(R.id.tv_logo);
                    this.holder.ll_list_view = (LinearLayout) view2.findViewById(R.id.ll_list_view);
                    this.holder.favourite = (ImageView) view2.findViewById(R.id.iv_favourite);
                    this.holder.tv_currently_playing = (ImageView) view2.findViewById(R.id.tv_currently_playing);
                    view2.setTag(this.holder);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                view2 = view;
                e = e3;
                e.printStackTrace();
                this.holder.text.setText(this.filteredData.get(i).getName());
                String url = this.filteredData.get(i).getUrl();
                i2 = Integer.parseInt(this.filteredData.get(i).getStreamId());
                if (!SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                }
                if (this.filteredData.get(i).getStreamIcon() != null) {
                }
                if (Build.VERSION.SDK_INT >= 21) {
                }
                return view2;
            }
        } else {
            this.holder = (ViewHolder) view.getTag();
            view2 = view;
        }
        try {
            this.holder.text.setText(this.filteredData.get(i).getName());
            String url2 = this.filteredData.get(i).getUrl();
            try {
                i2 = Integer.parseInt(this.filteredData.get(i).getStreamId());
            } catch (NumberFormatException unused) {
                i2 = -1;
            }
            if (!SharepreferenceDBHandler.getCurrentAPPType(this.context).equals(AppConst.TYPE_M3U)) {
                ArrayList<FavouriteM3UModel> checkFavourite = this.liveStreamDBHandler.checkFavourite(url2, SharepreferenceDBHandler.getUserID(this.context));
                if (checkFavourite == null || checkFavourite.size() <= 0) {
                    try {
                        this.holder.favourite.setVisibility(4);
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                } else {
                    try {
                        this.holder.favourite.setVisibility(0);
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                fetchCurrentlyPlayingChannelM3U();
                this.holder.channel_number.setText(this.filteredData.get(i).getNum());
                if (this.currentPlayingNum.equals("") || !this.currentPlayingNum.equals(String.valueOf(this.filteredData.get(i).getUrl())) || !AppConst.SETPLAYERLISTENER.booleanValue()) {
                    this.holder.text.setSelected(false);
                    this.holder.tv_currently_playing.setVisibility(8);
                } else {
                    this.holder.text.setSelected(true);
                    this.holder.tv_currently_playing.setVisibility(0);
                }
            } else {
                ArrayList<FavouriteDBModel> checkFavourite2 = this.database.checkFavourite(i2, this.filteredData.get(i).getCategoryId(), "live", SharepreferenceDBHandler.getUserID(this.context));
                if (checkFavourite2 == null || checkFavourite2.size() <= 0) {
                    try {
                        this.holder.favourite.setVisibility(4);
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                } else {
                    try {
                        this.holder.favourite.setVisibility(0);
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                fetchCurrentlyPlayingChannel();
                this.holder.channel_number.setText(this.filteredData.get(i).getNum());
                if (this.currentPlayingNum.equals("") || !this.currentPlayingNum.equals(String.valueOf(this.filteredData.get(i).getStreamId())) || !AppConst.SETPLAYERLISTENER.booleanValue()) {
                    this.holder.text.setSelected(false);
                    this.holder.tv_currently_playing.setVisibility(8);
                } else {
                    this.holder.text.setSelected(true);
                    this.holder.tv_currently_playing.setVisibility(0);
                }
            }
            if (this.filteredData.get(i).getStreamIcon() != null || this.filteredData.get(i).getStreamIcon().equals("")) {
                if (Build.VERSION.SDK_INT >= 21) {
                    this.holder.image.setImageDrawable(this.context.getResources().getDrawable(R.drawable.logo_placeholder_white, null));
                }
                return view2;
            }
            Picasso.with(this.context).load(this.filteredData.get(i).getStreamIcon()).resize(80, 55).placeholder((int) R.drawable.logo_placeholder_white).into(this.holder.image);
            return view2;
        } catch (Exception e8) {
            e8.printStackTrace();
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

    public Filter getFilter() {
        return this.mFilter;
    }

    private class ItemFilter extends Filter {
        private ItemFilter() {
        }

        public FilterResults performFiltering(CharSequence charSequence) {
            String lowerCase = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            ArrayList<LiveStreamsDBModel> arrayList = SearchableAdapter.this.originalData;
            int size = arrayList.size();
            ArrayList arrayList2 = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                LiveStreamsDBModel liveStreamsDBModel = arrayList.get(i);
                if (liveStreamsDBModel.getName().toLowerCase().contains(lowerCase) || liveStreamsDBModel.getNum().contains(lowerCase)) {
                    arrayList2.add(liveStreamsDBModel);
                }
            }
            filterResults.values = arrayList2;
            filterResults.count = arrayList2.size();
            return filterResults;
        }

        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            try {
                ArrayList unused = SearchableAdapter.this.filteredData = (ArrayList) filterResults.values;
                SearchableAdapter.this.notifyDataSetChanged();
                if (SearchableAdapter.this.filteredData.size() == 0) {
                    SearchableAdapter.this.noChannelFound.setVisibility(0);
                } else {
                    SearchableAdapter.this.noChannelFound.setVisibility(8);
                }
            } catch (Exception unused2) {
            }
        }
    }

    public void settesxtviewvisibility(TextView textView) {
        this.noChannelFound = textView;
    }
}
