package com.mili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mili.R;
import com.mili.utils.LogUtils;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.btn_grid, R.id.btn_keywords_high_light})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_grid:
                startActivity(new Intent(this, SortGridViewActivity.class));
                LogUtils.d("SortGridViewActivity");
                break;
            case R.id.btn_keywords_high_light:
                startActivity(new Intent(this, KeywordsHighLightActivity.class));
                LogUtils.d("KeywordsHighLightActivity");
                break;
        }
    }
}
