package com.mili.activity;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;
import com.mili.R;
import com.mili.base.BaseActivity;
import com.mili.fragment.FoundFragment;
import com.mili.fragment.HomeFragment;
import com.mili.fragment.MessageFragment;
import com.mili.fragment.MineFragment;
import com.mili.fragment.UsageDialogFragment;
import com.mili.utils.LogUtils;
import com.mili.utils.StatusBarUtil;
import com.mili.widget.DragFloatActionButton;

import java.lang.reflect.Field;

import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.content_fragment)
    FrameLayout mFragment;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.nav_view_left)
    NavigationView mDrawerLayoutNavigationView;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.float_button)
    DragFloatActionButton mFloatButton;
    private HomeFragment mHomeFragment;
    private FoundFragment mFoundFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private ActionBarDrawerToggle mToggle;
    private GifImageView mPandaGif;
    private CircleImageView mPandaAnim;
    private ImageView mPandaGlide;
    private AnimationDrawable animationDrawable;
    private UsageDialogFragment usageDialogFragment;
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void fetchExtra() {
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(getString(R.string.home_nav_index));
        // 设置状态栏颜色
//        getStatusBarConfig().statusBarColorInt(ContextCompat.getColor(this, R.color.text_high_light), 1f);
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_red), 1f);
        this.mToolbar.setNavigationOnClickListener(v -> onToggleSupport());
    }

    @Override
    protected void initView() {
        initFragments();
        initDrawerLayoutNavigationView();
        initDrawerLayout();
        initBottomNavigationView();
    }

    @OnClick({R.id.float_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.float_button:
                LogUtils.d("float_button: ACTION_DOWN");
                ToastUtils.show("我是客服小妹");
                break;
        }

    }

    @Override
    protected void initData() {
    }

    private void initDrawerLayout() {
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // 加上super(), 有菜单按钮--->箭头的国度动画
                super.onDrawerSlide(drawerView, slideOffset);
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
//                View mContent = mDrawerLayout.getChildAt(0);
//                float scale = 1 - slideOffset;
//                float endScale = 0.8f + scale * 0.2f;
//                float startScale = 1 - 0.3f * scale;
//
//                //设置左边菜单滑动后的占据屏幕大小
//                drawerView.setScaleX(startScale);
//                drawerView.setScaleY(startScale);
//                //设置菜单透明度
//                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));
//
//                //设置内容界面水平和垂直方向偏转量
//                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
//                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
//                //设置内容界面操作无效（比如有button就会点击无效）
//                mContent.invalidate();
//                //设置右边菜单滑动后的占据屏幕大小
//                mContent.setScaleX(endScale);
//                mContent.setScaleY(endScale);
            }
        };
        setDrawerLeftEdgeSize(this, mDrawerLayout, 0.5f);
        setDrawerRightEdgeSize(this, mDrawerLayout, 0.5f);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    /**
     * 左侧抽屉滑动范围控制
     *
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage 占全屏的份额0~1
     */
    private void setDrawerRightEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mRightDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    /**
     * 左侧抽屉滑动范围控制
     *
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage 占全屏的份额0~1
     */
    private void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if (y1 - y2 > 50) {
//                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                LogUtils.d("向上滑");
            } else if (y2 - y1 > 50) {
//                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                LogUtils.d("向下滑");
            } else if (x1 - x2 > 50) {
//                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                LogUtils.d("向左滑");
            } else if (x2 - x1 > 50) {
//                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                LogUtils.d("向右滑");
            }
        }
//        return true;
        return super.onTouchEvent(event);
    }


    private void initDrawerLayoutNavigationView() {
        // 1. 使用GifImageView
        mPandaGif = mDrawerLayoutNavigationView.getHeaderView(0).findViewById(R.id.giv_panda_gif);
        // 2. 使用加载drawable中的animation-list(帧动画)
        mPandaAnim = mDrawerLayoutNavigationView.getHeaderView(0).findViewById(R.id.iv_panda_anim);
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_frame);
        mPandaAnim.setBackground(animationDrawable);
        animationDrawable.start();
        // 3. 使用glide加载本地或网络gif图片
        mPandaGlide = mDrawerLayoutNavigationView.getHeaderView(0).findViewById(R.id.iv_panda_glide);
        Glide.with(this).asGif().load(R.drawable.mm_sweet_heart_gif).into(mPandaGlide);
        Menu menu = mDrawerLayoutNavigationView.getMenu();
        menu.findItem(R.id.nav_item_wan_android).setOnMenuItemClickListener(item -> {
            ToastUtils.show(item.getTitle());
            return true;
        });

        menu.findItem(R.id.nav_item_my_collect).setOnMenuItemClickListener(item -> {
            ToastUtils.show(item.getTitle());
            return true;
        });

        menu.findItem(R.id.nav_item_setting).setOnMenuItemClickListener(item -> {
            ToastUtils.show(item.getTitle());
            return true;
        });

        menu.findItem(R.id.nav_item_logout).setOnMenuItemClickListener(item -> {
            ToastUtils.show(item.getTitle());
            return true;
        });
        menu.findItem(R.id.nav_item_about_us).setOnMenuItemClickListener(item -> {
            ToastUtils.show(item.getTitle());
            return true;
        });
    }

    private void initFragments() {
        // 实例化Fragment
        mHomeFragment = new HomeFragment();
        mFoundFragment = new FoundFragment();
        mMessageFragment = new MessageFragment();
        mMineFragment = new MineFragment();

        // 示例MainActivity传至到Fragment
        Bundle mBundle = new Bundle();
        mBundle.putString("key", "value");
        mMineFragment.setArguments(mBundle);

        // 展示默认Fragment
        changeFragment(getString(R.string.home_nav_index), mHomeFragment, true);
    }

    private void initBottomNavigationView() {
        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    /**
     * 控制切换Fragment
     *
     * @param title
     * @param fragment
     * @param isInit
     */
    private void changeFragment(String title, Fragment fragment, boolean isInit) {
        mTitleTv.setText(title);
        if (fragment == null) {
            return;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        int contentId = R.id.content_fragment;
        String tag = fragment.getClass().getName();

        if (isInit) {
            int count = supportFragmentManager.getBackStackEntryCount();
            for (int i = 0; i < count; i++) {
                supportFragmentManager.popBackStack();
            }
            ft.replace(contentId, fragment, tag);
        } else {
            for (Fragment preFragment : supportFragmentManager.getFragments()) {
                if (preFragment != null) {
                    if (preFragment.isVisible()) {
                        ft.hide(preFragment);
                    }
                }
            }
            if (supportFragmentManager.findFragmentByTag(tag) != null) {
                ft.show(fragment);
            } else {
                ft.add(contentId, fragment, tag);
            }
        }
        // ft.commit();
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                changeFragment(getString(R.string.home_nav_index), mHomeFragment, false);
                return true;
            case R.id.home_found:
                changeFragment(getString(R.string.home_nav_found), mFoundFragment, false);
                return true;
            case R.id.home_message:
                changeFragment(getString(R.string.home_nav_message), mMessageFragment, false);
                return true;
            case R.id.home_me:
                changeFragment(getString(R.string.home_nav_me), mMineFragment, false);
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_usage:
                if (usageDialogFragment == null) {
                    usageDialogFragment = new UsageDialogFragment();
                }
                if (!isDestroyed() && usageDialogFragment.isAdded()) {
                    usageDialogFragment.dismiss();
                }
                usageDialogFragment.show(getSupportFragmentManager(), "UsageDialogFragment");
                break;
            case R.id.action_search:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onToggleSupport() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            LogUtils.d("isDrawerOpen: " + mDrawerLayout.isDrawerOpen(GravityCompat.START));
        } /*else if (usageDialogFragment != null
                && usageDialogFragment.getDialog() != null
                && usageDialogFragment.getDialog().isShowing()) {
            usageDialogFragment.hide();
            LogUtils.d("usageDialogFragment.getDialog().isShowing(): " + usageDialogFragment.getDialog().isShowing());
        } */ else {
            super.onBackPressed();
            LogUtils.d("super.onBackPressed()");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animationDrawable.stop();
    }
}
