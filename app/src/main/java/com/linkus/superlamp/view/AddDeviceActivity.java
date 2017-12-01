package com.linkus.superlamp.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linkus.superlamp.BaseActivity;
import com.linkus.superlamp.R;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.NetTools;
import com.linkus.superlamp.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 54757 on 9/24/2017.
 */

public class AddDeviceActivity extends BaseActivity {
    @BindView(R.id.tv_page_name)
    TextView pageName;
    @BindView(R.id.tv_top_right)
    TextView topRight;
    @BindView(R.id.back_left)
    ImageView backLeft;
    @BindView(R.id.ll_has_network)
    LinearLayout llHasNetwork;
    @BindView(R.id.ll_no_network)
    LinearLayout llNONetwork;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.psd_eye)
    CheckBox psdEye;

    private boolean etPsdHastText;


    @Override
    protected void beforeShow(Bundle savedInstanceState) {

    }

    @Override
    public void bindView() {
        setContentView(R.layout.activity_add_device);
    }

    @Override
    public void findAllViews() {
        backLeft.setVisibility(View.VISIBLE);
        pageName.setText("添加设备");
    }
    boolean isInit = true;
    @Override
    public void setAllListeners() {
        etPsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1){
                   if (psdEye.isChecked()){
                       etPsd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                   }else {
                       etPsd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                       etPsd.setSelection(1);
                   }
                }
                if (s.length() == 0){
                    etPsd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });
        psdEye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPsd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etPsd.setSelection(etPsd.getText().length());
                } else {
                    etPsd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPsd.setSelection(etPsd.getText().length());
                }
            }
        });

    }

    @Override
    public void doProcess() {
        if (NetTools.isNetworkConnected(this)) {
            llNONetwork.setVisibility(View.GONE);
            llHasNetwork.setVisibility(View.VISIBLE);
        } else {
            llHasNetwork.setVisibility(View.GONE);
            llNONetwork.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.back_left, R.id.btn_ok})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                goBack();
                break;
            case R.id.btn_ok:
                handleEnsurePressed();
                break;
        }
    }


    private void handleEnsurePressed() {
        showAddResultDialog(true);
    }

    private void showAddResultDialog(boolean success) {
        Dialog dialog = new Dialog(this, R.style.Dialog_FULL_SCREEN);
        StatusBarUtils.setWindowStatusBarColor(dialog, R.color.transiant_44);
        dialog.setContentView(R.layout.dialog_add_result);
        dialog.show();
        View successView = dialog.findViewById(R.id.ll_success);
        View failedView = dialog.findViewById(R.id.ll_failed);
        if (success) {
            successView.setVisibility(View.VISIBLE);
            failedView.setVisibility(View.GONE);
        } else {
            successView.setVisibility(View.GONE);
            failedView.setVisibility(View.VISIBLE);
        }

    }

    private void goBack() {
        finish();
    }
}
