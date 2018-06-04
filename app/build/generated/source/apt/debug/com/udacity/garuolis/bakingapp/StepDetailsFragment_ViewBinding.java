// Generated code from Butter Knife. Do not modify!
package com.udacity.garuolis.bakingapp;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.exoplayer2.ui.PlayerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StepDetailsFragment_ViewBinding implements Unbinder {
  private StepDetailsFragment target;

  @UiThread
  public StepDetailsFragment_ViewBinding(StepDetailsFragment target, View source) {
    this.target = target;

    target.mPlayerView = Utils.findRequiredViewAsType(source, R.id.exo_player, "field 'mPlayerView'", PlayerView.class);
    target.mShortDescriptionView = Utils.findRequiredViewAsType(source, R.id.tv_short_description, "field 'mShortDescriptionView'", TextView.class);
    target.mDescriptionView = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'mDescriptionView'", TextView.class);
    target.mLoading = Utils.findRequiredViewAsType(source, R.id.pb_loading_video, "field 'mLoading'", ProgressBar.class);
    target.mImage = Utils.findRequiredViewAsType(source, R.id.iv_image, "field 'mImage'", ImageView.class);
    target.mItemWrap = Utils.findRequiredViewAsType(source, R.id.ll_wrap, "field 'mItemWrap'", LinearLayout.class);
    target.mBtnNext = Utils.findRequiredViewAsType(source, R.id.fab_next, "field 'mBtnNext'", FloatingActionButton.class);
    target.mBtnPrev = Utils.findRequiredViewAsType(source, R.id.fab_prev, "field 'mBtnPrev'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StepDetailsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mPlayerView = null;
    target.mShortDescriptionView = null;
    target.mDescriptionView = null;
    target.mLoading = null;
    target.mImage = null;
    target.mItemWrap = null;
    target.mBtnNext = null;
    target.mBtnPrev = null;
  }
}
