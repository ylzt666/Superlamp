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

public class FragmentScenes_ViewBinding implements Unbinder {
  private FragmentScenes target;

  @UiThread
  public FragmentScenes_ViewBinding(FragmentScenes target, View source) {
    this.target = target;

    target.rvList = Utils.findRequiredViewAsType(source, R.id.rv_list, "field 'rvList'", RecyclerView.class);
    target.no_sences = Utils.findRequiredViewAsType(source, R.id.no_sences, "field 'no_sences'", LinearLayout.class);
    target.srlLayout = Utils.findRequiredViewAsType(source, R.id.srl_layout, "field 'srlLayout'", SmartRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentScenes target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvList = null;
    target.no_sences = null;
    target.srlLayout = null;
  }
}
