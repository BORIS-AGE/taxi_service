package com.sergey.taxiservice.ui.fragments.search.input.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sergey.taxiservice.models.geo.AddressModel;

import java.util.List;

public class StreetSearchAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<AddressModel> mDataSet;
    private StreetSearchClickListener clickListener;

    public StreetSearchAdapter(StreetSearchClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder((TextView) view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mDataSet.get(position).getData());
        holder.textView.setOnClickListener(view -> {
            if(clickListener != null) {
                clickListener.onItemClicked(mDataSet.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public void setData(List<AddressModel> dataSet){
        this.mDataSet = dataSet;
        notifyDataSetChanged();
    }
}
