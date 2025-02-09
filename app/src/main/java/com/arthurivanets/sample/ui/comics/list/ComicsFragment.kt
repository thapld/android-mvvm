/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.l@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthurivanets.sample.ui.comics.list

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.mvvm.events.ViewModelEvent
import com.arthurivanets.sample.BR
import com.arthurivanets.sample.R
import com.arthurivanets.sample.adapters.comics.ComicsItem
import com.arthurivanets.sample.adapters.comics.ComicsItemResources
import com.arthurivanets.sample.adapters.comics.ComicsItemViewHolder
import com.arthurivanets.sample.adapters.comics.ComicsItemsRecyclerViewAdapter
import com.arthurivanets.sample.databinding.FragmentComicsBinding
import com.arthurivanets.sample.domain.entities.Comics
import com.arthurivanets.sample.ui.base.BaseFragment
import com.arthurivanets.sample.ui.comics.COMICS_COLUMN_COUNT
import com.arthurivanets.sample.ui.comics.info.ComicsInfoFragment
import com.arthurivanets.sample.ui.comics.info.newBundle
import com.arthurivanets.sample.ui.util.extensions.sharedImageTransitionName
import com.arthurivanets.sample.ui.util.extensions.sharedTitleTransitionName
import com.arthurivanets.sample.ui.util.markers.CanScrollToTop
import kotlinx.android.synthetic.main.fragment_comics.*
import kotlinx.android.synthetic.main.view_progress_bar_circular.*
import javax.inject.Inject

class ComicsFragment : BaseFragment<FragmentComicsBinding, ComicsViewModel>(), CanScrollToTop {


    @Inject
    lateinit var localViewModel : ComicsViewModel

    @Inject
    lateinit var itemResources : ComicsItemResources
    
    private lateinit var adapter : ComicsItemsRecyclerViewAdapter


    override fun init(savedInstanceState : Bundle?) {
        initRecyclerView()
    }


    private fun initRecyclerView() {
        with(recyclerView) {
            layoutManager = initLayoutManager()
            adapter = initAdapter()
        }
    }


    private fun initLayoutManager() : RecyclerView.LayoutManager {
        return GridLayoutManager(context, COMICS_COLUMN_COUNT)
    }


    private fun initAdapter() : ComicsItemsRecyclerViewAdapter {
        adapter = ComicsItemsRecyclerViewAdapter(
            context = context!!,
            items = localViewModel.items,
            resources = itemResources
        )
        adapter.onItemClickListener = OnItemClickListener { _, item, _ -> localViewModel.onComicsClicked(item) }

        return adapter
    }
    
    
    override fun postInit() {
        super.postInit()
        
        onLoadingStateChanged(localViewModel.isLoading)
    }
    
    
    override fun scrollToTop(animate : Boolean) {
        if(animate) {
            recyclerView?.smoothScrollToPosition(0)
        } else {
            recyclerView?.scrollToPosition(0)
        }
    }
    
    
    override fun onRegisterObservables() {
        localViewModel.loadingStateHolder.register(::onLoadingStateChanged)
    }
    
    
    private fun onLoadingStateChanged(isLoading : Boolean) {
        progress_bar.isVisible = isLoading
    }


    override fun onViewModelEvent(event : ViewModelEvent<*>) {
        super.onViewModelEvent(event)

        when(event) {
            is ComicsViewModelEvents.OpenComicsInfoScreen -> event.data?.let(::onOpenComicsInfoScreen)
        }
    }


    private fun onOpenComicsInfoScreen(comics : Comics) {
        val viewHolder = (getItemViewHolder(comics) ?: return)
        
        navigate(
            R.id.comicsInfoFragmentAction,
            ComicsInfoFragment.newBundle(comics),
            FragmentNavigatorExtras(
                viewHolder.imageIv to comics.sharedImageTransitionName,
                viewHolder.titleTv to comics.sharedTitleTransitionName
            )
        )
    }
    
    
    private fun getItemViewHolder(comics : Comics) : ComicsItemViewHolder? {
        val index = adapter.indexOf(ComicsItem(comics))
    
        return if(index != -1) {
            (recyclerView.findViewHolderForAdapterPosition(index) as ComicsItemViewHolder)
        } else {
            null
        }
    }


    override fun getLayoutId() : Int {
        return R.layout.fragment_comics
    }


    override fun getBindingVariable() : Int {
        return BR.viewModel
    }


    override fun getViewModel() : ComicsViewModel {
        return localViewModel
    }


}