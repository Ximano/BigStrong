package com.mili.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mili.R;
import com.mili.utils.RichTextUtil;

public class KeywordsHighLightActivity extends AppCompatActivity {
    private TextView tvHigh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keywords);

        tvHigh = findViewById(R.id.tv_high_light);
        tvHigh.setMovementMethod(LinkMovementMethod.getInstance());// 设置点击事件时，必须添加的配置
        String originStr = "小米粒是谁, 小米粒是个小懒蛋";
        CharSequence targetStr = RichTextUtil.getColorString(originStr, "小米粒", Color.parseColor("#FF00FF"), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(KeywordsHighLightActivity.this, "么么哒~", Toast.LENGTH_SHORT).show();
            }
        });
        tvHigh.setText(targetStr);
    }
}
