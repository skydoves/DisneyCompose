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

package com.skydoves.disneycompose.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skydoves.disneycompose.ui.details.PosterDetails
import com.skydoves.disneycompose.ui.posters.Posters

@Composable
fun DisneyMainScreen() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = NavScreen.Home.route) {
    composable(NavScreen.Home.route) {
      Posters(
        viewModel = hiltViewModel(),
        selectPoster = {
          navController.navigate("${NavScreen.PosterDetails.route}/$it")
        }
      )
    }
    composable(
      route = NavScreen.PosterDetails.routeWithArgument,
      arguments = listOf(
        navArgument(NavScreen.PosterDetails.argument0) { type = NavType.LongType }
      )
    ) { backStackEntry ->
      val posterId =
        backStackEntry.arguments?.getLong(NavScreen.PosterDetails.argument0) ?: return@composable

      PosterDetails(posterId = posterId, viewModel = hiltViewModel()) {
        navController.navigateUp()
      }
    }
  }
}

sealed class NavScreen(val route: String) {

  object Home : NavScreen("Home")

  object PosterDetails : NavScreen("PosterDetails") {

    const val routeWithArgument: String = "PosterDetails/{posterId}"

    const val argument0: String = "posterId"
  }
}
