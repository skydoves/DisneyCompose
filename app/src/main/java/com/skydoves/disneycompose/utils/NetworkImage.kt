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

package com.skydoves.disneycompose.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.skydoves.disneycompose.ui.theme.shimmerHighLight
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

/**
 * A wrapper around [CoilImage] setting a default [contentScale] and showing
 * an indicator when loading disney poster images.
 *
 * @see CoilImage https://github.com/skydoves/landscapist#coil
 */
@Preview
@Composable
fun NetworkImage(
  @PreviewParameter(NetworkUrlPreviewProvider::class) url: String,
  modifier: Modifier = Modifier,
  circularRevealedEnabled: Boolean = false,
  contentScale: ContentScale = ContentScale.Crop
) {
  CoilImage(
    imageModel = url,
    modifier = modifier,
    contentScale = contentScale,
    circularRevealedEnabled = circularRevealedEnabled,
    circularRevealedDuration = 450,
    shimmerParams = ShimmerParams(
      baseColor = MaterialTheme.colors.background,
      highlightColor = shimmerHighLight,
      dropOff = 0.65f
    ),
    failure = {
      Text(
        text = "image request failed.",
        style = MaterialTheme.typography.body2
      )
    }
  )
}
