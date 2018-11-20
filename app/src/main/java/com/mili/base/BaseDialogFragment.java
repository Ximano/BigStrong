package com.mili.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by TeeMo111 on 2018/11/20.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    public View mRootView;
    private Unbinder mButterKnife;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayout(), container, false);
        mButterKnife = ButterKnife.bind(this, mRootView);
        initEventAndData();
        return mRootView;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            //防止连续点击add多个fragment
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前dialogFragment的UI布局
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 初始化事件和数据
     */
    protected abstract void initEventAndData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mButterKnife != null && mButterKnife != Unbinder.EMPTY) {
            mButterKnife.unbind();
            mButterKnife = null;
        }
    }

}
