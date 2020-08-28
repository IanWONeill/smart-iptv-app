package com.nst.yourname.miscelleneious;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.nst.yourname.R;

public class SectionViewHolder extends RecyclerView.ViewHolder {
    TextView name;

    public SectionViewHolder(View view) {
        super(view);
        this.name = (TextView) view.findViewById(R.id.section);
    }
}
