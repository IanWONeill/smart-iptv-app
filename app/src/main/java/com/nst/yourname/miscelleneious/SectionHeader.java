package com.nst.yourname.miscelleneious;

import android.support.v7.widget.RecyclerView;
import com.intrusoft.sectionedrecyclerview.Section;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.view.adapter.SubCategoriesChildAdapter;
import java.util.ArrayList;
import java.util.List;

public class SectionHeader implements Section<Child> {
    ArrayList<LiveStreamsDBModel> channelAvailable;
    RecyclerView childList;
    private List<Child> list;
    String sectionText;
    private SubCategoriesChildAdapter subCategoriesChildAdapter;

    public SectionHeader(RecyclerView recyclerView, String str, ArrayList<LiveStreamsDBModel> arrayList, SubCategoriesChildAdapter subCategoriesChildAdapter2, List<Child> list2) {
        this.childList = recyclerView;
        this.sectionText = str;
        this.channelAvailable = arrayList;
        this.subCategoriesChildAdapter = subCategoriesChildAdapter2;
        this.list = list2;
    }

    public String getSectionText() {
        return this.sectionText;
    }

    @Override // com.intrusoft.sectionedrecyclerview.Section
    public List<Child> getChildItems() {
        return this.list;
    }

    public List<LiveStreamsDBModel> channelSelcted() {
        return this.channelAvailable;
    }
}
