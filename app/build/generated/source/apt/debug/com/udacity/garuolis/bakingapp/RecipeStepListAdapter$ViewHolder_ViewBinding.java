// Generated code from Butter Knife. Do not modify!
package com.udacity.garuolis.bakingapp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecipeStepListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private RecipeStepListAdapter.ViewHolder target;

  @UiThread
  public RecipeStepListAdapter$ViewHolder_ViewBinding(RecipeStepListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.mView = Utils.findRequiredView(source, R.id.item, "field 'mView'");
    target.mTitleView = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'mTitleView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RecipeStepListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mView = null;
    target.mTitleView = null;
  }
}
