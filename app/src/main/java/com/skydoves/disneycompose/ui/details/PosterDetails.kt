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

import android.view.View
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.LifecycleOwnerAmbient
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.disneycompose.model.Poster
import com.skydoves.disneycompose.ui.custom.BalloonFactory
import com.skydoves.disneycompose.ui.custom.BalloonTarget
import com.skydoves.disneycompose.ui.main.MainViewModel
import com.skydoves.disneycompose.ui.theme.purple500
import com.skydoves.disneycompose.utils.NetworkImage

@Composable
fun PosterDetails(
  viewModel: MainViewModel,
  pressOnBack: () -> Unit
) {
  val details: Poster? by viewModel.posterDetails.observeAsState()
  details?.let { poster ->
    val context = ContextAmbient.current
    val lifecycleOwner = LifecycleOwnerAmbient.current
    val view = remember { View(context) }
    val balloon = remember { BalloonFactory.create(context, poster.name, lifecycleOwner) }
    ScrollableColumn(
      modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxHeight()
    ) {
      ConstraintLayout {
        val (arrow, image, title, content) = createRefs()
        NetworkImage(
          url = poster.poster,
          modifier = Modifier.constrainAs(image) {
            top.linkTo(parent.top)
          }.fillMaxWidth().aspectRatio(0.85f)
        )
        Text(
          text = poster.name,
          style = MaterialTheme.typography.h1,
          overflow = TextOverflow.Ellipsis,
          maxLines = 1,
          modifier = Modifier.constrainAs(title) {
            top.linkTo(image.bottom)
          }.padding(start = 16.dp, top = 16.dp)
        )
        Text(
          text = poster.description,
          style = MaterialTheme.typography.body2,
          modifier = Modifier.constrainAs(content) {
            top.linkTo(title.bottom)
          }.padding(16.dp)
        )
        Icon(
          asset = Icons.Filled.ArrowBack,
          tint = Color.White,
          modifier = Modifier.constrainAs(arrow) {
            top.linkTo(parent.top)
          }.padding(12.dp)
            .clickable(onClick = { pressOnBack() })
        )
        BalloonTarget(
          anchor = view,
          modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f)
            .clickable(
              onClick = { balloon.showAlignBottom(view) },
              indication = RippleIndication(color = purple500)
            ),
          reference = image
        )
      }
    }
  }
}
