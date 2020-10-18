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

package com.skydoves.disneycompose.ui.posters

import androidx.annotation.StringRes
import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.disneycompose.R
import com.skydoves.disneycompose.extensions.visible
import com.skydoves.disneycompose.model.Poster
import com.skydoves.disneycompose.ui.main.MainViewModel
import com.skydoves.disneycompose.ui.theme.purple200
import com.skydoves.disneycompose.utils.navigationBarsHeightPlus
import com.skydoves.disneycompose.utils.navigationBarsPadding

@Composable
fun Posters(
  viewModel: MainViewModel,
  selectPoster: (Long) -> Unit,
  selectedTab: DisneyHomeTab,
  setSelectedTab: (DisneyHomeTab) -> Unit
) {
  val posters: List<Poster> by viewModel.posterList.observeAsState(listOf())
  val isLoading: Boolean by viewModel.isLoading.observeAsState(false)
  val tabs = DisneyHomeTab.values()
  ConstraintLayout {
    val (body, progress) = createRefs()
    Scaffold(
      backgroundColor = MaterialTheme.colors.primarySurface,
      topBar = { PosterAppBar() },
      modifier = Modifier.constrainAs(body) {
        top.linkTo(parent.top)
      },
      bottomBar = {
        BottomNavigation(
          backgroundColor = purple200,
          modifier = Modifier
            .navigationBarsHeightPlus(56.dp)
        ) {
          tabs.forEach { tab ->
            BottomNavigationItem(
              icon = { Icon(asset = tab.icon) },
              label = { Text(text = stringResource(tab.title), color = Color.White) },
              selected = tab == selectedTab,
              onClick = { setSelectedTab(tab) },
              alwaysShowLabels = false,
              selectedContentColor = AmbientContentColor.current,
              unselectedContentColor = AmbientContentColor.current,
              modifier = Modifier.navigationBarsPadding()
            )
          }
        }
      }
    ) { innerPadding ->
      val modifier = Modifier.padding(innerPadding)
      when (selectedTab) {
        DisneyHomeTab.HOME -> HomePosters(posters, selectPoster, modifier)
        DisneyHomeTab.RADIO -> RadioPosters(posters, selectPoster, modifier)
        DisneyHomeTab.LIBRARY -> LibraryPosters(posters, selectPoster, modifier)
      }
    }
    CircularProgressIndicator(
      modifier = Modifier.constrainAs(progress) {
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
      }.visible(isLoading)
    )
  }
}

@Composable
fun PosterAppBar() {
  TopAppBar(
    elevation = 6.dp,
    backgroundColor = purple200,
    modifier = Modifier.preferredHeight(58.dp)
  ) {
    Text(
      modifier = Modifier
        .padding(8.dp)
        .align(Alignment.CenterVertically),
      text = stringResource(R.string.app_name),
      color = Color.White,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold
    )
  }
}

enum class DisneyHomeTab(
  @StringRes val title: Int,
  val icon: VectorAsset
) {
  HOME(R.string.menu_home, Icons.Filled.Home),
  RADIO(R.string.menu_radio, Icons.Filled.Radio),
  LIBRARY(R.string.menu_library, Icons.Filled.LibraryAdd),
}
