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
package com.christianbahl.appkit.viewstate.kotlin.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.christianbahl.appkit.viewstate.kotlin.R
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateActivity

/**
 * @author Christian Bahl
 */
public abstract class CBActivityMvpToolbarViewState<CV : View, D, V : MvpLceView<D>, P : MvpPresenter<V>> : MvpLceViewStateActivity<CV, D, V, P>() {

  protected var toolbar: Toolbar? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val layoutResId = getLayoutRes()!!
    setContentView(layoutResId)
  }

  override fun onContentChanged() {
    super.onContentChanged()

    toolbar = findViewById(R.id.toolbar) as Toolbar

    setSupportActionBar(toolbar)
    getSupportActionBar()!!.setDisplayShowTitleEnabled(isDisplayShowTitleEnabled())
  }

  override fun getErrorMessage(throwable: Throwable, isContentVisible: Boolean): String? {
    return throwable.getMessage()
  }

  /**
   * Should the title be displayed in the toolbar.
   *
   * @return `true` if title should be displayed in toolbar otherwise `false`
   */
  protected fun isDisplayShowTitleEnabled(): Boolean {
    return true
  }

  /**
   * Provide the layout res id for the activity.
   *
   * @return layout res id
   */
  protected open fun getLayoutRes(): Int? {
    return R.layout.cb_activity_toolbar_fragment
  }
}