package com.mili.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;
import com.mili.R;
import com.mili.base.BaseActivity;
import com.mili.utils.RichTextUtil;
import com.mili.utils.StatusBarUtil;
import com.mili.widget.CenterAlignImageSpan;
import com.mili.widget.EllipsizeTextView;
import com.mili.widget.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RichTextViewActivity extends BaseActivity {
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.tv_high_light)
    TextView mHighLightText;
    @BindView(R.id.marquee_view)
    UPMarqueeView mMarqueeView;
    @BindView(R.id.etv_expand)
    EllipsizeTextView mExpandTextView;
    @BindView(R.id.tv_icon_span)
    TextView mIconSpan;
    @BindView(R.id.tv_multi_icon_span)
    TextView mMultiIconSpan;
    @BindView(R.id.tv_vertical_center_icon)
    TextView mVerticalCenterIcon;
    @BindView(R.id.tv_url_span)
    TextView mUrlSpan;
    @BindView(R.id.tv_foreground_color_span)
    TextView mForegroundColorSpan;
    @BindView(R.id.tv_background_color_span)
    TextView mBackgroundColorSpan;
    @BindView(R.id.tv_icon_drawable_left_code)
    TextView mDrawableLeftCode;
    private String label;
    private List<View> views;
    private List<String> datas;
    private int[] drawableIds;
    private TextView mLeftView;
    private TextView mRightView;
    private ImageView mIcon;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rich_textview;
    }

    @Override
    protected void fetchExtra() {
        label = getIntent().getStringExtra("label");
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(label);
        // 设置状态栏颜色
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_red), 1f);
        this.mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void initView() {
        initMarqueeView();
        initHighLightText();
        initExpandTextView();
        initSpanTextView();
    }

    private void initMarqueeView() {
        /*=========== 可以自定义布局滚动的view ============*/
        drawableIds = new int[]{R.mipmap.ic_cloud, R.mipmap.ic_rain, R.mipmap.ic_snow, R.mipmap.ic_sunshine};
        datas = new ArrayList<>();
        datas.add("北京  阴天冬北风2~3级_10分钟前");
        datas.add("上海  下雨冬北风2~3级_5分钟前");
        datas.add("广州  下雪冬北风2~3级_2分钟前");
        datas.add("郑州  晴天冬北风2~3级_1分钟前");
        views = new ArrayList<>();

        mMarqueeView.setViews(addViews());
        mMarqueeView.setOnItemClickListener((position, view) -> {
            String[] split = datas.get(position).split("_");
            Toast.makeText(RichTextViewActivity.this, split[1], Toast.LENGTH_SHORT).show();
        });
    }

    private List<View> addViews() {
        for (int i = 0; i < datas.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_marquee_view, null);
            mIcon = view.findViewById(R.id.iv_icon);
            mLeftView = view.findViewById(R.id.tv_left);
            mRightView = view.findViewById(R.id.tv_right);

            String[] item = datas.get(i).split("_");
            mLeftView.setText(item[0]);
            mRightView.setText(item[1]);
            mIcon.setImageDrawable(getDrawable(drawableIds[i]));
            views.add(view);
        }

        return views;
    }

    private void initSpanTextView() {
        setCodeIconSpan();
        setIconSpan(mIconSpan.getText());
        setMultiIconSpan(mMultiIconSpan.getText());
        setVerticalCenterIconSpan(mVerticalCenterIcon.getText());
        setUrlSpan(mUrlSpan.getText());
        setForegroundColorSpan(mForegroundColorSpan.getText());
        setBackgroundColorSpan(mBackgroundColorSpan.getText());
    }


    private void initExpandTextView() {
        mExpandTextView.setText(getString(R.string.text_expand));
        mExpandTextView.setOnTextClickListener(() -> ToastUtils.show(mExpandTextView.getMoreText()));
    }

    /**
     * 多个关键字高亮, 点击事件,
     */
    private void initHighLightText() {
        mHighLightText = findViewById(R.id.tv_high_light);
        // 设置点击事件时，必须添加的配置
        mHighLightText.setMovementMethod(LinkMovementMethod.getInstance());
        // 点击无背景色
        mHighLightText.setHighlightColor(getResources().getColor(android.R.color.transparent));
        CharSequence targetStr = RichTextUtil.getColorString(getString(R.string.text_high_light), "达令", Color.parseColor("#FF00FF"), view -> {
            ToastUtils.show("么么哒~");
        });
        mHighLightText.setText(targetStr);
    }

    private void setCodeIconSpan() {
        Drawable leftDrawable = getResources().getDrawable(R.mipmap.ic_zone);
        leftDrawable.setBounds(0, 0, 5, 5);
        mDrawableLeftCode.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
    }

    private void setIconSpan(CharSequence charSequence) {
        String text = "[icon] " + charSequence;
        SpannableString spannable = new SpannableString(text);
        Drawable drawable = this.getResources().getDrawable(R.mipmap.ic_qq);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //第一个参数drawable 第二个参数对齐方式
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        spannable.setSpan(imageSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mIconSpan.setText(spannable);
    }

    private void setMultiIconSpan(CharSequence charSequence) {
        String text = "[icon] ";
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        Drawable drawable1 = this.getResources().getDrawable(R.mipmap.ic_qq);
        drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
        ImageSpan imageSpan1 = new ImageSpan(drawable1, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(imageSpan1, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //追加一个icon
        spannable.append("[icon] " + charSequence);
        Drawable drawable2 = this.getResources().getDrawable(R.mipmap.ic_wechat);
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
        ImageSpan imageSpan2 = new ImageSpan(drawable2, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(imageSpan2, 7, 13, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mMultiIconSpan.setText(spannable);

    }

    private void setVerticalCenterIconSpan(CharSequence charSequence) {
        String text = "[icon] " + charSequence;
        SpannableString spannable = new SpannableString(text);
        Drawable drawable = this.getResources().getDrawable(R.mipmap.ic_zone);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //图片居中
        CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(imageSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mVerticalCenterIcon.setText(spannable);
    }

    private void setUrlSpan(CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        //URLSpan
        URLSpan urlSpan = new URLSpan("https://www.jianshu.com/u/9006081639f4");
        spannableString.setSpan(urlSpan, 4, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mUrlSpan.setMovementMethod(LinkMovementMethod.getInstance());
        mUrlSpan.setHighlightColor(ContextCompat.getColor(this, R.color.colorAccent));
        mUrlSpan.setText(spannableString);
    }

    private void setForegroundColorSpan(CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        //ForegroundColorSpan
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        spannableString.setSpan(colorSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mForegroundColorSpan.setText(spannableString);
    }

    private void setBackgroundColorSpan(CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        BackgroundColorSpan colorSpan = new BackgroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent));
        spannableString.setSpan(colorSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mBackgroundColorSpan.setText(spannableString);
    }

    @Override
    protected void initData() {
    }

}
