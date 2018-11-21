package com.mili.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;
import com.mili.R;
import com.mili.base.BaseActivity;
import com.mili.utils.RichTextUtil;
import com.mili.utils.StatusBarUtil;

import butterknife.BindView;

public class RichTextViewActivity extends BaseActivity {
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.tv_high_light)
    TextView mHighLightText;
    private String label;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_keywords;
    }

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
    protected void initView() {
        initHighLightText();
    }


    private void initHighLightText() {
        mHighLightText = findViewById(R.id.tv_high_light);
        mHighLightText.setMovementMethod(LinkMovementMethod.getInstance());// 设置点击事件时，必须添加的配置
        String originStr = "达令是谁, 达令是darling";
        CharSequence targetStr = RichTextUtil.getColorString(originStr, "达令", Color.parseColor("#FF00FF"), view -> {
            ToastUtils.show("么么哒~");
        });
        mHighLightText.setText(targetStr);
    }

    @Override
    protected void initData() {
    }
}
