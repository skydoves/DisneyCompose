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

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.skydoves.disneycompose.model.Poster
import com.skydoves.disneycompose.ui.custom.StaggeredVerticalGrid
import com.skydoves.disneycompose.ui.theme.DisneyComposeTheme
import com.skydoves.disneycompose.ui.theme.purple500
import com.skydoves.disneycompose.utils.NetworkImage
import com.skydoves.disneycompose.utils.statusBarsPadding

@Composable
fun LibraryPosters(
  posters: List<Poster>,
  selectPoster: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  ScrollableColumn(
    modifier = modifier
      .statusBarsPadding()
      .background(MaterialTheme.colors.background)
  ) {
    StaggeredVerticalGrid(
      maxColumnWidth = 330.dp,
      modifier = Modifier.padding(4.dp)
    ) {
      posters.forEach { poster ->
        LibraryPoster(poster = poster, selectPoster = selectPoster)
      }
    }
  }
}

@Composable
fun LibraryPoster(
  poster: Poster,
  selectPoster: (Long) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .padding(4.dp),
    color = MaterialTheme.colors.onBackground,
    elevation = 8.dp,
    shape = RoundedCornerShape(8.dp)
  ) {
    ConstraintLayout(
      modifier = Modifier.clickable(
        onClick = { selectPoster(poster.id) },
        indication = RippleIndication(color = purple500)
      ).padding(16.dp)
    ) {
      val (image, title, content) = createRefs()
      NetworkImage(
        url = poster.poster,
        modifier = Modifier.constrainAs(image) {
          centerHorizontallyTo(parent)
          top.linkTo(parent.top)
          bottom.linkTo(title.top)
        }.preferredHeight(112.dp)
          .aspectRatio(1.0f)
          .fillMaxSize()
          .clip(CircleShape)
      )
      Text(
        text = poster.name,
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Center,
        modifier = Modifier.constrainAs(title) {
          centerHorizontallyTo(parent)
          top.linkTo(image.bottom)
        }.padding(8.dp)
      )
      Text(
        text = poster.playtime,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        modifier = Modifier.constrainAs(content) {
          centerHorizontallyTo(parent)
          top.linkTo(title.bottom)
        }.padding(horizontal = 8.dp)
      )
    }
  }
}

@Preview
@Composable
fun LibraryPosterPreviewLight() {
  DisneyComposeTheme(darkTheme = false) {
    LibraryPoster(
      poster = Poster.mock(),
      selectPoster = {}
    )
  }
}

@Preview
@Composable
fun LibraryPosterPreviewDark() {
  DisneyComposeTheme(darkTheme = true) {
    LibraryPoster(
      poster = Poster.mock(),
      selectPoster = {}
    )
  }
}
