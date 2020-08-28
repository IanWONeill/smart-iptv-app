package com.nst.yourname.miscelleneious;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.nst.yourname.R;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.view.adapter.SubCategoriesChildAdapter;
import java.util.ArrayList;
import java.util.List;

public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader, Child, SectionViewHolder, ChildViewHolder> {
    ArrayList<LiveStreamsDBModel> channelAvailable;
    Context context;
    List<SectionHeader> sectionItemList;

    public AdapterSectionRecycler(Context context2, List<SectionHeader> list, ArrayList<LiveStreamsDBModel> arrayList, RecyclerView recyclerView) {
        super(context2, list);
        this.context = context2;
        this.channelAvailable = arrayList;
        this.sectionItemList = list;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup viewGroup, int i) {
        return new SectionViewHolder(LayoutInflater.from(this.context).inflate((int) R.layout.layout_section_header, viewGroup, false));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter
    public ChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int i) {
        return new ChildViewHolder(LayoutInflater.from(this.context).inflate((int) R.layout.layout_section_child, viewGroup, false));
    }

    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int i, SectionHeader sectionHeader) {
        sectionViewHolder.name.setText(sectionHeader.sectionText);
    }

    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, int i2, Child child) {
        this.channelAvailable = (ArrayList) this.sectionItemList.get(i).channelSelcted();
        childViewHolder.name.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        childViewHolder.name.setAdapter(new SubCategoriesChildAdapter(this.channelAvailable, this.context));
    }
}
