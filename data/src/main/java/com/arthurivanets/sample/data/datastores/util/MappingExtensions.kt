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

@file:JvmName("MappingExtensions")

package com.arthurivanets.sample.data.datastores.util

import com.arthurivanets.commons.data.exceptions.NoResultError
import com.arthurivanets.commons.data.util.Response
import com.arthurivanets.sample.data.api.responses.base.BaseDataResponse


internal fun <T, R> BaseDataResponse<T>.toSingleItemResponse(resultMapper : (T) -> R) : Response<R, Throwable> {
    val hasDataItems = (this.hasData && (this.data?.results?.isNotEmpty() ?: false))

    return when {
        this.isErroneous -> Response.error(this.message ?: "")
        hasDataItems -> Response.result(resultMapper(this.data!!.results[0]))
        else -> Response.error(NoResultError(""))
    }
}


internal fun <T, R> BaseDataResponse<T>.toResponse(resultMapper : (List<T>) -> R) : Response<R, Throwable> {
    return if(this.isErroneous) {
        Response.error(this.message ?: "")
    } else {
        Response.result(resultMapper(this.data?.results ?: emptyList()))
    }
}