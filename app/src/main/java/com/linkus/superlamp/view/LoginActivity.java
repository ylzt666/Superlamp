package com.linkus.superlamp.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.linkus.superlamp.BaseActivity;
import com.linkus.superlamp.R;
import com.linkus.superlamp.utils.SharePrefrenceUtil;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jizi.chen on 2017/8/28.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.rl_login)
    RelativeLayout reLogin;

    @BindView(R.id.tv_login_hit)
    TextView tvLoginHit;
    @Override
    protected void beforeShow(Bundle savedInstanceState) {

    }

    @Override
    public void bindView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void findAllViews() {

    }

    @Override
    public void setAllListeners() {

    }

    @Override
    public void doProcess() {
        tvLoginHit.setText("Login with Amazon");
    }

    @OnClick({R.id.rl_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_login:
                doLogin();
                break;
        }
    }

    private void doLogin() {
        SharePrefrenceUtil.put(this,"isLogin","true");
        goMainActivity();
    }


    public void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
