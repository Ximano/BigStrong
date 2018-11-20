package com.mili.fragment;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.mili.R;
import com.mili.anim.CircularRevealAnim;
import com.mili.app.Constants;
import com.mili.base.BaseDialogFragment;
import com.mili.utils.Utils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;

/**
 * Created by TeeMo111 on 2018/11/20.
 */
public class UsageDialogFragment extends BaseDialogFragment implements CircularRevealAnim.AnimListener, ViewTreeObserver.OnPreDrawListener {
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleView;
    @BindView(R.id.useful_sites_flow_layout)
    TagFlowLayout mUsefulSitesFlowLayout;
    private CircularRevealAnim mCircularRevealAnim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_dialog_usage;
    }

    @Override
    protected void initEventAndData() {
        initCircleAnimation();
        initToolbar();
        mUsefulSitesFlowLayout.setAdapter(new TagAdapter<String>(Constants.TAB_TITLES) {
            @Override
            public View getView(FlowLayout parent, int position, String label) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert label != null;
                tv.setText(label);
                setItemBackground(tv);
                mUsefulSitesFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    ToastUtils.show(Constants.TAB_TITLES[position1]);
                    return true;
                });
                return tv;
            }
        });
    }

    /**
     * 设置标签的背景
     *
     * @param tv
     */
    private void setItemBackground(TextView tv) {
        tv.setBackgroundColor(Utils.randomTagColor());
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    private void initCircleAnimation() {
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(this);
        mTitleView.getViewTreeObserver().addOnPreDrawListener(this);
    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过渡动画 , 使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    private void initToolbar() {
        mTitleView.setText(R.string.useful_sites);
        setToolbarView(R.color.title_black, R.color.white, R.drawable.ic_arrow_back_grey_24dp);
        mToolbar.setNavigationOnClickListener(v -> mCircularRevealAnim.hide(mTitleView, mRootView));
    }

    private void setToolbarView(@ColorRes int textColor, @ColorRes int backgroundColor, @DrawableRes int navigationIcon) {
        mTitleView.setTextColor(ContextCompat.getColor(getContext(), textColor));
        mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), backgroundColor));
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getContext(), navigationIcon));
    }

    @Override
    public void onHideAnimationEnd() {
        dismissAllowingStateLoss();
    }

    @Override
    public void onShowAnimationEnd() {

    }

    @Override
    public boolean onPreDraw() {
        mTitleView.getViewTreeObserver().removeOnPreDrawListener(this);
        mCircularRevealAnim.show(mTitleView, mRootView);
        return true;
    }
}
