package com.skydoves.disneycompose.ui.custom

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.skydoves.disneycompose.R
import com.skydoves.disneycompose.ui.posters.DisneyHomeTab
import com.skydoves.disneycompose.ui.theme.purple200

/**
 * @author abhianv chouhan
 * @link https://medium.com/@thegoldycopythat/animated-bottom-navigation-in-jetpack-compose-af8f590fbeca
 */
@Composable
fun AnimatedBottomNav(
    modifier: Modifier = Modifier,
    tabs: Array<DisneyHomeTab> = DisneyHomeTab.values(),
    selectedTab:  DisneyHomeTab,
    onItemClick: (tab:Int) -> Unit,
) {

    Box {
        var width by remember { mutableStateOf(0f) }
        var currentIndex by remember { mutableStateOf(0) }
        val offsetAnim by animateFloatAsState(
            targetValue = when (currentIndex) {
                1 -> width / 2f - with(LocalDensity.current) { 50.dp.toPx() }
                2 -> width - with(LocalDensity.current) { 100.dp.toPx() }
                else -> 0f
            }
        )
        BottomNavigation(
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    width = it.size.width.toFloat()
                },
            backgroundColor = purple200
        ) {
            tabs.forEachIndexed { index, tab ->
                AnimatedBottomNavigationItem(
                    label = tab.title,
                    icon = tab.icon,
                    selected = tab == selectedTab,
                    onClick = {
                        onItemClick(tab.title)
                        currentIndex = index
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(3.dp)
                .offset(with(LocalDensity.current) { offsetAnim.toDp() }, 0.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.White)
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AnimatedBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: Int,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = LocalContentColor.current
) {

    val top by animateDpAsState(
        targetValue = if (selected) 0.dp else 56.dp,
        animationSpec = SpringSpec(dampingRatio = 0.5f, stiffness = 200f)
    )
    Box(
        modifier = Modifier
            .height(56.dp)
            .padding(start = 30.dp, end = 30.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick.invoke()
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            tint = selectedContentColor,

            contentDescription = null,
            modifier = Modifier
                .height(56.dp)
                .width(26.dp)
                .offset(y = top)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(56.dp)
                .offset(y = top - 56.dp)
        ) {
            NormalBoldText(
                text = stringResource(id = label),
            )
        }
    }
}


data class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String,
)


@Composable
fun NormalBoldText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}
