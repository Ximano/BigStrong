package com.mili.listener;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.mili.utils.LogUtils;

/**
 * @dec :
 * @author: big strong
 * @date : 2018/12/14
 */
public class GestureEvent extends GestureDetector.SimpleOnGestureListener {
    private Activity context;

    public GestureEvent(Activity context) {
        this.context = context;
    }

    public interface OnFlingListener {
        void onLeftFling();

        void onRightFling();

        void onTopFling();

        void onBottomFling();
    }

    private OnFlingListener mListener;

    //写一个设置接口监听的方法
    public void setOnOnFlingListener(OnFlingListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float startX = e1.getX();//通过e1.getX（）获得手指按下位置的横坐标
        float endX = e2.getX();//通过e2.getX（）获得手指松开位置的横坐标
        float startY = e1.getY();//通过e1.getY（）获得手指按下位置的纵坐标
        float endY = e2.getY();//通过e2.getY（）获得手指松开的纵坐标
        if ((startX - endX) > 50 && Math.abs(startY - endY) < 200) {
            //(startX - endX) > 50 是手指从按下到松开的横坐标距离大于50
            // Math.abs(startY - endY) < 200 是手指从按下到松开的纵坐标的差的绝对值
            mListener.onLeftFling();
            LogUtils.d(context.getClass().getSimpleName() + "--向左滑--");
        }

        if ((endX - startX) > 50 && Math.abs(startY - endY) < 200) {
            mListener.onRightFling();
            LogUtils.d(context.getClass().getSimpleName() + "--向右滑--");
        }

        if ((startY - endY) > 50 && Math.abs(startX - endX) < 200) {
            mListener.onTopFling();
            LogUtils.d(context.getClass().getSimpleName() + "--向上滑--");
        }

        if ((endY - startY) > 50 && Math.abs(startX - endX) < 200) {
            mListener.onBottomFling();
            LogUtils.d(context.getClass().getSimpleName() + "--向下滑--");
        }

        //返回值是重点：如果返回值是true则动作可以执行，如果是flase动作将无法执行
        return true;
    }
}
