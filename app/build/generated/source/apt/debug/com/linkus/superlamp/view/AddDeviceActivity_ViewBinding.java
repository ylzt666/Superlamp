// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.linkus.superlamp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddDeviceActivity_ViewBinding implements Unbinder {
  private AddDeviceActivity target;

  private View view2131165220;

  private View view2131165229;

  @UiThread
  public AddDeviceActivity_ViewBinding(AddDeviceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddDeviceActivity_ViewBinding(final AddDeviceActivity target, View source) {
    this.target = target;

    View view;
    target.pageName = Utils.findRequiredViewAsType(source, R.id.tv_page_name, "field 'pageName'", TextView.class);
    target.topRight = Utils.findRequiredViewAsType(source, R.id.tv_top_right, "field 'topRight'", TextView.class);
    view = Utils.findRequiredView(source, R.id.back_left, "field 'backLeft' and method 'onViewClicked'");
    target.backLeft = Utils.castView(view, R.id.back_left, "field 'backLeft'", ImageView.class);
    view2131165220 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.llHasNetwork = Utils.findRequiredViewAsType(source, R.id.ll_has_network, "field 'llHasNetwork'", LinearLayout.class);
    target.llNONetwork = Utils.findRequiredViewAsType(source, R.id.ll_no_network, "field 'llNONetwork'", LinearLayout.class);
    target.etPsd = Utils.findRequiredViewAsType(source, R.id.et_psd, "field 'etPsd'", EditText.class);
    target.psdEye = Utils.findRequiredViewAsType(source, R.id.psd_eye, "field 'psdEye'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.btn_ok, "method 'onViewClicked'");
    view2131165229 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AddDeviceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pageName = null;
    target.topRight = null;
    target.backLeft = null;
    target.llHasNetwork = null;
    target.llNONetwork = null;
    target.etPsd = null;
    target.psdEye = null;

    view2131165220.setOnClickListener(null);
    view2131165220 = null;
    view2131165229.setOnClickListener(null);
    view2131165229 = null;
  }
}
