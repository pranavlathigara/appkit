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
package com.christianbahl.appkit.core.kotlin.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.christianbahl.appkit.core.kotlin.R
import com.christianbahl.appkit.core.kotlin.adapter.CBAdapterRecyclerView
import com.christianbahl.appkit.core.kotlin.view.CBMvpView
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment
import kotlin.properties.Delegates

/**
 * A fragment which uses the Model-View-Presenter architecture.
 *
 * The content view is a [RecyclerView] with the id `R.layout.contentView`
 *
 * You have to implement the [A] for the [RecyclerView] in [createAdapter].
 *
 * @author Christian Bahl
 * @see MvpLceFragment
 */
public abstract class CBFragmentMvpRecyclerView<M : Any, V : CBMvpView<M>, P : MvpPresenter<V>, A : CBAdapterRecyclerView<M>> : MvpLceFragment<RecyclerView, MutableList<M>, V, P>() {

  protected var adapter: A by Delegates.notNull()
  protected var emptyView: View by Delegates.notNull()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter = createAdapter()
    contentView.setAdapter(adapter)

    emptyView = view.findViewById(R.id.emptyView)!!
    contentView.setLayoutManager(createRecyclerViewLayoutManager())

    onMvpViewCreated(view, savedInstanceState)

    loadData(false)
  }

  override fun showContent() {
    super.showContent()

    if (adapter.getItemCount() == 0) {
      emptyView.setVisibility(View.VISIBLE)
    } else {
      emptyView.setVisibility(View.GONE)
    }
  }

  override fun showLoading(isContentVisible: Boolean) {
    super.showLoading(isContentVisible)

    if (isContentVisible && emptyView.getVisibility() == View.VISIBLE) {
      emptyView.setVisibility(View.GONE)
    }
  }

  override fun showError(e: Throwable, isContentVisible: Boolean) {
    super.showError(e, isContentVisible)

    if (isContentVisible && emptyView.getVisibility() == View.VISIBLE) {
      emptyView.setVisibility(View.GONE)
    }
  }

  override fun getLayoutRes(): Int {
    return R.layout.cb_fragment_recycler_view
  }

  override fun getErrorMessage(throwable: Throwable, isContentVisible: Boolean): String? {
    return throwable.getMessage()
  }

  /**
   * Creates the [RecyclerView.LayoutManager].
   * Default: [LinearLayoutManager]
   *
   * @return [RecyclerView.LayoutManager]
   */
  protected fun createRecyclerViewLayoutManager(): RecyclerView.LayoutManager {
    return LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
  }

  /**
   * Called after the mvp views and the recycler view are created
   *
   * @param view [View]
   * @param savedInstanceState [Bundle]
   */
  protected open fun onMvpViewCreated(view: View, savedInstanceState: Bundle?) {

  }

  /**
   * Creates the [A].
   * Called in [onViewCreated]
   *
   * @return [A]
   */
  protected abstract fun createAdapter(): A

}