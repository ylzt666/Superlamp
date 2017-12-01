package com.linkus.superlamp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.linkus.superlamp.BaseActivity;
import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.XulUtils;
import com.linkus.superlamp.widget.BorderRadioButton;
import com.linkus.superlamp.widget.wheelview.adapter.NumericWheelAdapter;
import com.linkus.superlamp.widget.wheelview.widget.WheelView;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 54757 on 10/11/2017.
 */

public class ClockActivity extends BaseActivity {


    private WheelView hour;
    private WheelView mins;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_page_name)
    TextView tvPageName;
    @BindView(R.id.back_left)
    ImageView backLeft;
    @BindView(R.id.rg_freq)
    RadioGroup rgFreq;
    @BindView(R.id.rg_action)
    RadioGroup rgAction;


    private String triggerTime;
    private Constant.TRIGGER triggerFrequence = Constant.TRIGGER.TRIGGER_FREQUENCE_UNDEFINED;
    private Constant.TRIGGER triggerAction = Constant.TRIGGER.TRIGGER_FREQUENCE_UNDEFINED;

    /**
     * 是否已显示时间弹框
     */
    private boolean hasShowDialog;


    @Override
    protected void beforeShow(Bundle savedInstanceState) {

    }

    @Override
    public void bindView() {
        setContentView(R.layout.activity_clock);
    }

    @Override
    public void findAllViews() {
        backLeft.setVisibility(View.VISIBLE);
        String title = getIntent().getStringExtra("title");
        title = TextUtils.isEmpty(title) ? "undefined" : title;
        tvPageName.setVisibility(View.VISIBLE);
        tvPageName.setText(title);
    }

    @Override
    public void setAllListeners() {
        setFrequencyLogic();
        setActionLogic();
    }

    private void setActionLogic() {
        int count = rgAction.getChildCount();
        Constant.TRIGGER[] triggers = {
                Constant.TRIGGER.TRIGGER_ACTION_OPEN,
                Constant.TRIGGER.TRIGGER_ACTION_CLOSE,
        };
        for (int i = 0; i < count; i++) {
            View childAt = rgAction.getChildAt(i);
            if (childAt instanceof BorderRadioButton) {
                ((BorderRadioButton) childAt).setText(triggers[i].getName());
                ((BorderRadioButton) childAt).setType(triggers[i].getValue());
            }
        }
        rgAction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                Logger.i(TAG,"BorderRadioButton is "+ (findViewById(checkedId) instanceof BorderRadioButton));
                View brbutton = findViewById(checkedId);
                if (brbutton instanceof BorderRadioButton ) {
                    BorderRadioButton button = ((BorderRadioButton) brbutton);
                    if (button.isChecked()) {
                        int type = button.getType();
                        String name = button.getText().toString();
                        Constant.TRIGGER trigger = Constant.TRIGGER.valueT(name);
                        triggerAction = trigger;
                        Logger.i(TAG, "current checked button is trigger action " + trigger + " type " + type);
                    }
                }
            }
        });
    }

    private void setFrequencyLogic() {
        int childCount = rgFreq.getChildCount();
        Constant.TRIGGER[] triggers = {
                Constant.TRIGGER.TRIGGER_FREQUENCE_ONCE,
                Constant.TRIGGER.TRIGGER_FREQUENCE_PERTIME,
                Constant.TRIGGER.TRIGGER_FREQUENCE_PERHOUR,
                Constant.TRIGGER.TRIGGER_FREQUENCE_EVERYDAY,
        };
        for (int i = 0; i < childCount; i++) {
            View childAt = rgFreq.getChildAt(i);
            if (childAt instanceof BorderRadioButton) {
                ((BorderRadioButton) childAt).setText(triggers[i].getName());
                ((BorderRadioButton) childAt).setType(triggers[i].getValue());
            }
        }

        rgFreq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                Logger.i(TAG,"BorderRadioButton is "+ (findViewById(checkedId) instanceof BorderRadioButton));
                View brbutton = findViewById(checkedId);
                if (brbutton instanceof BorderRadioButton ) {
                    BorderRadioButton button = ((BorderRadioButton) brbutton);
                    if (button.isChecked()) {
                        int type = button.getType();
                        String name = button.getText().toString();
                        Constant.TRIGGER trigger = Constant.TRIGGER.valueT(name);
                        triggerFrequence = trigger;
                        Logger.i(TAG, "current checked button is trigger freq " + trigger + "type" + type);
                    }
                }
            }
        });
    }

    @Override
    public void doProcess() {

    }

    @OnClick({R.id.tv_time, R.id.back_left})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_time:
                showTime();
                break;
            case R.id.back_left:
                finish();
                break;
        }
    }

    /**
     * 显示时间
     */
    private void showTimeDialog() {
        final Dialog dialog = new Dialog(this, R.style.Dialog_FULL_SCREEN);
        if (hasShowDialog) {
            return;
        }
        dialog.show();
        hasShowDialog = true;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                hasShowDialog = false;
            }
        });
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.time_picker_layout);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);

        Calendar c = Calendar.getInstance();
        int curHour = c.get(Calendar.HOUR_OF_DAY);
        int curMin = c.get(Calendar.MINUTE);


        hour = (WheelView) window.findViewById(R.id.hour);
        initHour();
        mins = (WheelView) window.findViewById(R.id.mins);
        initMins();
        // 设置当前时间
        hour.setCurrentItem(curHour);
        mins.setCurrentItem(curMin);


        hour.setVisibleItems(7);
        mins.setVisibleItems(7);


        if (!TextUtils.isEmpty(triggerTime)){//设置了闹钟时间的话 显示轮子定位
            String[] split = triggerTime.split(":");
            if (split.length == 2){
                if (TextUtils.isDigitsOnly(split[0])){
                    hour.setCurrentItem(XulUtils.tryParseInt(split[0]));
                }
                if (TextUtils.isDigitsOnly(split[1])){
                    mins.setCurrentItem(XulUtils.tryParseInt(split[1]));
                }
            }
        }
        // 设置监听
        Button ok = (Button) window.findViewById(R.id.set);
        Button cancel = (Button) window.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String str = String.format(Locale.CHINA, "%02d:   %02d", hour.getCurrentItem(), mins.getCurrentItem());
                showToast(str);
                triggerTime =  hour.getCurrentItem() + ":" + mins.getCurrentItem();
                tvTime.setText(str);
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });

        LinearLayout cancelLayout = (LinearLayout) window.findViewById(R.id.view_none);
        cancelLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.cancel();
                return false;
            }
        });
    }

    /**
     * 初始化时
     */
    private void initHour() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this, 0, 23, "%02d");
        numericWheelAdapter.setLabel(" 时");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        hour.setViewAdapter(numericWheelAdapter);
        hour.setCyclic(true);
    }

    /**
     * 初始化分
     */
    private void initMins() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this, 0, 59, "%02d");
        numericWheelAdapter.setLabel(" 分");
//		numericWheelAdapter.setTextSize(15);  设置字体大小
        mins.setViewAdapter(numericWheelAdapter);
        mins.setCyclic(true);
    }

    public void showTime() {
        showTimeDialog();
    }


    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("trigger_time", triggerTime);// hour:min
        intent.putExtra("trigger_freq", "" + triggerFrequence.getValue());
        intent.putExtra("trigger_action", "" + triggerAction.getValue());
        setResult(RESULT_OK, intent);
        super.finish();
    }

    /**
     * 设置闹钟初始时间
     * @param hour 小时
     * @param min 分钟
     */
    public void setTriggerTime(int hour,int min){
        String str = String.format(Locale.CHINA, "%02d:   %02d", hour, min);
        triggerTime =  hour + ":" + min;
        tvTime.setText(str);
    }


}
