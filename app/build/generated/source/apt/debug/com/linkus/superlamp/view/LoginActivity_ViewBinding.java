// Generated code from Butter Knife. Do not modify!
package com.linkus.superlamp.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.linkus.superlamp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131165368;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.rl_login, "field 'reLogin' and method 'onViewClicked'");
    target.reLogin = Utils.castView(view, R.id.rl_login, "field 'reLogin'", RelativeLayout.class);
    view2131165368 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvLoginHit = Utils.findRequiredViewAsType(source, R.id.tv_login_hit, "field 'tvLoginHit'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.reLogin = null;
    target.tvLoginHit = null;

    view2131165368.setOnClickListener(null);
    view2131165368 = null;
  }
}
