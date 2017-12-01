// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.linkus.superlamp.R;
import com.linkus.superlamp.widget.HorizontalScrollDisabledViewPager;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingMenuLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131165392;

  private View view2131165298;

  private View view2131165318;

  private View view2131165314;

  private View view2131165323;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.show_drawer, "field 'showDrawer' and method 'onViewClicked'");
    target.showDrawer = Utils.castView(view, R.id.show_drawer, "field 'showDrawer'", ImageView.class);
    view2131165392 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.pageName = Utils.findRequiredViewAsType(source, R.id.tv_page_name, "field 'pageName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_top_right, "field 'topRight' and method 'onViewClicked'");
    target.topRight = Utils.castView(view, R.id.iv_top_right, "field 'topRight'", ImageView.class);
    view2131165298 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.drawer = Utils.findRequiredViewAsType(source, R.id.drawerlayout, "field 'drawer'", FlowingDrawer.class);
    target.content = Utils.findRequiredViewAsType(source, R.id.content, "field 'content'", RelativeLayout.class);
    target.menulayout = Utils.findRequiredViewAsType(source, R.id.menulayout, "field 'menulayout'", FlowingMenuLayout.class);
    target.cbContent = Utils.findRequiredViewAsType(source, R.id.cb_content, "field 'cbContent'", HorizontalScrollDisabledViewPager.class);
    target.llBottom = Utils.findRequiredViewAsType(source, R.id.ll_bottom, "field 'llBottom'", LinearLayout.class);
    target.ivAvator = Utils.findRequiredViewAsType(source, R.id.avator, "field 'ivAvator'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.ll_config, "field 'llConfig' and method 'onViewClicked'");
    target.llConfig = Utils.castView(view, R.id.ll_config, "field 'llConfig'", LinearLayout.class);
    view2131165318 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_about, "field 'llAbout' and method 'onViewClicked'");
    target.llAbout = Utils.castView(view, R.id.ll_about, "field 'llAbout'", LinearLayout.class);
    view2131165314 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_help, "field 'llHelp' and method 'onViewClicked'");
    target.llHelp = Utils.castView(view, R.id.ll_help, "field 'llHelp'", LinearLayout.class);
    view2131165323 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.topBarContent = Utils.findRequiredViewAsType(source, R.id.top_bar, "field 'topBarContent'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.showDrawer = null;
    target.pageName = null;
    target.topRight = null;
    target.drawer = null;
    target.content = null;
    target.menulayout = null;
    target.cbContent = null;
    target.llBottom = null;
    target.ivAvator = null;
    target.llConfig = null;
    target.llAbout = null;
    target.llHelp = null;
    target.topBarContent = null;

    view2131165392.setOnClickListener(null);
    view2131165392 = null;
    view2131165298.setOnClickListener(null);
    view2131165298 = null;
    view2131165318.setOnClickListener(null);
    view2131165318 = null;
    view2131165314.setOnClickListener(null);
    view2131165314 = null;
    view2131165323.setOnClickListener(null);
    view2131165323 = null;
  }
}
