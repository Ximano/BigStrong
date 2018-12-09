package com.mili.activity;


import com.alibaba.fastjson.JSON;
import com.mili.R;
import com.mili.adapter.SortAdapter;
import com.mili.base.Label2UIActivity;
import com.mili.model.Sort;
import com.mili.utils.AssetUtils;
import com.mili.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SortGridViewActivity extends Label2UIActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<Sort> data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort_grid_view;
    }

    @Override
    protected void initView() {
        data = new ArrayList<>();
        String json = AssetUtils.getStringFromAssets("sort.json");
        List<Sort> sorts = JSON.parseArray(json, Sort.class);
        for (int i = 0; i < sorts.size(); i++) {
            if (sorts.get(i).getSub() != null) {
                data.add(sorts.get(i));
            } else {
                LogUtils.d("当前无sub的position = " + i);
            }
        }
        // 设置RecycleView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SortAdapter sortAdapter = new SortAdapter(this, data);
        mRecyclerView.setAdapter(sortAdapter);
    }

    @Override
    protected void initData() {
    }
}
