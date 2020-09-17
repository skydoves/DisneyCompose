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

package com.skydoves.disneycompose.ui.navigation

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.android.parcel.Parcelize

/**
 * Navigation destination models the screens in the app and arguments they require.
 */
sealed class Destination : Parcelable {

  @Parcelize
  object Home : Destination()

  @Immutable
  @Parcelize
  data class PosterDetail(val posterId: Long) : Destination()
}

class Actions(navigator: Navigator<Destination>) {

  val selectOnPoster: (Long) -> Unit = { posterId: Long ->
    navigator.navigate(Destination.PosterDetail(posterId))
  }

  val pressOnBack: () -> Unit = {
    navigator.back()
  }
}
