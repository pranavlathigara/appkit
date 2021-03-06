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
package com.christianbahl.appkit.core.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.R;

/**
 * <p>
 * An activity which adds a {@link Toolbar} on top and has a container for the {@link Fragment}.
 * </p>
 *
 * <p>
 * The layout must have a {@link ViewGroup} for the {@link Fragment} with the id
 * <code>R.layout.contentView</code>.
 * </p>
 *
 * <p>
 * The standard layout implements all necessary views. You can override the default layout in
 * {@link #getLayoutRes}. But be careful, you have to provide the necessary views!
 * </p>
 *
 * <p>
 * You have to override the {@link #createFragmentToDisplay} to create the {@link Fragment} which
 * should be displayed.
 * </p>
 *
 * @author Christian Bahl
 * @see CBActivityToolbar
 */
public abstract class CBActivityToolbarFragment extends CBActivityToolbar {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.contentView, createFragmentToDisplay())
          .commit();
    }
  }

  protected Integer getLayoutRes() {
    return R.layout.cb_activity_toolbar_fragment;
  }

  /**
   * Returns the {@link Fragment} which should be displayed by this activity.
   *
   * @return {@link Fragment}
   */
  protected abstract Fragment createFragmentToDisplay();

}
