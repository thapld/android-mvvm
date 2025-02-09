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

@file:JvmName("ComicsInfoFragmentUtils")

package com.arthurivanets.sample.ui.comics.info

import android.os.Bundle
import androidx.core.os.bundleOf
import com.arthurivanets.sample.domain.entities.Comics


internal val extrasExtractor : (Bundle.() -> Extras) = {
    Extras(
        comics = (this.getParcelable(ExtrasKeys.COMICS) ?: Comics())
    )
}


internal val stateExtractor : (Bundle.() -> State) = {
    State(
        comics = (this.getParcelable(StateKeys.COMICS) ?: Comics())
    )
}


internal object ExtrasKeys {
    
    const val COMICS = "comics"
    
}


internal object StateKeys {
    
    const val COMICS = "comics"
    
}


internal data class Extras(
    val comics : Comics = Comics()
)


internal data class State(
    val comics : Comics
)


internal fun Bundle.saveState(state : State) {
    this.putParcelable(StateKeys.COMICS, state.comics)
}


fun ComicsInfoFragment.Companion.newBundle(comics : Comics) : Bundle {
    return bundleOf(
        ExtrasKeys.COMICS to comics
    )
}