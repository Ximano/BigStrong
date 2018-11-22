package com.mili.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mili.R;
import com.mili.adapter.SortAdapter;
import com.mili.base.BaseActivity;
import com.mili.model.Sort;
import com.mili.utils.AssetUtils;
import com.mili.utils.LogUtils;
import com.mili.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SortGridViewActivity extends BaseActivity {
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<Sort> data;
    private String label;

    @Override
    protected void fetchExtra() {
        label = getIntent().getStringExtra("label");
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(label);
        // 设置状态栏颜色
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_red), 1f);
        this.mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

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

        mRecyclerView = findViewById(R.id.recyclerView);
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
