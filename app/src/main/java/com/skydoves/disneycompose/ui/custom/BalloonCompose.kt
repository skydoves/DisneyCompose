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

package com.skydoves.disneycompose.ui.custom

import android.view.View
import androidx.compose.foundation.layout.ConstrainedLayoutReference
import androidx.compose.foundation.layout.ConstraintLayoutScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ConstraintLayoutScope.BalloonTarget(
  anchor: View,
  modifier: Modifier,
  reference: ConstrainedLayoutReference
) {
  AndroidView(
    viewBlock = { anchor },
    modifier = Modifier.constrainAs(ConstrainedLayoutReference(anchor.id)) {
      start.linkTo(reference.start)
      end.linkTo(reference.end)
      top.linkTo(reference.top)
      bottom.linkTo(reference.bottom)
    }.then(modifier)
  )
}
