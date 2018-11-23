package com.mili.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mili.R;
import com.mili.utils.Utils;

/**
 * Package Name:com.xes.jazhanghui.views ClassName: ImageTextView <br/>
 * Description: 解决TextView 图片大小不可控问题. <br/>
 * Date: 2017-2-21 下午4:52:45 <br/>
 *
 * @author lihuixin
 */
public class ImageTextView extends AppCompatTextView {
    private Drawable mDrawable;//设置的图片
    private int mScaleWidth; // 图片的宽度
    private int mScaleHeight;// 图片的高度
    private int mPosition;// 图片的位置 1左2上3右4下

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ImageTextView);

        mDrawable = typedArray.getDrawable(R.styleable.ImageTextView_drawable);
        mScaleWidth = typedArray
                .getDimensionPixelOffset(
                        R.styleable.ImageTextView_drawableWidth,
                        Utils.dp2px(20));
        mScaleHeight = typedArray.getDimensionPixelOffset(
                R.styleable.ImageTextView_drawableHeight,
                Utils.dp2px(20));
        mPosition = typedArray.getInt(R.styleable.ImageTextView_position, 3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mDrawable != null) {
            mDrawable.setBounds(0, 0, Utils.dp2px(mScaleWidth),
                    Utils.dp2px(mScaleHeight));

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mPosition) {
            case 1:
                this.setCompoundDrawables(mDrawable, null, null, null);
                break;
            case 2:
                this.setCompoundDrawables(null, mDrawable, null, null);
                break;
            case 3:
                this.setCompoundDrawables(null, null, mDrawable, null);
                break;
            case 4:
                this.setCompoundDrawables(null, null, null, mDrawable);
                break;
            default:
                break;
        }
    }

    /**
     * 设置左侧图片并重绘
     *
     * @param drawable
     */
    public void setDrawableLeft(Drawable drawable) {
        this.mDrawable = drawable;
        invalidate();
    }

    /**
     * 设置左侧图片并重绘
     *
     * @param drawableRes
     * @param context
     */
    public void setDrawableLeft(int drawableRes, Context context) {
        this.mDrawable = context.getResources().getDrawable(drawableRes);
        invalidate();
    }
}
