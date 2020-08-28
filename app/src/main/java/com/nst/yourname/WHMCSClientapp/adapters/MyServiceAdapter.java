package com.nst.yourname.WHMCSClientapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class MyServiceAdapter extends RecyclerView.Adapter<MyServiceAdapter.ViewHolder> {
    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return 0;
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
