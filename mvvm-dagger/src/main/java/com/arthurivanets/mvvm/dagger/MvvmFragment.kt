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

package com.arthurivanets.mvvm.dagger

import androidx.databinding.ViewDataBinding
import com.arthurivanets.dagger.androidx.AndroidXSupportInjection
import com.arthurivanets.mvvm.BaseViewModel
import com.arthurivanets.mvvm.MvvmFragment

/**
 * A base MVVM Fragment with built-in support for the Dagger2-based dependency injection.
 */
abstract class MvvmFragment<VDB : ViewDataBinding, VM : BaseViewModel> : MvvmFragment<VDB, VM>() {


    final override fun injectDependencies() {
        AndroidXSupportInjection.inject(this)
    }


}