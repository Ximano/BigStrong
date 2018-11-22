package com.mili.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.mili.R;
import com.mili.base.BaseActivity;
import com.mili.utils.RichTextUtil;
import com.mili.utils.StatusBarUtil;
import com.mili.widget.EllipsizeTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RichTextViewActivity extends BaseActivity {
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.tv_high_light)
    TextView mHighLightText;
    @BindView(R.id.etv_expand)
    EllipsizeTextView mExpandTextView;
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
        initExpandTextView();
    }

    private void initExpandTextView() {
        mExpandTextView.setText(getString(R.string.text_expand));
        mExpandTextView.setOnTextClickListener(() -> ToastUtils.show(mExpandTextView.getMoreText()));
    }

    /**
     * 多个关键字高亮, 点击事件,
     */
    private void initHighLightText() {
        mHighLightText = findViewById(R.id.tv_high_light);
        // 设置点击事件时，必须添加的配置
        mHighLightText.setMovementMethod(LinkMovementMethod.getInstance());
        // 点击无背景色
        mHighLightText.setHighlightColor(getResources().getColor(android.R.color.transparent));
        CharSequence targetStr = RichTextUtil.getColorString(getString(R.string.text_high_light), "达令", Color.parseColor("#FF00FF"), view -> {
            ToastUtils.show("么么哒~");
        });
        mHighLightText.setText(targetStr);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
