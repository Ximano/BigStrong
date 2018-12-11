package com.mili.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.mili.R;
import com.mili.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by TeeMo111 on 2018/11/13.
 */
public class LauncherActivity extends BaseActivity implements Animation.AnimationListener {
    @BindView(R.id.iv_launcher_bg)
    ImageView mImageView;
    @BindView(R.id.iv_launcher_icon)
    ImageView mIconView;
    @BindView(R.id.iv_launcher_name)
    TextView mTextView;
    private static final int ANIM_TIME = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void fetchExtra() {
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void initView() {
        //初始化动画
        initStartAnim();
        //设置状态栏和导航栏参数
        getStatusBarConfig()
                .fullScreen(true)//有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)//隐藏状态栏
                .transparentNavigationBar()//透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                .init();
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(() -> startActivity(new Intent(LauncherActivity.this, MainActivity.class)), 2000);
        if(TextUtils.isEmpty("")) {

        }
    }

    /**
     * 启动动画
     */
    private void initStartAnim() {
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f);
        aa.setDuration(ANIM_TIME * 2);
        aa.setAnimationListener(this);
        mImageView.startAnimation(aa);

        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(ANIM_TIME);
        mIconView.startAnimation(sa);

        RotateAnimation ra = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(ANIM_TIME);
        mTextView.startAnimation(ra);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
