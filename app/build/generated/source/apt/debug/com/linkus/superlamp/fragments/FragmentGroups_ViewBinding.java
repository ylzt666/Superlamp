// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.linkus.superlamp.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentGroups_ViewBinding implements Unbinder {
  private FragmentGroups target;

  @UiThread
  public FragmentGroups_ViewBinding(FragmentGroups target, View source) {
    this.target = target;

    target.rvList = Utils.findRequiredViewAsType(source, R.id.rv_list, "field 'rvList'", RecyclerView.class);
    target.noGroups = Utils.findRequiredViewAsType(source, R.id.no_groups, "field 'noGroups'", LinearLayout.class);
    target.srlLayout = Utils.findRequiredViewAsType(source, R.id.srl_layout, "field 'srlLayout'", SmartRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentGroups target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvList = null;
    target.noGroups = null;
    target.srlLayout = null;
  }
}
