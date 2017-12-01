// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.linkus.superlamp.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentDevices_ViewBinding implements Unbinder {
  private FragmentDevices target;

  @UiThread
  public FragmentDevices_ViewBinding(FragmentDevices target, View source) {
    this.target = target;

    target.tvLink = Utils.findRequiredViewAsType(source, R.id.tv_link, "field 'tvLink'", TextView.class);
    target.rvList = Utils.findRequiredViewAsType(source, R.id.rv_list, "field 'rvList'", RecyclerView.class);
    target.srlLayout = Utils.findRequiredViewAsType(source, R.id.srl_layout, "field 'srlLayout'", SmartRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentDevices target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvLink = null;
    target.rvList = null;
    target.srlLayout = null;
  }
}
