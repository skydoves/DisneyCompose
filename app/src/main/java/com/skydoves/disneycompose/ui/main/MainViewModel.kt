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

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.skydoves.disneycompose.model.Poster
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  mainRepository: MainRepository
) : ViewModel() {

  val posterList: Flow<List<Poster>> =
    mainRepository.loadDisneyPosters(
      onStart = { _isLoading.value = true },
      onCompletion = { _isLoading.value = false },
      onError = { Timber.d(it) }
    )

  private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
  val isLoading: State<Boolean> get() = _isLoading

  private val _selectedTab: MutableState<Int> = mutableStateOf(0)
  val selectedTab: State<Int> get() = _selectedTab

  init {
    Timber.d("injection MainViewModel")
  }

  fun selectTab(@StringRes tab: Int) {
    _selectedTab.value = tab
  }
}
