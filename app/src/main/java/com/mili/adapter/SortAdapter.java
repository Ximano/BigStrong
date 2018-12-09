package com.mili.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mili.R;
import com.mili.base.BaseRecyclerAdapter;
import com.mili.divider.GridDividerItemDecoration;
import com.mili.model.Sort;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SortAdapter extends BaseRecyclerAdapter<Sort> {
    private GridDividerItemDecoration divider;

    public SortAdapter(Activity context, List<Sort> list) {
        this.activity = context;
        this.list = list;
        divider = new GridDividerItemDecoration(2, context.getResources().getColor(R.color.border_grid_view));
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
        /*GlideApp.with(activity)
                .load(list.get(position).getIcon())
                .error(R.mipmap.ic_sort)
                .into(itemHolder.ivIcon);*/
        itemHolder.recyclerView.setLayoutManager(new GridLayoutManager(activity, 5));
        itemHolder.recyclerView.setHasFixedSize(true);
        itemHolder.recyclerView.setNestedScrollingEnabled(false);

        if (itemHolder.recyclerView.getItemDecorationCount() == 0) {
            itemHolder.recyclerView.addItemDecoration(divider);
        }
        List<Sort.SortItem> subSort = list.get(position).getSub();
        if (subSort != null) {
            SortItemAdapter adapter = new SortItemAdapter(activity, subSort);
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
