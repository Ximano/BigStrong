package com.mili.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mili.R;
import com.mili.gilde.GlideApp;
import com.mili.utils.Utils;

/**
 * @author: strong
 * @date : 2019-07-25
 * @dec :
 */
public class GlideActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gilde);

        imageView = findViewById(R.id.iv_1);
        String url = "https://api.qa.tourscool.net/img/common/review/u=552639409,605634343&fm=26&gp=0.jpg";
//        String url = "http://img.tourscool.com/images/product/5c98c5fdc5ac9.jpg/600x338";
        GlideApp.with(this).asBitmap().load(url).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                int height = Utils.getScreenWidth(GlideActivity.this) * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = height;
                para.width = Utils.getScreenWidth(GlideActivity.this);
                imageView.setImageBitmap(resource);
            }
        });
    }
}
