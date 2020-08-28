package com.nst.yourname.miscelleneious;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.nst.yourname.R;

public class ChildViewHolder extends RecyclerView.ViewHolder {
    RecyclerView name;

    public ChildViewHolder(View view) {
        super(view);
        this.name = (RecyclerView) view.findViewById(R.id.my_recycler_view);
    }
}
