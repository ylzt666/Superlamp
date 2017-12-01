package com.linkus.superlamp.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linkus.superlamp.BaseActivity;
import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.beans.GroupBean;
import com.linkus.superlamp.beans.GroupItemBean;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.ScreenUtil;
import com.linkus.superlamp.widget.LampLayout;
import com.linkus.superlamp.widget.ProgressArs;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 54757 on 9/25/2017.
 */

public class LampBulbActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ImageView backLeft;
    @BindView(R.id.tv_page_name)
    TextView pageName;
    @BindView(R.id.tv_top_right)
    TextView tvTopRight;
    @BindView(R.id.lampLayout)
    LampLayout lampLayout;

//    @BindView(R.id.rg_switch)
//    RadioGroup rgSwitch;
    @BindView(R.id.iv_alarm)
    ImageView ivAlarm;

    @BindView(R.id.rg_colour)
    RadioGroup rgColour;

    @BindView(R.id.tv_color)
    TextView tvColor;

    @BindView(R.id.progress_arc)
    ProgressArs progressArs;

    @BindView(R.id.rl_switch)
    RelativeLayout rlSwitch;

    @BindView(R.id.rl_colour)
    PercentRelativeLayout rlColour;

    @BindView(R.id.rb_colour)
    RadioButton rbColour;

    @BindView(R.id.rb_white)
    RadioButton rbWhite;

    @BindView(R.id.clock_open)
    RadioButton clockOpen;

    @BindView(R.id.rg_clock)
    RadioGroup rgClock;

    @BindView(R.id.iv_alarm_device)
    ImageView ivAlarmDevice;

    @BindView(R.id.switch_rg_device_switch)
    RadioGroup rgSwitchDeviceSwitch;

    @BindView(R.id.rg_device_switch)
    RadioGroup rgColourDeviceSwitch;

    private int mColor;
    private int mType;
    private int TypeColour = 1;
    private int TypeSwitch = 2;

    private  boolean isGroup = false;
    private int RequestTimeCode = 3;
    private String currentGroupType;
    private PopupWindow popupWindow;
    private Serializable sData;

    @Override
    protected void beforeShow(Bundle savedInstanceState) {

    }

    @Override
    public void bindView() {
        setContentView(R.layout.activity_lamp_bulp);
        Logger.i(TAG, "height " + ScreenUtil.getScreenHeight(this));
    }

    @Override
    public void findAllViews() {
        pageName.setVisibility(View.VISIBLE);
        backLeft.setVisibility(View.VISIBLE);
        tvTopRight.setVisibility(View.VISIBLE);
        tvTopRight.setText(getResources().getString(R.string.more));
        Intent intent = getIntent();
        sData = intent.getSerializableExtra("data");
        if (sData == null){
            throw new NullPointerException("data is null");
        }
        String pageTitle = "undefind";
        if (sData instanceof GroupItemBean){
            pageTitle = ((GroupItemBean) sData).getGroupName();
            isGroup = false;
        }else if (sData instanceof GroupBean){
            pageTitle = ((GroupBean) sData).getGroupName();
            isGroup = true;
        }else {
            //do nothing
            throw new NullPointerException("undefind");
        }
        pageName.setText(pageTitle);
        if (sData != null) {
            if (sData instanceof GroupItemBean){
                currentGroupType = ((GroupItemBean) sData).getGroupType();
            }else if (sData instanceof GroupBean){
                currentGroupType = ((GroupBean) sData).getGroupType();
            }
            Logger.i(TAG,"currentGroupType is "+currentGroupType);
            if (Constant.GROUP_COLOUR.equals(currentGroupType)) {
                rlColour.setVisibility(View.VISIBLE);
                mType = TypeColour;
            }
            if (Constant.GROUP_WHITE.equals(currentGroupType)) {
                rlColour.setVisibility(View.VISIBLE);
                mType = TypeColour;
            }
            if (Constant.GROUP_SWITCH.equals(currentGroupType)) {
                rlSwitch.setVisibility(View.VISIBLE);
                mType = TypeSwitch;
            }
        }
    }

    @Override
    public void setAllListeners() {
        if (mType == TypeColour) {
            setColorLogic();
        }
        if (mType == TypeSwitch) {
            setSwitchLogic();
        }
    }

    /**
     * 开关逻辑
     */
    private void setSwitchLogic() {
        Logger.i(TAG,"setSwitchLogic");
        boolean isDeviceOpen;
        boolean isAlarmOpen;
        if (sData instanceof GroupItemBean){
            isAlarmOpen = ((GroupItemBean) sData).isAlarmOpen();
            isDeviceOpen = ((GroupItemBean) sData).isDeviceOpen();
        }else if (sData instanceof GroupBean){
            isAlarmOpen = ((GroupBean) sData).isAlarmOpen();
            isDeviceOpen = ((GroupBean) sData).isDeviceOpen();
            isGroup = true;
        }else {
            //do nothing
            throw new NullPointerException("undefind");
        }

        if (isDeviceOpen){
            rgSwitchDeviceSwitch.getChildAt(0).performClick();
        }else {
            rgSwitchDeviceSwitch.getChildAt(1).performClick();
        }
        if (isAlarmOpen){
            rgClock.getChildAt(0).performClick();
        }else {
            rgClock.getChildAt(1).performClick();
        }
        rgClock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.clock_open){//闹钟开关开启

                }else {//clock_close 闹钟开关关闭

                }
            }
        });

        ivAlarmDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LampBulbActivity.this, ClockActivity.class);
                startActivityForResult(intent,RequestTimeCode);
            }
        });
    }

    private void setColorLogic() {
        //设置设备初始开关状态
        boolean isDeviceOpen;
        boolean isAlarmOpen;
        if (sData instanceof GroupItemBean){
            isAlarmOpen = ((GroupItemBean) sData).isAlarmOpen();
            isDeviceOpen = ((GroupItemBean) sData).isDeviceOpen();
        }else if (sData instanceof GroupBean){
            isAlarmOpen = ((GroupBean) sData).isAlarmOpen();
            isDeviceOpen = ((GroupBean) sData).isDeviceOpen();
            isGroup = true;
        }else {
            //do nothing
            throw new NullPointerException("undefind");
        }

        if (isDeviceOpen){
            rgColourDeviceSwitch.getChildAt(0).performClick();
        }else {
            rgColourDeviceSwitch.getChildAt(1).performClick();
        }
//        if (isAlarmOpen){
//            rgClock.getChildAt(0).performClick();
//        }


        //设置亮度进度条
        progressArs.setProgress(0);

        ivAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LampBulbActivity.this,ClockActivity.class);
                startActivityForResult(intent,RequestTimeCode);
            }
        });
        rgColour.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_colour) {//彩灯开
                    tvColor.setVisibility(View.VISIBLE);
                    lampLayout.setIsColdWarmLamp(false, true);
                } else {//白灯
//                    tvColor.setVisibility(View.GONE);
                    lampLayout.setIsColdWarmLamp(true, true);
                    lampLayout.setHasWarmLamp(true, false);
                }
            }
        });

        lampLayout.setOnColorChangeListener(new LampLayout.OnColorChangeListener() {
            @Override
            public void onColorChange(int red, int green, int blue) {
                if (Constant.GROUP_WHITE.equals(currentGroupType)){
                    return;
                }
                Logger.i(TAG, "onColorChange()   red = " + red + "   green = " + green + "   blue = " + blue);
                tvColor.setText(String.format("r:%d : g:%d : b:%d \n", red, green, blue));
                mColor = Color.argb(0xff, red, green, blue);
                tvColor.setBackgroundColor(mColor);
            }

            @Override
            public void onColorChangeEnd(int red, int green, int blue) {
                if (Constant.GROUP_WHITE.equals(currentGroupType)){
                    return;
                }
                Logger.i(TAG, "onColorChangeEnd()   red = " + red + "   green = " + green + "   blue = " + blue);
                mColor = Color.argb(0xff, red, green, blue);
                tvColor.setText(String.format("r:%d : g:%d : b:%d \n", red, green, blue));
                tvColor.setBackgroundColor(mColor);
            }
        });

        lampLayout.setRotateChangeListener(new LampLayout.RotateChangeListener() {
            @Override
            public void onRoateChange(float degress, boolean fromUser) {
                Logger.i(TAG, "onRoateChange"+degress);
            }

            @Override
            public void onRotateChangeStart(float degree,float percent) {
                Logger.i(TAG, "onRotateChangeStart"+degree);
            }

            @Override
            public void onRotateChangeEnd(float degree,float percent) {
                Logger.i(TAG, "onRotateChangeEnd"+degree);
            }
        });

        lampLayout.setWarmChangeListener(new LampLayout.WarmChangeListener() {
            @Override
            public void onWhiteChange(int warm) {
                Logger.i(TAG,"warm is"+warm);
            }

            @Override
            public void onWhiteChanging(int warm) {
                if (Constant.GROUP_COLOUR.equals(currentGroupType)){
                    return;
                }
                Logger.i(TAG,"warm  " + warm + "K");
                tvColor.setText(warm + "K");
            }
        });

        progressArs.setOnBrightChangeListener(new ProgressArs.OnBrightChangeListener() {
            @Override
            public void onBrightChange(int percent,int bright, boolean fromUser) {
                Logger.i(TAG, "onBrightChange bright " + bright);
                tvColor.setText("bright : "+bright +" percent : "+ percent);
            }

            @Override
            public void onBrightChangeEnd(int percent,int bright, boolean fromUser) {
                Logger.i(TAG, "onBrightChangeEnd bright " + bright);

            }
        });

        Logger.i(TAG,"currentGroupType"+currentGroupType);
        if (Constant.GROUP_COLOUR.equals(currentGroupType)) {
            tvColor.setVisibility(View.VISIBLE);
            rbColour.setChecked(true);
            lampLayout.setIsColdWarmLamp(false, true);
//            lampLayout.setColorful(Color.rgb(0,255,230));
        }
        if (Constant.GROUP_WHITE.equals(currentGroupType)) {
//            tvColor.setVisibility(View.GONE);
            rbWhite.setChecked(true);
            lampLayout.setIsColdWarmLamp(true, true);
//            lampLayout.setWhiteWarm(3500);//set the white warm
        }
    }

    @Override
    public void doProcess() {


    }

    @OnClick({R.id.back_left, R.id.tv_top_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_top_right:
                showMorePop();
                break;
        }
    }

    private void showMorePop() {
//        showToast("more click");
        if (popupWindow != null && popupWindow.isShowing()){
            return;
        }
        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.pop_more,null));
        double width = ScreenUtil.getScreenWidth() * 0.4;
        double height = ScreenUtil.getScreenHeight() * 0.3;
        popupWindow.setWidth((int) width);
        popupWindow.setHeight((int) height);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        LinearLayout contentView = (LinearLayout) popupWindow.getContentView();
        TextView tvDeleteDevice  = contentView.findViewById(R.id.tv_delete_device);
        TextView tvDeleteGroup  = contentView.findViewById(R.id.tv_delete_group);
        TextView tvChangeDeviceName  = contentView.findViewById(R.id.tv_change_device_name);
        TextView tvChangeGroupName  = contentView.findViewById(R.id.tv_change_group_name);
        if (isGroup) {//如果是分组的话
            tvDeleteDevice.setVisibility(View.GONE);
            tvDeleteGroup.setVisibility(View.VISIBLE);
            tvChangeDeviceName.setVisibility(View.GONE);
            tvChangeGroupName.setVisibility(View.VISIBLE);
        } else {
            tvDeleteDevice.setVisibility(View.VISIBLE);
            tvDeleteGroup.setVisibility(View.GONE);
            tvChangeDeviceName.setVisibility(View.VISIBLE);
            tvChangeGroupName.setVisibility(View.GONE);
        }
        int childCount = contentView.getChildCount();
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.i(TAG,"View View View");
                switch (v.getId()){
                    case R.id.tv_update:
                        sendUpdateCmd();
                        break;
                    case R.id.tv_delete_device:
                        sendDeleteDeviceCmd();
                        break;
                    case R.id.tv_delete_group:
                        sendDeleteGroupCmd();
                        break;
                    case R.id.tv_recovery:
                        sendRecoveryCmd();
                        break;
                    case R.id.tv_change_device_name:
                        sendChangeDeviceNameCmd();
                        break;
                    case R.id.tv_change_group_name:
                        sendChangeGroupNameCmd();
                        break;
                        default:
//                            showToast("nothing ");
                            break;
                }
                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        };
        for (int i = 0; i < childCount; i++) {
//            contentView.getChildAt(i).setOnTouchListener(touchListener);
            contentView.getChildAt(i).setOnClickListener(clickListener);
        }
        contentView.setOnClickListener(clickListener);
        popupWindow.showAsDropDown(findViewById(R.id.tv_top_right));

    }

    /**
     * 发送修改组名命令
     */
    private void sendChangeGroupNameCmd() {

    }

    /**
     *  发送修改设备命令
     */
    private void sendChangeDeviceNameCmd() {

    }

    /**
     * 发送恢复出厂设置指令
     * 需区分设备分组还是单个设备
     */
    private void sendRecoveryCmd() {

    }

    /**
     * 发送删除分组指令
     */
    private void sendDeleteGroupCmd() {

    }

    /**
     * 发送删除单个设备指令
     */
    private void sendDeleteDeviceCmd() {

    }

    /**
     * 发送分组的更新指令
     * 需区分设备分组还是单个设备
     */
    private void sendUpdateCmd() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestTimeCode && data != null){
            String trigger_time = data.getStringExtra("trigger_time");
            String trigger_freq = data.getStringExtra("trigger_freq");
            String trigger_action = data.getStringExtra("trigger_action");
            Logger.i(TAG, "trigger_time " + trigger_time + "trigger_freq " + trigger_freq + "trigger_action" + trigger_action);

            Constant.TRIGGER triggerFreq = Constant.TRIGGER.valueT(trigger_freq);
            Constant.TRIGGER triggerAction = Constant.TRIGGER.valueT(trigger_action);

            // here got the cmd of user

        }
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
        super.onBackPressed();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Logger.i(TAG,"popupWindow != null && popupWindow.isShowing() != null && popupWindow.isShowing()"+(popupWindow != null && popupWindow.isShowing()));
        if (keyCode == KeyEvent.KEYCODE_BACK ){
                     if (popupWindow != null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
