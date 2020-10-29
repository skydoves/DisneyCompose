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

import androidx.annotation.WorkerThread
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.skydoves.disneycompose.base.LiveCoroutinesViewModel
import com.skydoves.disneycompose.model.Poster
import com.skydoves.disneycompose.repository.DetailRepository
import com.skydoves.disneycompose.repository.MainRepository
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(
  private val mainRepository: MainRepository,
  private val detailRepository: DetailRepository
) : LiveCoroutinesViewModel() {

  private var _posterList: MutableLiveData<Boolean> = MutableLiveData(true)
  val posterList: LiveData<List<Poster>>

  private var _posterDetails: LiveData<Poster> = MutableLiveData()
  val posterDetails: LiveData<Poster> get() = _posterDetails

  private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
  val isLoading: LiveData<Boolean> get() = _isLoading

  private val _toast: MutableLiveData<String> = MutableLiveData()
  val toast: LiveData<String> get() = _toast

  init {
    Timber.d("injection MainViewModel")

    posterList = _posterList.switchMap {
      _isLoading.postValue(true)
      launchOnViewModelScope {
        this.mainRepository.loadDisneyPosters(
          onSuccess = { _isLoading.postValue(false) },
          onError = { _toast.postValue(it) }
        ).asLiveData()
      }
    }
  }

  @WorkerThread
  fun getPoster(id: Long) {
    _posterDetails = detailRepository.getPosterById(id)
  }
}
