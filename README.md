# BPInfo

[![CircleCI](https://circleci.com/gh/ofalvai/BPInfo.svg?style=svg)](https://circleci.com/gh/ofalvai/BPInfo)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fofalvai%2FBPInfo.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Fofalvai%2FBPInfo?ref=badge_shield)

Budapest public transport info app based on public transport APIs.

[__Get It On Google Play__](play.google.com/store/apps/details?id=com.ofalvai.bpinfo)

![Screenshot](/screenshots.png?raw=true)

## Features

- real time public transport info
- push notifications
- current and planned traffic changes
- filter vehicle types
- small, optimized APK size (currently ~1.75 MB)


## Project

This app is also a demo project to try new technologies, libraries, architectures.

Technologies and libraries used:

- [Kotlin](https://kotlinlang.org)
- androidx.*
    - [ConstraintLayout](https://developer.android.com/training/constraint-layout)
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Koin](https://insert-koin.io/) for dependency injection
- Firebase
    - [Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/)
    - [Crashlytics](https://firebase.google.com/docs/crashlytics/)
    - [Performance](https://firebase.google.com/docs/perf-mon/)
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
- [ThreeTenABP](https://github.com/JakeWharton/ThreeTenABP)
- [LeakCanary](https://github.com/square/leakcanary)
- [Android App Bundle](https://developer.android.com/platform/technology/app-bundle/)

Libraries intentionally not used:

- [RxJava](https://github.com/ReactiveX/RxJava): would be overkill for such simple use cases
- [GSON](https://github.com/google/gson): the API responses require a lot of custom parsing and mapping that I decided to parse with custom code

Future plans:

- Kotlin coroutines
- Jetpack Navigation
- More tests


## License
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fofalvai%2FBPInfo.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2Fofalvai%2FBPInfo?ref=badge_large)