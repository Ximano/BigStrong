package com.mili.fragment;

import com.mili.R;
import com.mili.base.BaseFragment;

/**
 * Created by TeeMo111 on 2018/11/14.
 */
public class MineFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        if (isAdded() && getArguments() != null) {
            String value = getArguments().getString("key");
        }
    }

    @Override
    public void initData() {

    }
}
