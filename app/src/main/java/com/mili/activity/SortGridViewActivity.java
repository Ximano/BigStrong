package com.mili.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.mili.R;
import com.mili.adapter.SortAdapter;
import com.mili.model.Sort;
import com.mili.utils.LogUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SortGridViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Sort> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_grid_view);
        initView();
    }

    private void initView() {
        data = new ArrayList<>();
        String json = getStringFromAssets("sort.json");
        List<Sort> sorts = JSON.parseArray(json, Sort.class);
        for (int i = 0; i < sorts.size(); i++) {
            if (sorts.get(i).getSub() != null) {
                data.add(sorts.get(i));
            } else {
                LogUtils.d("当前无sub的position = " + i);
            }
        }

        recyclerView = findViewById(R.id.recyclerView);
        // 设置RecycleView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SortAdapter sortAdapter = new SortAdapter(this, data);
        recyclerView.setAdapter(sortAdapter);
    }

    public String getStringFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
