package com.nst.yourname.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.FavouriteDBModel;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.DatabaseHandler;
import com.nst.yourname.model.database.SharepreferenceDBHandler;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class LiveStreamListViewAdapter extends RecyclerView.Adapter<LiveStreamListViewAdapter.MyViewHolder> {
    String StreamIcon = "";
    String categoryId = "";
    String channelnum = "";
    public List<LiveStreamsDBModel> completeList;
    public Context context;
    public List<LiveStreamsDBModel> dataSet;
    public DatabaseHandler database;
    String epgChannelID = "";
    public List<LiveStreamsDBModel> filterList;
    private SharedPreferences loginPreferencesSharedPref;
    MyViewHolder myViewHolder;
    String name = "";
    int streamId = -1;
    String streamType = "";
    int userIdReferred = 0;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.ivChannelLogo = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_channel_logo, "field 'ivChannelLogo'", ImageView.class);
            myViewHolder.tvChannelName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_name, "field 'tvChannelName'", TextView.class);
            myViewHolder.cardView = (CardView) Utils.findRequiredViewAsType(view, R.id.card_view, "field 'cardView'", CardView.class);
            myViewHolder.tvStreamOptions = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_streamOptions, "field 'tvStreamOptions'", TextView.class);
            myViewHolder.ivFavourite = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_favourite, "field 'ivFavourite'", ImageView.class);
            myViewHolder.rlStreamsLayout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_streams_layout, "field 'rlStreamsLayout'", RelativeLayout.class);
            myViewHolder.rlChannelBottom = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_channel_bottom, "field 'rlChannelBottom'", RelativeLayout.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.ivChannelLogo = null;
                myViewHolder.tvChannelName = null;
                myViewHolder.cardView = null;
                myViewHolder.tvStreamOptions = null;
                myViewHolder.ivFavourite = null;
                myViewHolder.rlStreamsLayout = null;
                myViewHolder.rlChannelBottom = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public LiveStreamListViewAdapter(List<LiveStreamsDBModel> list, Context context2) {
        this.dataSet = list;
        this.context = context2;
        this.filterList = new ArrayList();
        this.filterList.addAll(list);
        this.completeList = list;
        this.database = new DatabaseHandler(context2);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.iv_channel_logo)
        ImageView ivChannelLogo;
        @BindView(R.id.iv_favourite)
        ImageView ivFavourite;
        @BindView(R.id.rl_channel_bottom)
        RelativeLayout rlChannelBottom;
        @BindView(R.id.rl_streams_layout)
        RelativeLayout rlStreamsLayout;
        @BindView(R.id.tv_movie_name)
        TextView tvChannelName;
        @BindView(R.id.tv_streamOptions)
        TextView tvStreamOptions;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
        this.myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.live_streams_linear_layout, viewGroup, false));
        return this.myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder2, int i) {
        if (this.context != null) {
            Context context2 = this.context;
            Context context3 = this.context;
            this.loginPreferencesSharedPref = context2.getSharedPreferences(AppConst.LOGIN_PREF_SELECTED_PLAYER, 0);
            this.loginPreferencesSharedPref.getString(AppConst.LOGIN_PREF_SELECTED_PLAYER, "");
            try {
                this.streamId = Integer.parseInt(this.dataSet.get(i).getStreamId());
            } catch (NumberFormatException unused) {
                this.streamId = 0;
            }
            this.categoryId = this.dataSet.get(i).getCategoryId();
            this.streamType = this.dataSet.get(i).getStreamType();
            this.epgChannelID = this.dataSet.get(i).getEpgChannelId();
            this.name = this.dataSet.get(i).getName();
            myViewHolder2.tvChannelName.setText(this.dataSet.get(i).getName());
            this.StreamIcon = this.dataSet.get(i).getStreamIcon();
            this.channelnum = this.dataSet.get(i).getNum();
            if (this.StreamIcon != null && !this.StreamIcon.equals("")) {
                Picasso.with(this.context).load(this.StreamIcon).placeholder((int) R.drawable.logo_placeholder_white).into(myViewHolder2.ivChannelLogo);
            }
            myViewHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamListViewAdapter.AnonymousClass1 */

                public void onClick(View view) {
                }
            });
            myViewHolder2.rlStreamsLayout.setOnKeyListener(new View.OnKeyListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamListViewAdapter.AnonymousClass2 */

                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (keyEvent.getAction() != 0) {
                        return false;
                    }
                    if (i != 23 && i != 66) {
                        return false;
                    }
                    myViewHolder2.rlChannelBottom.performClick();
                    return true;
                }
            });
            if (this.database.checkFavourite(this.streamId, this.categoryId, "live", SharepreferenceDBHandler.getUserID(this.context)).size() > 0) {
                myViewHolder2.ivFavourite.setVisibility(0);
            } else {
                myViewHolder2.ivFavourite.setVisibility(4);
            }
            myViewHolder2.rlChannelBottom.setOnClickListener(new View.OnClickListener() {
                /* class com.nst.yourname.view.adapter.LiveStreamListViewAdapter.AnonymousClass3 */

                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(LiveStreamListViewAdapter.this.context, myViewHolder2.tvStreamOptions);
                    popupMenu.inflate(R.menu.menu_card_live_streams);
                    if (LiveStreamListViewAdapter.this.database.checkFavourite(LiveStreamListViewAdapter.this.streamId, LiveStreamListViewAdapter.this.categoryId, "live", SharepreferenceDBHandler.getUserID(LiveStreamListViewAdapter.this.context)).size() > 0) {
                        popupMenu.getMenu().getItem(2).setVisible(true);
                    } else {
                        popupMenu.getMenu().getItem(1).setVisible(true);
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        /* class com.nst.yourname.view.adapter.LiveStreamListViewAdapter.AnonymousClass3.AnonymousClass1 */

                        @Override // android.support.v7.widget.PopupMenu.OnMenuItemClickListener
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int itemId = menuItem.getItemId();
                            if (itemId == R.id.nav_add_to_fav) {
                                addToFavourite();
                                return false;
                            } else if (itemId == R.id.nav_play) {
                                playChannel();
                                return false;
                            } else if (itemId != R.id.nav_remove_from_fav) {
                                return false;
                            } else {
                                removeFromFavourite();
                                return false;
                            }
                        }

                        private void playChannel() {
                            myViewHolder2.cardView.performClick();
                        }

                        private void addToFavourite() {
                            FavouriteDBModel favouriteDBModel = new FavouriteDBModel();
                            favouriteDBModel.setCategoryID(LiveStreamListViewAdapter.this.categoryId);
                            favouriteDBModel.setStreamID(LiveStreamListViewAdapter.this.streamId);
                            favouriteDBModel.setName(LiveStreamListViewAdapter.this.name);
                            favouriteDBModel.setNum(LiveStreamListViewAdapter.this.channelnum);
                            favouriteDBModel.setUserID(SharepreferenceDBHandler.getUserID(LiveStreamListViewAdapter.this.context));
                            LiveStreamListViewAdapter.this.database.addToFavourite(favouriteDBModel, "live");
                            myViewHolder2.ivFavourite.setVisibility(0);
                        }

                        private void removeFromFavourite() {
                            LiveStreamListViewAdapter.this.database.deleteFavourite(LiveStreamListViewAdapter.this.streamId, LiveStreamListViewAdapter.this.categoryId, "live", LiveStreamListViewAdapter.this.name, SharepreferenceDBHandler.getUserID(LiveStreamListViewAdapter.this.context));
                            myViewHolder2.ivFavourite.setVisibility(4);
                        }
                    });
                    popupMenu.show();
                }
            });
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.dataSet.size();
    }

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.LiveStreamListViewAdapter.AnonymousClass4 */

            public void run() {
                List unused = LiveStreamListViewAdapter.this.filterList = new ArrayList();
                if (LiveStreamListViewAdapter.this.filterList != null) {
                    LiveStreamListViewAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    LiveStreamListViewAdapter.this.filterList.addAll(LiveStreamListViewAdapter.this.completeList);
                } else {
                    for (LiveStreamsDBModel liveStreamsDBModel : LiveStreamListViewAdapter.this.dataSet) {
                        if (liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                            LiveStreamListViewAdapter.this.filterList.add(liveStreamsDBModel);
                        }
                    }
                }
                ((Activity) LiveStreamListViewAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.LiveStreamListViewAdapter.AnonymousClass4.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = LiveStreamListViewAdapter.this.dataSet = LiveStreamListViewAdapter.this.completeList;
                        } else if (!LiveStreamListViewAdapter.this.filterList.isEmpty() || LiveStreamListViewAdapter.this.filterList.isEmpty()) {
                            List unused2 = LiveStreamListViewAdapter.this.dataSet = LiveStreamListViewAdapter.this.filterList;
                        }
                        if (LiveStreamListViewAdapter.this.dataSet.size() == 0) {
                            textView.setVisibility(0);
                        }
                        LiveStreamListViewAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
