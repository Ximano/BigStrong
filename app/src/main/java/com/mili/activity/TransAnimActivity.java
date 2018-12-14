package com.mili.activity;

import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.mili.R;
import com.mili.base.Label2UIActivity;
import com.mili.listener.GestureEvent;

/**
 * Created by TeeMo111 on 2018/11/27.
 */
public class TransAnimActivity extends Label2UIActivity implements GestureEvent.OnFlingListener {
    private GestureDetector mGestureDetector;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trans_anim;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        //这里的第一个参数是上下文，第二个是手势监听器
        GestureEvent mEvent = new GestureEvent(this);
        mEvent.setOnOnFlingListener(this);
        mGestureDetector = new GestureDetector(this, mEvent);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onLeftFling() {

    }

    @Override
    public void onRightFling() {
        finish();
    }

    @Override
    public void onTopFling() {

    }

    @Override
    public void onBottomFling() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
