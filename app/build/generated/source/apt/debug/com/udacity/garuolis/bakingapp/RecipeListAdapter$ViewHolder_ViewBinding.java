// Generated code from Butter Knife. Do not modify!
package com.udacity.garuolis.bakingapp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RecipeListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private RecipeListAdapter.ViewHolder target;

  @UiThread
  public RecipeListAdapter$ViewHolder_ViewBinding(RecipeListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.mTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'mTitle'", TextView.class);
    target.mServings = Utils.findRequiredViewAsType(source, R.id.tv_servings, "field 'mServings'", TextView.class);
    target.mImage = Utils.findRequiredViewAsType(source, R.id.iv_image, "field 'mImage'", ImageView.class);
    target.mView = Utils.findRequiredView(source, R.id.item, "field 'mView'");
  }

  @Override
  @CallSuper
  public void unbind() {
    RecipeListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTitle = null;
    target.mServings = null;
    target.mImage = null;
    target.mView = null;
  }
}
