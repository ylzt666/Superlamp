// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.linkus.superlamp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashActivity_ViewBinding implements Unbinder {
  private SplashActivity target;

  private View view2131165426;

  private View view2131165423;

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashActivity_ViewBinding(final SplashActivity target, View source) {
    this.target = target;

    View view;
    target.cbWelcome = Utils.findRequiredViewAsType(source, R.id.cb_welcome, "field 'cbWelcome'", ConvenientBanner.class);
    view = Utils.findRequiredView(source, R.id.tv_go, "field 'tvGo' and method 'onViewClicked'");
    target.tvGo = Utils.castView(view, R.id.tv_go, "field 'tvGo'", TextView.class);
    view2131165426 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_count, "field 'tvCount' and method 'onViewClicked'");
    target.tvCount = Utils.castView(view, R.id.tv_count, "field 'tvCount'", TextView.class);
    view2131165423 = view;
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
    SplashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.cbWelcome = null;
    target.tvGo = null;
    target.tvCount = null;

    view2131165426.setOnClickListener(null);
    view2131165426 = null;
    view2131165423.setOnClickListener(null);
    view2131165423 = null;
  }
}
