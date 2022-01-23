# DaggerToHilt


### Overview
This repo provides a real example of using Hilt for dependency injection. It hits endpoints provided by the Movie Database, and offers the user the cabability of saving movies they want to a local Room database. 

### Details
This is meant to be a detailed POC of using Hilt in an application with Android best practices.

### Specific branches
There are a couple branches in this repo to focus on some of the details of dependency injection. One branch uses Dagger instead of Hilt. You can compare with the base to see how to convert from Dagger to Hilt. This code lab also probide a detailed explanation of migrating to Hilt. https://developer.android.com/codelabs/android-dagger-to-hilt#0
<br>
Hilt allows scoping of components to be shared. LoggerExample provides an example of sharing components across multiple classes, and using qualifiers to provide different implementations through Hilt.

### Getting started
To run the project yourself, you will need to set up an API key with the Movie Database. Follow the instructions here: https://developers.themoviedb.org/3/getting-started/introduction.
<br>
You will need to set up a gradle.properties file in your project root. Your gradle.properties file will look like this: 
```# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. More details, visit
# http://www.gradle.org/docs/current/userguide/multi_project_builds.html#sec:decoupled_projects
# org.gradle.parallel=true
# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app"s APK
# https://developer.android.com/topic/libraries/support-library/androidx-rn
android.useAndroidX=true
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official
# ensure that Hilt can compile with Kotlin 1.5.0
kapt.use.worker.api=false
# API key for hitting Movie Database server
movieDBApiKey="<Your API key here>"
```

To verify that your API key is set up correctly, run the tests in ServiceImplementationsTest.
