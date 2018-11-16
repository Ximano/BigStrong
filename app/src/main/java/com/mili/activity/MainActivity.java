package com.mili.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hjq.toast.ToastUtils;
import com.mili.R;
import com.mili.base.BaseActivity;
import com.mili.fragment.FoundFragment;
import com.mili.fragment.HomeFragment;
import com.mili.fragment.MessageFragment;
import com.mili.fragment.MineFragment;
import com.mili.utils.StatusBarUtil;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.content_fragment)
    FrameLayout mFragment;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.nav_view)
    NavigationView mDrawerLayoutNavigationView;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private HomeFragment mHomeFragment;
    private FoundFragment mFoundFragment;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private ActionBarDrawerToggle mToggle;
    private ImageView mSweetHeartView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(getString(R.string.home_pager));
        // 设置状态栏颜色
//        getStatusBarConfig().statusBarColorInt(ContextCompat.getColor(this, R.color.text_high_light), 1f);
        StatusBarUtil.setStatusColor(getWindow(), ContextCompat.getColor(this, R.color.main_status_bar_red), 1f);
        mToolbar.setNavigationOnClickListener(v -> onToggleSupport());
    }

    @Override
    protected void initView() {
        initFragments();
        initDrawerLayoutNavigationView();
        initDrawerLayout();
        initBottomNavigationView();
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
                View mContent = mDrawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    private void initDrawerLayoutNavigationView() {
        mSweetHeartView = mDrawerLayoutNavigationView.getHeaderView(0).findViewById(R.id.iv_sweet_heart);
//        Glide.with(MainActivity.this).load(R.mipmap.sweetHeart).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mSweetHeartView);
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
        changeFragment(mHomeFragment, true);
    }

    private void initBottomNavigationView() {
        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    /**
     * 控制切换Fragment
     *
     * @param fragment
     * @param isInit
     */
    private void changeFragment(Fragment fragment, boolean isInit) {
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
                changeFragment(mHomeFragment, false);
                return true;
            case R.id.home_found:
                changeFragment(mFoundFragment, false);
                return true;
            case R.id.home_message:
                changeFragment(mMessageFragment, false);
                return true;
            case R.id.home_me:
                changeFragment(mMineFragment, false);
                return true;
        }
        return false;
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
        } else {
            super.onBackPressed();
        }

    }
}
