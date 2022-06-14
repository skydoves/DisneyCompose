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

package com.skydoves.disneycompose.di

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.skydoves.disneycompose.network.DisneyService
import com.skydoves.disneycompose.network.RequestInterceptor
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideImageLoader(
    @ApplicationContext context: Context,
    okHttpClient: OkHttpClient
  ): ImageLoader {
    return ImageLoader.Builder(context)
      .okHttpClient { okHttpClient }
      .components {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          add(ImageDecoderDecoder.Factory())
        } else {
          add(GifDecoder.Factory())
        }
      }.build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl(
        "https://gist.githubusercontent.com/skydoves/176c209dbce4a53c0ff9589e07255f30/raw/6489d9712702e093c4df71500fb822f0d408ef75/"
      )
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideDisneyService(retrofit: Retrofit): DisneyService {
    return retrofit.create(DisneyService::class.java)
  }
}
