package com.linkus.superlamp.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkus.superlamp.BaseActivity;
import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.adapters.HomePageAdapter;
import com.linkus.superlamp.fragments.FragmentDevices;
import com.linkus.superlamp.fragments.FragmentGroups;
import com.linkus.superlamp.fragments.FragmentScenes;
import com.linkus.superlamp.glide.GlideCircleTransform;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.ScreenUtil;
import com.linkus.superlamp.utils.StatusBarUtils;
import com.linkus.superlamp.widget.HorizontalScrollDisabledViewPager;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingMenuLayout;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    public static final int ADD_DEVICES = 1;
    private long lastFinishTime ;
    @BindView(R.id.show_drawer)
    ImageView showDrawer;
    @BindView(R.id.tv_page_name)
    TextView pageName;
    @BindView(R.id.iv_top_right)
    ImageView topRight;
    @BindView(R.id.drawerlayout)
    FlowingDrawer drawer;
    @BindView(R.id.content)
    RelativeLayout content;
    @BindView(R.id.menulayout)
    FlowingMenuLayout menulayout;
    @BindView(R.id.cb_content)
    HorizontalScrollDisabledViewPager cbContent;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.avator)
    ImageView ivAvator;
    @BindView(R.id.ll_config)
    LinearLayout llConfig;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.ll_help)
    LinearLayout llHelp;
    @BindView(R.id.top_bar)
    RelativeLayout topBarContent;

    private ArrayList<Fragment> listFragments;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Logger.i(TAG, "onKeyDown ==>> " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return doubleClick();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击是否退出
     * @return
     */
    private boolean doubleClick() {
        Logger.i(TAG, " need to finish System.currentTimeMillis() " + System.currentTimeMillis() + " >> lastFinishTime " + lastFinishTime);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastFinishTime < 2000) {
            finish();
        } else {
            showToast("再按一次退出");
            lastFinishTime = currentTimeMillis;
            return true;
        }
        lastFinishTime = currentTimeMillis;
        return false;
    }

    @Override
    protected void beforeShow(Bundle savedInstanceState) {
    }

    @Override
    public void bindView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void findAllViews() {
        showDrawer.setVisibility(View.VISIBLE);
        showDrawer.setPadding(0,0,0,0);
        pageName.setVisibility(View.VISIBLE);
        topRight.setVisibility(View.VISIBLE);
        pageName.setText("LINKUS LIGHT");

        topRight.setImageResource(R.drawable.nav_addition);
        topRight.setScaleType(ImageView.ScaleType.FIT_XY);
        topRight.setPadding(0,0,0,0);
        //控制状态栏 和 top_bar 高度
        int statusBarHeight = StatusBarUtils.getWindowStatusBarHeight(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtil.getScreenWidth(), ScreenUtil.dip2px(this, 50)+ statusBarHeight);
        Constant.mainStatusBarHeight = ScreenUtil.dip2px(this, 50)+ statusBarHeight;
        Constant.mainBottomHeight = ScreenUtil.dip2px(this,60);
        topBarContent.setPadding(0,statusBarHeight,0,0);
        topBarContent.setLayoutParams(params);

        Glide.with(this)
//                .load("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=464871635,3825712800&fm=11&gp=0.jpg")
                .load("")
//                .placeholder(new ColorDrawable(Color.parseColor("#FFFFFFFF")))
                .transform(new GlideCircleTransform(this,0x818DA0))
                .into(ivAvator);
    }

    @Override
    public void setAllListeners() {
        drawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);
        drawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Logger.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
//                Logger.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
                ViewHelper.setTranslationX(content,offsetPixels * 0.8f);
            }
        });
        FragmentDevices deviceFragment = new FragmentDevices();
        FragmentGroups groupsFragment = new FragmentGroups();
        FragmentScenes scenesFragment = new FragmentScenes();
        listFragments = new ArrayList<>();
        listFragments.add(deviceFragment);
        listFragments.add(groupsFragment);
        listFragments.add(scenesFragment);
        cbContent.setAdapter(new HomePageAdapter(getSupportFragmentManager(), listFragments));
        cbContent.setNoScroll(true);

        int childCount = llBottom.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View tab = llBottom.getChildAt(i);
            final int finalI = i;
            tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cbContent.setCurrentItem(finalI,true);
                }
            });
        }

        cbContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {//场景加号影藏掉
                if (position == 2){//场景
                    topRight.setVisibility(View.GONE);
                }else {
                    topRight.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void doProcess() {
        // attach to current activity_add_device;

    }

    @Override
    protected void onDestroy() {
        Logger.i(TAG,"onDestroy onDestroy");
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @OnClick({R.id.show_drawer, R.id.iv_top_right, R.id.ll_config, R.id.ll_about, R.id.ll_help})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.show_drawer:
                showDrawer();
                break;
            case R.id.iv_top_right:
                topAddClick();
                break;
            case R.id.ll_config:
                configClicked(v);
                break;
            case R.id.ll_about:
                aboutClicked(v);
                break;
            case R.id.ll_help:
                helpClicked(v);
                break;
        }
    }

    private void helpClicked(View v) {
        showToast("帮助点击");
    }

    private void aboutClicked(View v) {
        showToast("关于点击");
    }

    private void configClicked(View v) {
        showToast("配置点击");
    }

    private void topAddClick() {
        if (cbContent.getCurrentItem() == 0){//设备
            FragmentDevices devices = (FragmentDevices) listFragments.get(0);
            devices.addDevice();
        }
        if (cbContent.getCurrentItem() == 1){//分组
            FragmentGroups groups = (FragmentGroups) listFragments.get(1);
            groups.addGroup();
        }

        if (cbContent.getCurrentItem() == 2){//场景
            showToast("场景添加");
            FragmentScenes scenes = (FragmentScenes) listFragments.get(2);
            scenes.addScene();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Logger.i(TAG,"width : "+menulayout.getLayoutParams().width+"screen with : "+ ScreenUtil.getScreenWidth(this));

    }

    private void showDrawer() {
        drawer.openMenu(true);
    }



}
