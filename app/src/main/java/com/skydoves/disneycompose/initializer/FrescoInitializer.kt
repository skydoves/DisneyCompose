/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.skydoves.disneycompose.initializer

import android.content.Context
import androidx.startup.Initializer
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import okhttp3.OkHttpClient
import timber.log.Timber

class FrescoInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    Timber.d("FrescoInitializer is initialized.")

    val pipelineConfig =
      OkHttpImagePipelineConfigFactory
        .newBuilder(context, OkHttpClient.Builder().build())
        .setDiskCacheEnabled(true)
        .setDownsampleEnabled(true)
        .setResizeAndRotateEnabledForNetwork(true)
        .build()

    Fresco.initialize(context, pipelineConfig)
  }

  override fun dependencies(): List<Class<out Initializer<*>>> =
    listOf(TimberInitializer::class.java)
}
