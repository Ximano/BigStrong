package com.mili.app;

import android.graphics.Color;

import com.mili.R;

/**
 * Created by TeeMo111 on 2018/11/20.
 */
public class Constants {
    // Logcat中日志过滤tag
    public static final String TAG = "TAG";
    /**
     * Tab colors
     */
    public static final int[] TAB_COLORS = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    /**
     * Tab colors
     */
    public static final int[] TAB_DRAWABLES = new int[]{
            R.drawable.item_selector1,
            R.drawable.item_selector2,
            R.drawable.item_selector3,
            R.drawable.item_selector4,
            R.drawable.item_selector5,
            R.drawable.item_selector6,
            R.drawable.item_selector7,
            R.drawable.item_selector1,
            R.drawable.item_selector2,
            R.drawable.item_selector3,
            R.drawable.item_selector4,
            R.drawable.item_selector4
    };

    /**
     * Tab titles
     */
    public static final String[] TAB_TITLES = new String[]{
            "Rich Text",
            "Grid View",
            "City Picker",
            "Kotlin",
            "RxAndroid",
            "MVP",
            "MVVM",
            "Handler",
            "LifeCycle",
            "CustomView",
            "navigation",
            "LoadingView",
            "Shape Use"
    };
}
