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

package com.skydoves.disneycompose.persistence

import com.skydoves.disneycompose.MainCoroutinesRule
import com.skydoves.disneycompose.model.Poster
import com.skydoves.disneycompose.utils.MockTestUtil.mockPosterList
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class PosterDaoTest : LocalDatabase() {

  private lateinit var posterDao: PosterDao

  @get:Rule
  val coroutinesRule = MainCoroutinesRule()

  @Before
  fun init() {
    posterDao = db.posterDao()
  }

  @Test
  fun insertAndLoadPosterListTest() = runTest {
    val mockDataList = mockPosterList()
    posterDao.insertPosterList(mockDataList)

    val loadFromDB = posterDao.getPosterList()
    assertThat(loadFromDB.toString(), `is`(mockDataList.toString()))

    val mockData = Poster.mock()
    assertThat(loadFromDB[0].toString(), `is`(mockData.toString()))
  }
}
