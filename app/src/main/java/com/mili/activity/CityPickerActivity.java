package com.mili.activity;

import android.view.MotionEvent;

import com.mili.R;
import com.mili.base.Label2UIActivity;
import com.mili.utils.LogUtils;

/**
 * Created by TeeMo111 on 2018/11/27.
 */
public class CityPickerActivity extends Label2UIActivity {
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_picker;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if (y1 - y2 > 20) {
//                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                LogUtils.d("向上滑");
            } else if (y2 - y1 > 20) {
//                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                LogUtils.d("向下滑");
            } else if (x1 - x2 > 20) {
//                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                LogUtils.d("向左滑");
            } else if (x2 - x1 > 20) {
//                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                LogUtils.d("向右滑");
            }
        }
//        return true;
        return super.onTouchEvent(event);
    }
}
