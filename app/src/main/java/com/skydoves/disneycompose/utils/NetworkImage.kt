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
import com.skydoves.disneycompose.ui.theme.shimmerHighLight
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.fresco.FrescoImage

/**
 * A wrapper around [FrescoImage] setting a default [contentScale]
 * and loading indicator for loading disney poster images.
 *
 * @see FrescoImage https://github.com/skydoves/landscapist
 */
@Composable
fun NetworkImage(
  url: String,
  modifier: Modifier,
  circularRevealedEnabled: Boolean = false,
  contentScale: ContentScale = ContentScale.Crop
) {
  FrescoImage(
    imageUrl = url,
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
