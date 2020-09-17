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

package com.skydoves.disneycompose.ui.main

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import com.skydoves.disneycompose.ui.details.PosterDetails
import com.skydoves.disneycompose.ui.navigation.Actions
import com.skydoves.disneycompose.ui.navigation.BackDispatcherAmbient
import com.skydoves.disneycompose.ui.navigation.Destination
import com.skydoves.disneycompose.ui.navigation.Navigator
import com.skydoves.disneycompose.ui.posters.DisneyHomeTab
import com.skydoves.disneycompose.ui.posters.Posters
import com.skydoves.disneycompose.utils.ProvideDisplayInsets

@Composable
fun DisneyMain(viewModel: MainViewModel, backDispatcher: OnBackPressedDispatcher) {
  val navigator: Navigator<Destination> = rememberSavedInstanceState(
    saver = Navigator.saver(backDispatcher)
  ) {
    Navigator(Destination.Home, backDispatcher)
  }
  val actions = remember(navigator) { Actions(navigator) }
  val (selectedTab, setSelectedTab) = remember { mutableStateOf(DisneyHomeTab.HOME) }

  Providers(BackDispatcherAmbient provides backDispatcher) {
    ProvideDisplayInsets {
      Crossfade(navigator.current) { destination ->
        when (destination) {
          Destination.Home -> Posters(
            viewModel = viewModel,
            selectPoster = actions.selectOnPoster,
            selectedTab = selectedTab,
            setSelectedTab = setSelectedTab
          )
          is Destination.PosterDetail -> {
            viewModel.getPoster(destination.posterId)
            PosterDetails(
              viewModel = viewModel,
              pressOnBack = actions.pressOnBack
            )
          }
        }
      }
    }
  }
}
