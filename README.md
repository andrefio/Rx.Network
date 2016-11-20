# Rx.Network
[![Release](https://jitpack.io/v/io.andref/Rx.Network.svg)](https://jitpack.io/#io.andref/Rx.Network)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

Listen for Android's `CONNECTIVITY_CHANGE` broadcasts.

## Use

Add the `ACCESS_NEWORK_STATE` permission to `AndroidManifest.xml`

```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

### Observing

This library exposes three different observables. They all monitor network connectivity the same way, but they provided varying levels of specificity about the state of the network.

#### Boolean

Use this observable when all you're interested in is knowing whether the network is connected or not.

```java
	ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    RxNetwork.connectivityChanges(this, connectivityManager)
        .subscribe(new Action1<Boolean>()
        {
            @Override
            public void call(Boolean connected)
            {
                // [...]
            }
        }
```

#### NetworkInfo.State

If you want just a little more information about the state of the network, but don't want to be weighed down by the minutia of things, then use this observable. Read [NetworkInfo.State](https://developer.android.com/reference/android/net/NetworkInfo.State.html) for more information about what you'll get back.

```java
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    RxNetwork.stateChanges(this, connectivityManager)
        .subscribe(new Action1<NetworkInfo.State>()
        {
            @Override
            public void call(NetworkInfo.State state)
            {
                // [...]
            }
        }
```

#### NetworkInfo.DetailedState

Here's the big daddy. This observable will tell you the "fine-grained" state of the network. Read [NetworkInfo.DetailedState](https://developer.android.com/reference/android/net/NetworkInfo.DetailedState.html) for more information about what you'll get back.

```java
    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    RxNetwork.connectivityChanges(this, connectivityManager)
        .subscribe(new Action1<NetworkInfo.DetailedState>()
        {
            @Override
            public void call(NetworkInfo.DetailedState detailedState)
            {
                // [...]
            }
        }
```

## Binaries

Add the JitPack repository to your root build.gradle at the end of repositories:

```groovy
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
   }
```

And then add this library to your project:

```groovy
   dependencies {
        compile 'io.andref:Rx.Network:1.0.1'
   }
```


## License

Copyright 2016 Michael De Soto

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.