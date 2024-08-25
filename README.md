# RCache

[![](https://jitpack.io/v/rahmat3nanda/RCache-Android.svg)](https://jitpack.io/#rahmat3nanda/RCache-Android)

RCache is an Android library designed to simplify the process of storing and managing data securely. It provides a unified API for storing data in both `SharedPreferences` and `EncryptedSharedPreferences`.

## Features

- **General SharedPreferences Storage**: Easy-to-use methods for storing and retrieving data from `SharedPreferences`.
- **Encrypted SharedPreferences Storage**: Secure storage for sensitive data using `EncryptedSharedPreferences`.
- **JitPack Support**: Easily integrate with your Android projects using JitPack.

## Installation
### JitPack

1. Add the JitPack repository to your build file.
   
   Add it in your root build.gradle at the end of repositories:
```groovy
    dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
        }
    }
```
2. Add the dependency
```groovy
    dependencies {
        // Others Dependencies
        implementation 'com.github.rahmat3nanda:RCache-Android:1.0.3'
    }
```
3. Add Required dependency
```groovy
    dependencies {
        // Others Dependencies
        implementation("androidx.security:security-crypto:1.1.0-alpha06")
    }
```

### Local

1. Download the latest release of [RCache](https://github.com/rahmat3nanda/RCache-Android/releases). It is recommended to use `.aar` files
2. Define local library in `build.gradle` (`app level`)
```groovy
android {
   // Others config
   
   defaultConfig {
      // Others config

      //Add this block
      sourceSets.main {
         jni.srcDirs = []
         jniLibs.srcDir "libs"
      }
   }
}

dependencies {
   implementation fileTree(include: ['*.jar'], dir: 'libs') // Add this line for .jar files
   implementation fileTree(include: ['*.aar'], dir: 'libs') // Add this line for .aar files
}
```
3. Copy the downloaded RCache binary to the `jniLibs` folder.

   ![img.png](jniLibs.png)
4. Add Required dependency
```groovy
    dependencies {
        // Others Dependencies
        implementation("androidx.security:security-crypto:1.1.0-alpha06")
    }
```

## Usage

RCache saves data to SharedPreferences and EncryptedSharedPreferences
```
import id.nesd.rcache.RCache

// initialize RCache
RCache.initialize(this)

// save/load SharedPreferences data
RCache.common

// save/load EncryptedSharedPreferences data
RCache.credentials
```



Define your own Key
```
object MyRCacheKey {
    val myKey: RCache.Key = RCache.Key("myKey")
}
```

## License
RCache is released under the MIT License. See the [LICENSE](https://github.com/rahmat3nanda/RCache-Android?tab=MIT-1-ov-file) file for details.

## Contact
For any questions or feedback, feel free to reach out to [rahmat3nanda@gmail.com](mailto:rahmat3nanda@gmail.com) or [My LinkedIn](https://www.linkedin.com/in/rahmat-trinanda/).
