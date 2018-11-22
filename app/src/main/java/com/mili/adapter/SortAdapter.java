package com.mili.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mili.R;
import com.mili.base.BaseRecyclerAdapter;
import com.mili.divider.GridDividerItemDecoration;
import com.mili.gilde.GlideApp;
import com.mili.model.Sort;

import java.util.List;

public class SortAdapter extends BaseRecyclerAdapter<Sort> {
    private GridDividerItemDecoration divider;

    public SortAdapter(Activity context, List<Sort> list) {
        this.context = context;
        this.list = list;
        divider = new GridDividerItemDecoration(2, Color.parseColor("#333333"));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categroes_item, parent, false);
        RecyclerView.ViewHolder holder = new SortHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SortHolder itemHolder = (SortHolder) holder;
        itemHolder.tvTitle.setText(list.get(position).getName());
        GlideApp.with(context)
                .load(list.get(position).getIcon())
                .into(itemHolder.ivIcon);
        itemHolder.recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        itemHolder.recyclerView.setHasFixedSize(true);
        itemHolder.recyclerView.setNestedScrollingEnabled(false);

        if (itemHolder.recyclerView.getItemDecorationCount() == 0) {
            itemHolder.recyclerView.addItemDecoration(divider);
        }
        List<Sort.SortItem> subSort = list.get(position).getSub();
        if (subSort != null) {
            SortItemAdapter adapter = new SortItemAdapter(context, subSort);
            itemHolder.recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class SortHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView tvTitle;
        private ImageView ivIcon;

        public SortHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title_text);
            ivIcon = itemView.findViewById(R.id.title_img);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
