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

import android.view.View
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticAmbientOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.AmbientView
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Taken from https://goo.gle/compose-insets. Requires androidx.core:core v1.5.0-alpha02+
 */

/**
 * Main holder of our inset values.
 */
@Stable
class DisplayInsets {
  /**
   * Inset values which match [WindowInsetsCompat.Type.systemBars]
   */
  val systemBars = Insets()

  /**
   * Inset values which match [WindowInsetsCompat.Type.systemGestures]
   */
  val systemGestures = Insets()

  /**
   * Inset values which match [WindowInsetsCompat.Type.navigationBars]
   */
  val navigationBars = Insets()

  /**
   * Inset values which match [WindowInsetsCompat.Type.statusBars]
   */
  val statusBars = Insets()

  /**
   * Inset values which match [WindowInsetsCompat.Type.ime]
   */
  val ime = Insets()
}

@Stable
class Insets {
  /**
   * The left dimension of these insets in pixels.
   */
  var left by mutableStateOf(0)
    internal set

  /**
   * The top dimension of these insets in pixels.
   */
  var top by mutableStateOf(0)
    internal set

  /**
   * The right dimension of these insets in pixels.
   */
  var right by mutableStateOf(0)
    internal set

  /**
   * The bottom dimension of these insets in pixels.
   */
  var bottom by mutableStateOf(0)
    internal set

  /**
   * Whether the insets are currently visible.
   */
  var isVisible by mutableStateOf(true)
    internal set
}

val AmbientInsets = staticAmbientOf<DisplayInsets>()

/**
 * Applies any [WindowInsetsCompat] values to [AmbientInsets], which are then available
 * within [content].
 *
 * @param consumeWindowInsets Whether to consume any [WindowInsetsCompat]s which are dispatched to
 * the host view. Defaults to `true`.
 */
@Composable
fun ProvideDisplayInsets(
  consumeWindowInsets: Boolean = true,
  content: @Composable () -> Unit
) {
  val view = AmbientView.current

  val displayInsets = remember { DisplayInsets() }

  onCommit(view) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
      displayInsets.systemBars.updateFrom(windowInsets, WindowInsetsCompat.Type.systemBars())
      displayInsets.systemGestures.updateFrom(
        windowInsets,
        WindowInsetsCompat.Type.systemGestures()
      )
      displayInsets.statusBars.updateFrom(windowInsets, WindowInsetsCompat.Type.statusBars())
      displayInsets.navigationBars.updateFrom(
        windowInsets,
        WindowInsetsCompat.Type.navigationBars()
      )
      displayInsets.ime.updateFrom(windowInsets, WindowInsetsCompat.Type.ime())

      if (consumeWindowInsets) WindowInsetsCompat.CONSUMED else windowInsets
    }

    // Add an OnAttachStateChangeListener to request an inset pass each time we're attached
    // to the window
    val attachListener = object : View.OnAttachStateChangeListener {
      override fun onViewAttachedToWindow(v: View) = v.requestApplyInsets()
      override fun onViewDetachedFromWindow(v: View) = Unit
    }
    view.addOnAttachStateChangeListener(attachListener)

    if (view.isAttachedToWindow) {
      // If the view is already attached, we can request an inset pass now
      view.requestApplyInsets()
    }

    onDispose {
      view.removeOnAttachStateChangeListener(attachListener)
    }
  }

  Providers(AmbientInsets provides displayInsets) {
    content()
  }
}

/**
 * Updates our mutable state backed [Insets] from an Android system insets.
 */
private fun Insets.updateFrom(windowInsets: WindowInsetsCompat, type: Int) {
  val insets = windowInsets.getInsets(type)
  left = insets.left
  top = insets.top
  right = insets.right
  bottom = insets.bottom

  isVisible = windowInsets.isVisible(type)
}

/**
 * Apply additional space which matches the height of the status bars height along the top edge
 * of the content.
 */
fun Modifier.statusBarsPadding() = composed {
  insetsPadding(insets = AmbientInsets.current.statusBars, top = true)
}

/**
 * Apply additional space which matches the height of the navigation bars height
 * along the [bottom] edge of the content, and additional space which matches the width of
 * the navigation bars on the respective [left] and [right] edges.
 *
 * @param bottom Whether to apply padding to the bottom edge, which matches the navigation bars
 * height (if present) at the bottom edge of the screen. Defaults to `true`.
 * @param left Whether to apply padding to the left edge, which matches the navigation bars width
 * (if present) on the left edge of the screen. Defaults to `true`.
 * @param right Whether to apply padding to the right edge, which matches the navigation bars width
 * (if present) on the right edge of the screen. Defaults to `true`.
 */
fun Modifier.navigationBarsPadding(
  bottom: Boolean = true,
  left: Boolean = true,
  right: Boolean = true
) = composed {
  insetsPadding(
    insets = AmbientInsets.current.navigationBars,
    left = left,
    right = right,
    bottom = bottom
  )
}

/**
 * Declare the height of the content to match the height of the navigation bars, plus some
 * additional height passed in via [additional]
 *
 * As an example, this could be used with `Spacer` to push content above the navigation bar
 * and bottom app bars:
 *
 * ```
 * Column {
 *     // Content to be drawn above navigation bars and bottom app bar (y-axis)
 *
 *     Spacer(Modifier.statusBarHeightPlus(48.dp))
 * }
 * ```
 *
 * Internally this matches the behavior of the [Modifier.height] modifier.
 *
 * @param additional Any additional height to add to the status bars size.
 */
fun Modifier.navigationBarsHeightPlus(additional: Dp) = composed {
  InsetsSizeModifier(
    insets = AmbientInsets.current.navigationBars,
    heightSide = VerticalSide.Bottom,
    additionalHeight = additional
  )
}

enum class HorizontalSide {
  Left,
  Right
}

enum class VerticalSide {
  Top,
  Bottom
}

/**
 * Allows conditional setting of [insets] on each dimension.
 */
private fun Modifier.insetsPadding(
  insets: Insets,
  left: Boolean = false,
  top: Boolean = false,
  right: Boolean = false,
  bottom: Boolean = false
) = this then InsetsPaddingModifier(insets, left, top, right, bottom)

private data class InsetsPaddingModifier(
  private val insets: Insets,
  private val applyLeft: Boolean = false,
  private val applyTop: Boolean = false,
  private val applyRight: Boolean = false,
  private val applyBottom: Boolean = false
) : LayoutModifier {
  override fun MeasureScope.measure(
    measurable: Measurable,
    constraints: Constraints
  ): MeasureResult {
    val left = if (applyLeft) insets.left else 0
    val top = if (applyTop) insets.top else 0
    val right = if (applyRight) insets.right else 0
    val bottom = if (applyBottom) insets.bottom else 0
    val horizontal = left + right
    val vertical = top + bottom

    val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))

    val width = (placeable.width + horizontal)
      .coerceIn(constraints.minWidth, constraints.maxWidth)
    val height = (placeable.height + vertical)
      .coerceIn(constraints.minHeight, constraints.maxHeight)
    return layout(width, height) {
      placeable.place(left, top)
    }
  }
}

private data class InsetsSizeModifier(
  private val insets: Insets,
  private val widthSide: HorizontalSide? = null,
  private val additionalWidth: Dp = 0.dp,
  private val heightSide: VerticalSide? = null,
  private val additionalHeight: Dp = 0.dp
) : LayoutModifier {
  private val Density.targetConstraints: Constraints
    get() {
      val additionalWidthPx = additionalWidth.toIntPx()
      val additionalHeightPx = additionalHeight.toIntPx()
      return Constraints(
        minWidth = additionalWidthPx + when (widthSide) {
          HorizontalSide.Left -> insets.left
          HorizontalSide.Right -> insets.right
          null -> 0
        },
        minHeight = additionalHeightPx + when (heightSide) {
          VerticalSide.Top -> insets.top
          VerticalSide.Bottom -> insets.bottom
          null -> 0
        },
        maxWidth = when (widthSide) {
          HorizontalSide.Left -> insets.left + additionalWidthPx
          HorizontalSide.Right -> insets.right + additionalWidthPx
          null -> Constraints.Infinity
        },
        maxHeight = when (heightSide) {
          VerticalSide.Top -> insets.top + additionalHeightPx
          VerticalSide.Bottom -> insets.bottom + additionalHeightPx
          null -> Constraints.Infinity
        }
      )
    }

  override fun MeasureScope.measure(
    measurable: Measurable,
    constraints: Constraints
  ): MeasureResult {
    val wrappedConstraints = targetConstraints.let { targetConstraints ->
      val resolvedMinWidth = if (widthSide != null) {
        targetConstraints.minWidth
      } else {
        constraints.minWidth.coerceAtMost(targetConstraints.maxWidth)
      }
      val resolvedMaxWidth = if (widthSide != null) {
        targetConstraints.maxWidth
      } else {
        constraints.maxWidth.coerceAtLeast(targetConstraints.minWidth)
      }
      val resolvedMinHeight = if (heightSide != null) {
        targetConstraints.minHeight
      } else {
        constraints.minHeight.coerceAtMost(targetConstraints.maxHeight)
      }
      val resolvedMaxHeight = if (heightSide != null) {
        targetConstraints.maxHeight
      } else {
        constraints.maxHeight.coerceAtLeast(targetConstraints.minHeight)
      }
      Constraints(
        resolvedMinWidth,
        resolvedMaxWidth,
        resolvedMinHeight,
        resolvedMaxHeight
      )
    }
    val placeable = measurable.measure(wrappedConstraints)
    return layout(placeable.width, placeable.height) {
      placeable.place(0, 0)
    }
  }

  override fun IntrinsicMeasureScope.minIntrinsicWidth(
    measurable: IntrinsicMeasurable,
    height: Int
  ) = measurable.minIntrinsicWidth(height).let {
    val constraints = targetConstraints
    it.coerceIn(constraints.minWidth, constraints.maxWidth)
  }

  override fun IntrinsicMeasureScope.maxIntrinsicWidth(
    measurable: IntrinsicMeasurable,
    height: Int
  ) = measurable.maxIntrinsicWidth(height).let {
    val constraints = targetConstraints
    it.coerceIn(constraints.minWidth, constraints.maxWidth)
  }

  override fun IntrinsicMeasureScope.minIntrinsicHeight(
    measurable: IntrinsicMeasurable,
    width: Int
  ) = measurable.minIntrinsicHeight(width).let {
    val constraints = targetConstraints
    it.coerceIn(constraints.minHeight, constraints.maxHeight)
  }

  override fun IntrinsicMeasureScope.maxIntrinsicHeight(
    measurable: IntrinsicMeasurable,
    width: Int
  ) = measurable.maxIntrinsicHeight(width).let {
    val constraints = targetConstraints
    it.coerceIn(constraints.minHeight, constraints.maxHeight)
  }
}
