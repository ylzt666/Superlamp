// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.linkus.superlamp.R;
import com.linkus.superlamp.widget.LampLayout;
import com.linkus.superlamp.widget.ProgressArs;
import com.zhy.android.percent.support.PercentRelativeLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LampBulbActivity_ViewBinding implements Unbinder {
  private LampBulbActivity target;

  private View view2131165220;

  private View view2131165435;

  @UiThread
  public LampBulbActivity_ViewBinding(LampBulbActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LampBulbActivity_ViewBinding(final LampBulbActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.back_left, "field 'backLeft' and method 'onViewClicked'");
    target.backLeft = Utils.castView(view, R.id.back_left, "field 'backLeft'", ImageView.class);
    view2131165220 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.pageName = Utils.findRequiredViewAsType(source, R.id.tv_page_name, "field 'pageName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_top_right, "field 'tvTopRight' and method 'onViewClicked'");
    target.tvTopRight = Utils.castView(view, R.id.tv_top_right, "field 'tvTopRight'", TextView.class);
    view2131165435 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.lampLayout = Utils.findRequiredViewAsType(source, R.id.lampLayout, "field 'lampLayout'", LampLayout.class);
    target.ivAlarm = Utils.findRequiredViewAsType(source, R.id.iv_alarm, "field 'ivAlarm'", ImageView.class);
    target.rgColour = Utils.findRequiredViewAsType(source, R.id.rg_colour, "field 'rgColour'", RadioGroup.class);
    target.tvColor = Utils.findRequiredViewAsType(source, R.id.tv_color, "field 'tvColor'", TextView.class);
    target.progressArs = Utils.findRequiredViewAsType(source, R.id.progress_arc, "field 'progressArs'", ProgressArs.class);
    target.rlSwitch = Utils.findRequiredViewAsType(source, R.id.rl_switch, "field 'rlSwitch'", RelativeLayout.class);
    target.rlColour = Utils.findRequiredViewAsType(source, R.id.rl_colour, "field 'rlColour'", PercentRelativeLayout.class);
    target.rbColour = Utils.findRequiredViewAsType(source, R.id.rb_colour, "field 'rbColour'", RadioButton.class);
    target.rbWhite = Utils.findRequiredViewAsType(source, R.id.rb_white, "field 'rbWhite'", RadioButton.class);
    target.clockOpen = Utils.findRequiredViewAsType(source, R.id.clock_open, "field 'clockOpen'", RadioButton.class);
    target.rgClock = Utils.findRequiredViewAsType(source, R.id.rg_clock, "field 'rgClock'", RadioGroup.class);
    target.ivAlarmDevice = Utils.findRequiredViewAsType(source, R.id.iv_alarm_device, "field 'ivAlarmDevice'", ImageView.class);
    target.rgSwitchDeviceSwitch = Utils.findRequiredViewAsType(source, R.id.switch_rg_device_switch, "field 'rgSwitchDeviceSwitch'", RadioGroup.class);
    target.rgColourDeviceSwitch = Utils.findRequiredViewAsType(source, R.id.rg_device_switch, "field 'rgColourDeviceSwitch'", RadioGroup.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LampBulbActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.backLeft = null;
    target.pageName = null;
    target.tvTopRight = null;
    target.lampLayout = null;
    target.ivAlarm = null;
    target.rgColour = null;
    target.tvColor = null;
    target.progressArs = null;
    target.rlSwitch = null;
    target.rlColour = null;
    target.rbColour = null;
    target.rbWhite = null;
    target.clockOpen = null;
    target.rgClock = null;
    target.ivAlarmDevice = null;
    target.rgSwitchDeviceSwitch = null;
    target.rgColourDeviceSwitch = null;

    view2131165220.setOnClickListener(null);
    view2131165220 = null;
    view2131165435.setOnClickListener(null);
    view2131165435 = null;
  }
}
