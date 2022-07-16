
<h1 align="center">DisneyCompose</h1></br>
<p align="center">  
A demo Disney app using compose and Hilt based on modern Android tech-stacks and MVVM architecture. Fetching data from the network and integrating persisted data in the database via repository pattern.<br> Declarative UI version of the <a href="https://github.com/skydoves/DisneyMotions" target="_blank"> DisneyMotions </a> using compose.
</p>
</br>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/skydoves/DisneyCompose/actions"><img alt="Build Status" src="https://github.com/skydoves/DisneyCompose/workflows/Android%20CI/badge.svg"/></a>
  <a href="https://proandroiddev.com/exploring-jetpack-compose-with-dagger-hilt-and-viewmodels-3e0ca939daa7"><img alt="Medium" src="https://skydoves.github.io/badges/Story-Medium.svg"/></a>
  <a href="https://github.com/skydoves"><img alt="Profile" src="https://skydoves.github.io/badges/skydoves.svg"/></a> 
</p>

## Download
Go to the [Releases](https://github.com/skydoves/DisneyCompose/releases) to download the latest APK.

## Screenshots
<p align="center">
<img src="https://user-images.githubusercontent.com/24237865/93901108-238eb000-fd31-11ea-9fac-c9ba1eca146c.gif" width="280"/>
<img src="/preview/preview0.gif" width="280"/>
<img src="/preview/preview1.gif" width="280"/>
</p>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt for dependency injection.
- JetPack
  - Compose - A modern toolkit for building native Android UI.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.
  - App Startup - Provides a straightforward, performant way to initialize components at application startup.
- Architecture
  - MVVM Architecture (Declarative View - ViewModel - Model)
  - Repository pattern
- Material Design & Animations
- [Accompanist](https://github.com/google/accompanist) - A collection of extension libraries for Jetpack Compose.
- [Landscapist](https://github.com/skydoves/landscapist) - Jetpack Compose image loading library with shimmer & circular reveal animations.
- [Orchestra-Balloon](https://github.com/skydoves/orchestra) - Jetpack Compose tooltips library.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Sandwich](https://github.com/skydoves/Sandwich) - construct lightweight http API response and handling error responses.
- [WhatIf](https://github.com/skydoves/whatif) - checking nullable object and empty collections more fluently.
- [Balloon](https://github.com/skydoves/balloon) -  A lightweight popup like tooltips, fully customizable with arrow and animations.
- [Timber](https://github.com/JakeWharton/timber) - logging.

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/DisneyCompose/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/skydoves)__ me for my next creations! 🤩

<img src="https://user-images.githubusercontent.com/24237865/141674368-6013d77c-d52b-4bb1-afe4-9a57a06be32f.jpg" width="18%" align="right" />

## Contents Credits
All copyrights of the contents, concepts, and phrases used for this open-source project belong to [The Walt Disney Company](https://www.disneyplus.com/).

# License
```xml
Designed and developed by 2020 skydoves (Jaewoong Eum)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
