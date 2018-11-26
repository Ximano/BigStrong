package com.mili.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mili.R;
import com.mili.base.BaseRecyclerAdapter;
import com.mili.model.Sort;

import java.util.List;

class SortItemAdapter extends BaseRecyclerAdapter<Sort.SortItem> {
    public SortItemAdapter(Activity context, List<Sort.SortItem> list) {
        this.activity = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);
        RecyclerView.ViewHolder holder = new SortItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SortItemHolder itemHolder = (SortItemHolder) holder;
        String name = list.get(position).getName();
        if (name.length() > 3) {
            name = name.substring(0, 3);
        }
        itemHolder.tvItem.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class SortItemHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public SortItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_grid_item);
        }
    }
}
