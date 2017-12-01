// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.linkus.superlamp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ClockActivity_ViewBinding implements Unbinder {
  private ClockActivity target;

  private View view2131165434;

  private View view2131165220;

  @UiThread
  public ClockActivity_ViewBinding(ClockActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ClockActivity_ViewBinding(final ClockActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_time, "field 'tvTime' and method 'onViewClicked'");
    target.tvTime = Utils.castView(view, R.id.tv_time, "field 'tvTime'", TextView.class);
    view2131165434 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvPageName = Utils.findRequiredViewAsType(source, R.id.tv_page_name, "field 'tvPageName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.back_left, "field 'backLeft' and method 'onViewClicked'");
    target.backLeft = Utils.castView(view, R.id.back_left, "field 'backLeft'", ImageView.class);
    view2131165220 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rgFreq = Utils.findRequiredViewAsType(source, R.id.rg_freq, "field 'rgFreq'", RadioGroup.class);
    target.rgAction = Utils.findRequiredViewAsType(source, R.id.rg_action, "field 'rgAction'", RadioGroup.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ClockActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTime = null;
    target.tvPageName = null;
    target.backLeft = null;
    target.rgFreq = null;
    target.rgAction = null;

    view2131165434.setOnClickListener(null);
    view2131165434 = null;
    view2131165220.setOnClickListener(null);
    view2131165220 = null;
  }
}
