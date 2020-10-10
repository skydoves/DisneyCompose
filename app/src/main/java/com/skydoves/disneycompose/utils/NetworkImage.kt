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

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.fresco.FrescoImage

/**
 * A wrapper around [FrescoImage] setting a default [contentScale]
 * and loading indicator for loading disney poster images.
 *
 * https://github.com/skydoves/landscapist
 */
@Composable
fun NetworkImage(
  url: String,
  modifier: Modifier,
  contentScale: ContentScale = ContentScale.Crop
) {
  FrescoImage(
    imageUrl = url,
    modifier = modifier,
    contentScale = contentScale,
    circularRevealedEnabled = true,
    loading = {
      ConstraintLayout(
        modifier = Modifier.fillMaxSize()
      ) {
        val indicator = createRef()
        CircularProgressIndicator(
          modifier = Modifier.constrainAs(indicator) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
          }
        )
      }
    },
    failure = {
      ConstraintLayout(
        modifier = Modifier.fillMaxSize()
      ) {
        val message = createRef()
        Text(
          text = "image request failed.",
          style = MaterialTheme.typography.body2,
          modifier = Modifier.constrainAs(message) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
          }
        )
      }
    }
  )
}
