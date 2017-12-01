package com.linkus.superlamp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.SharePrefrenceUtil;
import com.linkus.superlamp.utils.StatusBarUtils;
import com.linkus.superlamp.utils.ToastUtils;
import com.linkus.superlamp.widget.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

import static android.os.Build.VERSION_CODES.M;


/**
 * <p> ProjectName： WST</p>
 * <p>
 * Description：
 * </p>
 *
 * @version 1.0
 * @createdate 2016/5/11/011
 */
public abstract class BaseActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks{

    private CustomProgressDialog dialog;
    public String TAG = getClass().getSimpleName();


    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG,"onCreate Activity "+getClass().getSimpleName());
        if (Build.VERSION.SDK_INT >= M){
            checkPermissions();
//            showTranslucentStatusBar();
        }

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        beforeShow(savedInstanceState);
        bindView();//加载布局

        ButterKnife.bind(this);//黄牛刀房子绑定布局下面

        findAllViews();

        setAllListeners();

        doProcess();


//        init();


    }

    private void init(){
        View decorView = getWindow().getDecorView();
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
        if (Build.VERSION.SDK_INT < 19 || !checkDeviceHasNavigationBar()) {
            //一定要判断是否存在按键，否则在没有按键的手机调用会影响别的功能。如之前没有考虑到，导致图传全屏变成小屏显示。
            return;
        } else {
            // 获取属性
            decorView.setSystemUiVisibility(flag);
        }
    }

    /**
     * 判断是否存在虚拟按键
     * @return
     */
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    private void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    protected void checkPermissions(String... permission) {
        assert permission == null;
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE
//                , Manifest.permission.CALL_PHONE
                , Manifest.permission.CHANGE_NETWORK_STATE
                , Manifest.permission.REORDER_TASKS
                , Manifest.permission.VIBRATE
                , Manifest.permission.CALL_PHONE
                , Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.WAKE_LOCK
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
        };
        if (EasyPermissions.hasPermissions(this, perms)) {//检查是否获取该权限
            Log.i(TAG, "已获取权限");
        } else {
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
    }

    /**
     * 在显示之前   getIntent啊 等等
     * @param savedInstanceState
     */
    protected abstract void beforeShow(Bundle savedInstanceState);

    /**
     * 绑定布局
     */
    public  abstract void bindView();

    /**
     * 查找控件
     */
    public  abstract void findAllViews();

    /**
     * 设置所有点击事件
     */
    public  abstract void setAllListeners();

    /**
     * 处理逻辑的地方
     */
    public  abstract void doProcess();

    public void showToast(final String msg){
        if (TextUtils.isEmpty(msg)) {
            return;
        }
//        CustomToast.showToast(this,msg,Toast.LENGTH_SHORT);
//        ToastUtils.showMessage(this,msg);
        handler.post(new Runnable() {
            @Override
            public void run() {
                        ToastUtils.showToast(BaseActivity.this,msg);
            }
        });
    }
    public void showToast(int resId){
        final String msg = getResources().getString(resId);
//        ToastUtils.showMessageLong(this,msg);
//        CustomToast.showToast(this,resId,Toast.LENGTH_SHORT);
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(BaseActivity.this,msg);
            }
        });
    }
    public void startProgressDialog() {
        if (dialog == null) {
            dialog = CustomProgressDialog.createDialog(this);
        }
        dialog.setCanceledOnTouchOutside(false);
        if(!isFinishing()) {
            dialog.setPointRecycle(true);
            dialog.show();
        }
    }

    /**
     * 着色模式显示透明的状态栏
     */
    @TargetApi(M)
    public void showTranslucentStatusBar(int color) {

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏

        Window window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(color));

        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }
    @TargetApi(M)
    public void showTranslucentStatusBar() {

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏

        Window window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor("#ff0000"));

        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }



    public void stopProgressDialog() {
        Logger.i(TAG, "dialog == null>>" + (dialog == null));
        if (!isDestroyed() && !isFinishing() && dialog != null) {
            dialog.dismiss();
        }
    }


    public void showDifferentLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.setTitle("温馨提示")
                .setMessage("您的账号在异地登录，如果不是本人操作。您的密码可能已经泄露，修改密码")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        SharePererenceUtils.removeToken(BaseActivity.this);
//                        SharePererenceUtils.reMoveLoginUserId(BaseActivity.this);
//                        SharePererenceUtils.reMoveLoginUserName(BaseActivity.this);
//                        SharePererenceUtils.
                        dialog.dismiss();
                    }
                }).setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        startActivityForResult(new Intent(BaseActivity.this,LoginActivity.class), Constant.REQUEST_LOGIN);
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 提示登录框
     */
    public void showLoginHintDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您还未登录，现在去登录吗？");
        builder.setPositiveButton("现在就去",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(BaseActivity.this,
//                                LoginActivity.class);
////                        intent.putExtra("from", 1);
//                        startActivityForResult(intent, Constant.REQUEST_LOGIN);
                    }
                });
        builder.setNegativeButton("稍后再说",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //下面两个方法是实现EasyPermissions的EasyPermissions.PermissionCallbacks接口
    //分别返回授权成功和失败的权限
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i(TAG, "onPermissionsGranted " + perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i(TAG, "获取失败的权限" + perms);
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handleChildMsg(msg);
        }
    };

    protected  void handleChildMsg(Message msg){
        // for rent
    }

    public Handler getHandler(){
        return handler;
    }

    @Override
    protected void onStart() {
        super.onStart();
        StatusBarUtils.setWindowStatusBarColor(this,R.color.green);
    }

    public void removeLoginMessage() {
        Logger.i(TAG, "removeLoginMessage cur remove activity_add_device is " + TAG);
        SharePrefrenceUtil.removeKey(this, "login");
        SharePrefrenceUtil.removeKey(this, "last_login_time");
        SharePrefrenceUtil.removeKey(this, "account");
        SharePrefrenceUtil.removeKey(this, "userid");
        SharePrefrenceUtil.removeKey(this, "nickname");
        SharePrefrenceUtil.removeKey(this, "phone");
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        ToastUtils.cancelToast();
        super.onDestroy();
    }

    public static String getMainKey(Context ctx){
        String packageName = ctx.getPackageName();
        PackageManager packageManager = ctx.getPackageManager();
        Bundle bd = null;
        String key = "";
        try {
            ApplicationInfo info = packageManager.getApplicationInfo(packageName, 128);
            bd = info.metaData;//获取metaData标签内容
            String permission = info.permission;
            Logger.i("permission"+permission);
            if (bd != null) {
// 获取AndroidManifest.xml文件中ZHUAMOB_APPKEY
                Object keyO = bd.get("Key");
                key = keyO.toString();//这里获取的就是value值
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException1) {
        }
        return key;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i(TAG,"onActivityResult >> requestCode is "+requestCode+"result code is "+ resultCode);
    }
}
