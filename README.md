# popcorn <a href="https://repo.celerry.com/javadoc/releases/dev/kokiriglade/popcorn/latest"><img align="right" src="https://img.shields.io/badge/JavaDoc-Online-green"></a> <img src="https://img.shields.io/github/v/release/celerry/popcorn" align="right"> <img src="https://img.shields.io/github/license/celerry/popcorn" align="right">

Started off as a  fork of [corn](https://github.com/broccolai/corn), but just their `ItemBuilder` component. now aiming
to write general utilities & builders for the [Paper](https://github.com/PaperMC/paper) API.

## Gradle dependency

To add this project as a dependency for your Gradle project, make sure your dependencies section of your `build.gradle.kts` looks like the following:

```kotlin
dependencies {
    implementation("dev.kokiriglade:popcorn:3.0.0")
    // ...
}
```

You also need to add my Maven repository:

```kotlin
repositories {
    maven("https://repo.celerry.com/releases")
    // ...
}
```

In order to include the project in your own project, you will need to use the `shadowJar` plugin. If you don't have it already, add the following to the top of your file:

```kotlin
plugins {
    // ...
    id("io.github.goooler.shadow") version "8.1.7"
}
```

To relocate the project's classes to your own namespace, add the following, with `[YOUR PACKAGE]` being the top-level package of your project:
```kotlin
tasks {
    // ...
    shadowJar {
        relocate("dev.kokiriglade.popcorn", "[YOUR PACKAGE].popcorn")
    }
}
```

## Dependency via plugin.yml

popcorn does not support declaring the dependency via the libraries section in the plugin.yml. Please make use of a build tool as described above to use popcorn as a dependency.
