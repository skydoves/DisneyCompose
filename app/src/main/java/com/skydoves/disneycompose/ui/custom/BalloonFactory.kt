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

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon
import com.skydoves.balloon.textForm
import com.skydoves.disneycompose.R

object BalloonFactory {

  fun create(context: Context, title: String, lifecycle: LifecycleOwner?): Balloon {
    val textForm = textForm(context) {
      setText(title)
      setTextSize(15f)
      setTextColorResource(R.color.white)
    }

    return createBalloon(context) {
      setArrowSize(10)
      setPadding(10)
      setCornerRadius(8f)
      setElevation(4)
      setTextForm(textForm)
      setArrowPosition(0.5f)
      setArrowOrientation(ArrowOrientation.TOP)
      setBackgroundColorResource(R.color.purple_500)
      setDismissWhenClicked(true)
      setDismissWhenShowAgain(true)
      setBalloonAnimation(BalloonAnimation.ELASTIC)
      setLifecycleOwner(lifecycle)
      build()
    }
  }
}
