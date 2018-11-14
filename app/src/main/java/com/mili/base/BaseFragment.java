package com.mili.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by TeeMo111 on 2018/11/14.
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;
    protected Activity activity;
    private View mRootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(getLayoutId(), null);
        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 引入布局
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    public abstract View initView();

    /**
     * 初始化数据
     */
    public abstract void initData();
}
