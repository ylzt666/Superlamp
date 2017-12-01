// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.linkus.superlamp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GroupListActivity_ViewBinding implements Unbinder {
  private GroupListActivity target;

  private View view2131165220;

  @UiThread
  public GroupListActivity_ViewBinding(GroupListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroupListActivity_ViewBinding(final GroupListActivity target, View source) {
    this.target = target;

    View view;
    target.pageName = Utils.findRequiredViewAsType(source, R.id.tv_page_name, "field 'pageName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.back_left, "field 'backLeft' and method 'onViewClicked'");
    target.backLeft = Utils.castView(view, R.id.back_left, "field 'backLeft'", ImageView.class);
    view2131165220 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rvList = Utils.findRequiredViewAsType(source, R.id.rv_list, "field 'rvList'", RecyclerView.class);
    target.topRight = Utils.findRequiredViewAsType(source, R.id.iv_top_right, "field 'topRight'", ImageView.class);
    target.btnApply = Utils.findRequiredViewAsType(source, R.id.btn_apply, "field 'btnApply'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroupListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pageName = null;
    target.backLeft = null;
    target.rvList = null;
    target.topRight = null;
    target.btnApply = null;

    view2131165220.setOnClickListener(null);
    view2131165220 = null;
  }
}
