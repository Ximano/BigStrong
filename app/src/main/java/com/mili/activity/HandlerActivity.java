package com.mili.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class HandlerActivity extends AppCompatActivity {

    // 非静态内部类
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 子线程中的Handler发送Message到主线程的消息队列中，
        // 主线程中程序在启动的时候main()方法中执行一个prepareMainLooper()方法，
        // 此方法中又执行一个prepare()方法，该方法内部new Looper()该构造中又new一个MessageQueue()实例，
        // Looper.loop()方法中维护一个while(true)的死循环，Message.next()方法，
        // 就是在消息队列中不停的取有没有下一个消息，如果有下一个消息，
        // 主线程中的Handler实例执行一个HandleMessage()方法，对消息进行处理

        // 匿名内部类
        new Thread(){
            @Override
            public void run() {
                super.run();
                // todo 如果进行一些耗时的操作，activity被finish的时候
                handler.sendEmptyMessage(1);
            }
        }.start();



        // 在子线程中维护一个Handler机制
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                    }
                };
                Looper.loop();
                handler.sendEmptyMessage(1);
            }
        }.start();
    }
}
