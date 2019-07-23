package com.mili.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.mili.R;

import java.util.Objects;

/**
 * Created by teemo111 on 2018-8-20 15:27:41.
 */
public class LoadingView extends ProgressDialog {
    private String loadingText;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, int theme, String loadingText) {
        super(context, theme);
        this.loadingText = loadingText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        setCancelable(true);
//        setCanceledOnTouchOutside(false);
        setContentView(R.layout.layout_loading);//loading的xml文件
        ((TextView) findViewById(R.id.tv_loading_text)).setText(TextUtils.isEmpty(loadingText) ? "加载中..." : loadingText);
        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }
}
