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
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.palette.graphics.Palette
import com.skydoves.disneycompose.model.Poster
import com.skydoves.disneycompose.ui.custom.ImageBalloonAnchor
import com.skydoves.disneycompose.utils.NetworkImage
import com.skydoves.landscapist.palette.BitmapPalette

@Composable
fun PosterDetails(
  viewModel: DetailViewModel,
  pressOnBack: () -> Unit
) {
  val details: Poster? by viewModel.posterDetails.observeAsState()
  var palette by remember { mutableStateOf<Palette?>(null) }
  details?.let { poster ->
    Column(
      modifier = Modifier
        .verticalScroll(rememberScrollState())
        .background(MaterialTheme.colors.background)
        .fillMaxHeight()
    ) {
      ConstraintLayout {
        val (arrow, image, paletteRow, title, content) = createRefs()
        NetworkImage(
          url = poster.poster,
          modifier = Modifier
            .constrainAs(image) {
              top.linkTo(parent.top)
            }
            .fillMaxWidth()
            .aspectRatio(0.85f),
          circularRevealedEnabled = true,
          bitmapPalette = BitmapPalette {
            palette = it
          }
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
        ImageBalloonAnchor(
          reference = image,
          modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f),
          content = poster.name,
          onClick = { balloon, anchor -> balloon.showAlignBottom(anchor) }
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
            .clickable(onClick = { pressOnBack() })
        )
      }
    }
  }
}

@Composable
fun ColorPalettes(
  palette: Palette?,
  modifier: Modifier
) {
  Row(
    modifier = modifier
      .padding(horizontal = 8.dp, vertical = 16.dp)
      .horizontalScroll(rememberScrollState())
  ) {
    Crossfade(
      targetState = palette,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .size(45.dp)
    ) {
      Box(
        modifier = Modifier
          .background(color = Color(it?.lightVibrantSwatch?.rgb ?: 0))
          .fillMaxSize()
      )
    }
    Crossfade(
      targetState = palette,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .size(45.dp)
    ) {
      Box(
        modifier = Modifier
          .background(color = Color(it?.lightMutedSwatch?.rgb ?: 0))
          .fillMaxSize()
      )
    }
    Crossfade(
      targetState = palette,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .size(45.dp)
    ) {
      Box(
        modifier = Modifier
          .background(color = Color(it?.vibrantSwatch?.rgb ?: 0))
          .fillMaxSize()
      )
    }
    Crossfade(
      targetState = palette,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .size(45.dp)
    ) {
      Box(
        modifier = Modifier
          .background(color = Color(it?.mutedSwatch?.rgb ?: 0))
          .fillMaxSize()
      )
    }
    Crossfade(
      targetState = palette,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .size(45.dp)
    ) {
      Box(
        modifier = Modifier
          .background(color = Color(it?.darkVibrantSwatch?.rgb ?: 0))
          .fillMaxSize()
      )
    }
    Crossfade(
      targetState = palette,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .size(45.dp)
    ) {
      Box(
        modifier = Modifier
          .background(color = Color(it?.darkMutedSwatch?.rgb ?: 0))
          .fillMaxSize()
      )
    }
    Crossfade(
      targetState = palette,
      modifier = Modifier
        .padding(horizontal = 8.dp)
        .size(45.dp)
    ) {
      Box(
        modifier = Modifier
          .background(color = Color(it?.dominantSwatch?.rgb ?: 0))
          .fillMaxSize()
      )
    }
  }
}
