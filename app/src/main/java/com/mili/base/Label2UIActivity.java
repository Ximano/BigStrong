package com.mili.base;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mili.R;
import com.mili.utils.StatusBarUtil;

/**
 * Created by TeeMo111 on 2018/11/27.
 */
public abstract class Label2UIActivity extends BaseActivity {
    protected Toolbar mToolbar;
    protected TextView mTitleTv;
    protected String label;
    protected int statusColorId = R.color.main_status_bar_red;

    @Override
    protected void fetchExtra() {
        label = getIntent().getStringExtra("label");
    }

    @Override
    protected void initToolbar() {
        mToolbar = findViewById(R.id.common_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv = findViewById(R.id.common_toolbar_title_tv);
        mTitleTv.setText(label);
        // 设置状态栏颜色
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, getStatusColorId()), 1f);
        this.mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    /**
     * 获取状态栏颜色
     *
     * @return
     */
    private int getStatusColorId() {
        return statusColorId;
    }

    /**
     * 设置状态栏颜色
     *
     * @param statusColorId
     */
    public void setStatusColorId(int statusColorId) {
        this.statusColorId = statusColorId;
    }
}
