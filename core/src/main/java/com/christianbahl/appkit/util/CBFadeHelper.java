/*
 * Copyright 2015 Christian Bahl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.christianbahl.appkit.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

/**
 * A helper calss for fading views in the Model-View-Presenter architecture
 *
 * @author Christian Bahl
 */
public class CBFadeHelper {

  public static final int DURATION = 200;

  /**
   * Show loading view
   *
   * @param loadingView loading view
   * @param contentView content view
   * @param errorView error view
   */
  public static void showLoading(View loadingView, View contentView, View errorView) {
    contentView.setVisibility(View.GONE);
    errorView.setVisibility(View.GONE);
    loadingView.setVisibility(View.VISIBLE);
  }

  /**
   * Show content view
   *
   * @param loadingView loading view
   * @param contentView content view
   * @param errorView error view
   */
  public static void showContent(final View loadingView, final View contentView,
      final View errorView) {
    if (contentView.getVisibility() == View.VISIBLE) {
      // No Changing needed, because contentView is already visible
      errorView.setVisibility(View.GONE);
      loadingView.setVisibility(View.GONE);
    } else {

      if (Build.VERSION.SDK_INT < 11) {
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
      } else {
        errorView.setVisibility(View.GONE);

        // Not visible yet, so animate the view in
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator contentIn = ObjectAnimator.ofFloat(contentView, "alpha", 1.0f);
        ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView, "alpha", 0.0f);

        set.playTogether(contentIn, loadingOut);
        set.setDuration(DURATION);

        set.addListener(new AnimatorListenerAdapter() {
          @Override public void onAnimationStart(Animator animation) {
            super.onAnimationStart(animation);

            contentView.setVisibility(View.VISIBLE);
          }

          @Override public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);

            loadingView.setVisibility(View.GONE);
            loadingView.setAlpha(1.0f); // For future showLoading calls
          }
        });

        set.start();
      }
    }
  }

  /**
   * Show error view
   *
   * @param errorMessage error message
   * @param loadingView loading view
   * @param contentView content view
   * @param errorView error view
   */
  public static void showError(String errorMessage, final View loadingView, final View contentView,
      final TextView errorView) {
    errorView.setText(errorMessage);

    if (Build.VERSION.SDK_INT < 11) {
      contentView.setVisibility(View.GONE);
      loadingView.setVisibility(View.GONE);
      errorView.setVisibility(View.VISIBLE);
    } else {
      contentView.setVisibility(View.GONE);

      // Not visible yet, so animate the view in
      AnimatorSet set = new AnimatorSet();
      ObjectAnimator in = ObjectAnimator.ofFloat(errorView, "alpha", 1.0f);
      ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView, "alpha", 0.0f);

      set.playTogether(in, loadingOut);
      set.setDuration(DURATION);

      set.addListener(new AnimatorListenerAdapter() {
        @Override public void onAnimationStart(Animator animation) {
          super.onAnimationStart(animation);

          errorView.setVisibility(View.VISIBLE);
        }

        @Override public void onAnimationEnd(Animator animation) {
          super.onAnimationEnd(animation);

          loadingView.setVisibility(View.GONE);
          loadingView.setAlpha(1.0f);
        }
      });

      set.start();
    }
  }
}