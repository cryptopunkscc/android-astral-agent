# Android Astral Agent

[astrald](https://github.com/cryptopunkscc/astrald) agent for Android OS

## Features

* Foreground service for keeping astrald alive
* Ongoing notification
* Embedded goja runner for js app backend
* WebView runner for js app frontend
* Apphost support via js adapter
* Installing js app from zip bundle

## Dev dependencies

* GO 1.19
* JDK 11 or later

## How to build

Build app & generate apk file for Android OS:

```shell
./gradlew :app:assembleDebug
```

Locate generated apk file under following path:

```shell
./app/build/outputs/apk/debug/app-debug.apk
```

## How to install

Just copy apk to Android device manually and install.

Or install & start using adb commands:

```shell
adb install ./app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n cc.cryptopunks.astral.agent/cc.cryptopunks.astral.agent.MainActivity
```
