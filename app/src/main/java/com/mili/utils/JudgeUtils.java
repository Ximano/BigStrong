package com.mili.utils;

import android.app.Activity;
import android.content.Intent;

import com.mili.app.Constants;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class JudgeUtils {
    /**
     * 跳转到label对应的Activity
     *
     * @param position 标题在数组中的position
     * @param cls   目标Activity的Class
     */
    public static void label2Activity(Activity activity, Class<? extends Activity> cls, int position) {
        activity.startActivity(new Intent(activity, cls).putExtra("label", Constants.TAB_TITLES[position]));
    }
}
