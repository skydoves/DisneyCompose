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

package com.skydoves.disneycompose.ui.details

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.palette.graphics.Palette
import com.skydoves.disneycompose.extensions.paletteColorList
import com.skydoves.disneycompose.model.Poster
import com.skydoves.disneycompose.utils.NetworkImage
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.palette.rememberPaletteState

@Composable
fun PosterDetails(
  posterId: Long,
  viewModel: DetailViewModel,
  pressOnBack: () -> Unit = {}
) {
  LaunchedEffect(key1 = posterId) {
    viewModel.loadPosterById(posterId)
  }

  val details: Poster? by viewModel.posterDetailsFlow.collectAsState(initial = null)
  details?.let { poster ->
    PosterDetailsBody(poster, pressOnBack)
  }
}

@Composable
private fun PosterDetailsBody(
  poster: Poster,
  pressOnBack: () -> Unit
) {
  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .background(MaterialTheme.colors.background)
      .fillMaxHeight()
  ) {
    var palette by rememberPaletteState(value = null)

    ConstraintLayout {
      val (arrow, image, paletteRow, title, content, gifTitle, gif) = createRefs()

      NetworkImage(
        url = poster.poster,
        modifier = Modifier
          .constrainAs(image) {
            top.linkTo(parent.top)
          }
          .fillMaxWidth()
          .aspectRatio(0.85f),
        circularRevealEnabled = true,
        paletteLoadedListener = { palette = it }
      )

      ColorPalettes(
        palette = palette,
        modifier = Modifier
          .constrainAs(paletteRow) {
            top.linkTo(image.bottom)
          }
      )

      Text(
        text = poster.name,
        style = MaterialTheme.typography.h1,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier
          .constrainAs(title) {
            top.linkTo(paletteRow.bottom)
          }
          .padding(start = 16.dp, top = 12.dp)
      )

      Text(
        text = poster.description,
        style = MaterialTheme.typography.body2,
        modifier = Modifier
          .constrainAs(content) {
            top.linkTo(title.bottom)
          }
          .padding(16.dp)
      )

      Text(
        text = "Gif",
        style = MaterialTheme.typography.h2,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .padding(8.dp)
          .constrainAs(gifTitle) {
            top.linkTo(content.bottom)
          }
      )

      CoilImage(
        imageModel = { poster.gif },
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp)
          .height(500.dp)
          .clip(RoundedCornerShape(8.dp))
          .constrainAs(gif) {
            top.linkTo(gifTitle.bottom)
          },
      )

      Icon(
        imageVector = Icons.Filled.ArrowBack,
        tint = Color.White,
        contentDescription = null,
        modifier = Modifier
          .constrainAs(arrow) {
            top.linkTo(parent.top)
          }
          .padding(12.dp)
          .statusBarsPadding()
          .clickable(onClick = { pressOnBack() })
      )
    }
  }
}

@Composable
private fun ColorPalettes(
  palette: Palette?,
  modifier: Modifier
) {
  val colorList: List<Int> = palette.paletteColorList()
  LazyRow(
    modifier = modifier
      .padding(horizontal = 8.dp, vertical = 16.dp)
  ) {
    items(colorList) { color ->
      Crossfade(
        targetState = color,
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .size(45.dp)
      ) {
        Box(
          modifier = Modifier
            .background(color = Color(it))
            .fillMaxSize()
        )
      }
    }
  }
}
