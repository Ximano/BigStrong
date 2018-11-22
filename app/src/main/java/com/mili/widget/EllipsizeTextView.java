package com.mili.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import com.mili.R;

/**
 * Created by Gss on 2018/1/10 0010.
 */

public class EllipsizeTextView extends AppCompatTextView {
    private Context mContext;
    private AttributeSet mAttrs;
    private String mMoreText = "";
    private String mLessText = "";
    private int mMoreTextColor;
    private int mLessTextColor;
    private int mMaxLines;
    private CharSequence mText;
    private BufferType mBufferType = BufferType.NORMAL;

    private OnTextClickListener mListener;

    public interface OnTextClickListener {
        void click();
    }

    public void setOnTextClickListener(OnTextClickListener listener) {
        mListener = listener;
    }

    public EllipsizeTextView(Context context) {
        this(context, null);
    }

    public EllipsizeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EllipsizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mAttrs = attrs;
        init();
    }

    private void init() {
        TypedArray ta = null;
        try {
            if (mAttrs != null) {
                ta = mContext.obtainStyledAttributes(mAttrs, R.styleable.EllipsizeTextView);
                mMoreText = ta.getString(R.styleable.EllipsizeTextView_ell_more_text);
                mLessText = ta.getString(R.styleable.EllipsizeTextView_ell_less_text);
                mMoreTextColor = ta.getColor(R.styleable.EllipsizeTextView_ell_more_color, mContext.getResources().getColor(R.color.red));
                mLessTextColor = ta.getColor(R.styleable.EllipsizeTextView_ell_less_color, mContext.getResources().getColor(R.color.red));
                mMoreText = TextUtils.isEmpty(mMoreText) ? "全部" : mMoreText;
            }
        } finally {
            if (ta != null)
                ta.recycle();
        }
    }

    public void setMoreText(String more) {
        mMoreText = more;
    }

    public String getMoreText() {
        return mMoreText;
    }

    public void setLessText(String less) {
        mLessText = less;
    }

    public String getLessText() {
        return mLessText;
    }

    @Override
    public void setText(final CharSequence text, BufferType type) {
        mText = text;
        mBufferType = type;
        super.setText(text, type);
        if (mMaxLines > 0)
            post(r);
    }

    @Override
    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
        requestLayout();
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            if (getLineCount() <= mMaxLines) {
                return;
            }
            final CharSequence summary = createSummary();
            setTextInternal(summary);
        }
    };

    private void setTextInternal(CharSequence text) {
        super.setText(text, mBufferType);
    }

    private CharSequence createSummary() {
        if (mMoreText == null || mMoreText.length() == 0) {
            return mText;
        }
        Layout layout = getLayout();
        int start = layout.getLineStart(mMaxLines - 1);
        int end = layout.getLineEnd(mMaxLines - 1) - start;

        CharSequence content = mText.subSequence(start, mText.length());


        float moreWidth = getPaint().measureText(mMoreText, 0, mMoreText.length());
        float maxWidth = layout.getWidth() - moreWidth;
        int len = getPaint().breakText(content, 0, content.length(), true, maxWidth, null);
        if (content.charAt(end - 1) == '\n') {
            end = end - 1;
        }
        len = Math.min(len, end);
        return create(mText.subSequence(0, start + len - 1), mMoreText, mMoreTextColor);
    }

    private Spanned create(final CharSequence content, String label, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(label);
//        builder.setSpan(new ForegroundColorSpan(color), 0, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        // 可进行点击
        setMovementMethod(LinkMovementMethod.getInstance());
        // 点击无背景色
        setHighlightColor(getResources().getColor(android.R.color.transparent));
        // TODO: 2018/11/22 展开和收起功能暂未解决
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                setText(mText);
                EllipsizeTextView.super.setMaxLines(Integer.MAX_VALUE);
                requestLayout();
                mListener.click();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }, 0, label.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return new SpannableStringBuilder(content).append("...").append(builder);
    }
}
