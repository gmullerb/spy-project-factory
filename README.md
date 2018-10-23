# Spy Project Factory

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](/LICENSE.txt) [![Download](https://api.bintray.com/packages/gmullerb/all.shared.gradle/spy-project-factory/images/download.svg)](https://bintray.com/gmullerb/all.shared.gradle/spy-project-factory/_latestVersion)

**This project offers an utility class for creating Lighter spy Gradle Projects for test**.

This project is licensed under the terms of the [MIT license](/LICENSE.txt).

## Goals

Have a Gradle project for test with:

* Mock `Plugins` and Mock `PluginManager`.
  * Without this, `Project` will appeal to the network in order to get and apply the plugins, this can be time consuming and if the project use some paid by time platform this could be inadequate.
* Mock `Logger`.

## Features

Offers a [`SpyProjectFactory`](src/main/java/all/shared/gradle/testfixtures/SpyProjectFactory.java) class that has:

* `build()` method builds a Gradle **spy [`Project`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html)** with Default builder:
  * `pluginManager` field will be mocked.
  * `plugins` field will be mocked.
  * `logger` field will be mocked.
  * `name` field will be **Spy Project**.
* `build(final ProjectBuilder builder)` method builds a Gradle **spy [`Project`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html)** with provided builder:
  * `pluginManager` field will be mocked.
  * `plugins` field will be mocked.
  * `logger` field will be mocked.
* `builder` field, that allows to build `Project` without mocking.
  * This way only 1 [`ProjectBuilder`](https://docs.gradle.org/current/javadoc/org/gradle/testfixtures/ProjectBuilder.html) instance will exist in the test project.

## Using/Configuration

### Prerequisites

[`SpyProjectFactory`](src/main/java/all/shared/gradle/testfixtures/SpyProjectFactory.java) class will be used when using the Gradle API, then:

```gradle
  dependencies {
    compile gradleApi()
  }
```

> This library make use of [Mockito](http://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html) and is already set in the internal dependencies, therefore no need to add it to the project.

### Dependency configuration

1. Add repository:

```gradle
  repositories {
    maven {
      url 'https://dl.bintray.com/gmullerb/all.shared.gradle'
    }
  }
```

2. Add Test dependency:

```gradle
  dependencies {
    testCompile 'all.shared.gradle:spy-project-factory:+'
  }
```

### Use SpyProjectFactory utility class

#### Building a Spy Project with the Default Builder

```java
  final Project spyProject = SpyProjectFactory.build();
```

#### Building a Spy Project with the Custom Builder

```java
  final ProjectBuilder someBuilder = ProjectBuilder.builder()
    .withName(..);
  final Project spyProject = SpyProjectFactory.build(someBuilder);
```

#### Using the Spy Project

##### "Adding" a Plugin

Since [getPluginManager](https://docs.gradle.org/current/javadoc/org/gradle/api/plugins/PluginAware.html#getPluginManager--) and [getPlugins](https://docs.gradle.org/current/javadoc/org/gradle/api/plugins/PluginAware.html#getPlugins--) will be mocked, adding the plugin must be simulated [1], this imply:

* Must manually add any extensions the plugin adds and required by the test [2].
* Must manually add any task the plugin adds and required by the test [2].

```java
  spyProject.extensions.add(EXTENSION_NAME, someExtension)
  spyProject.tasks.create(TASK_NAME, someTask)
```

E.g:

```java
  final CodeNarcExtension extension = new CodeNarcExtension(spyProject)
  spyProject.extensions.add('codenarc', extension)
  spyProject.tasks.create('codenarcMain', CodeNarc)
```

> [1] The advantage is that the `Project` will not appeal the network to get the plugins, something that can be time consuming and if the project use some paid by time platform this could be inadequate.  
> [2] This can allow the mocking or spying of an extension or task.

##### Verifying

```java
  verify(spyProject.logger)
    .debug(..)
```

#### Building a Non spy Project

##### With default builder values

```java
    final Project someProject = SpyProjectFactory.build.build();
```

##### With custom builder values

```java
    final Project someProject = SpyProjectFactory.build
      .withName(..)
      .withProjectDir(..)
      ..
      .build();
```

## Extending/Developing

### Prerequisites

* [Java](http://www.oracle.com/technetwork/java/javase/downloads).
* [Git](https://git-scm.com/downloads) (only if you are going to clone the project).

### Getting it

Clone or download the project[1], in the desired folder execute:

```sh
git clone https://github.com/gmullerb/spy-project-factory
```

> [1] [Cloning a repository](https://help.github.com/articles/cloning-a-repository/)

### Set up

* **No need**, only download and run (It's Gradle! Yes!).

### Building

* To build it:
  * `gradlew`: this will run default task, or
  * `gradlew build`.

* To assess files:
  * `gradlew assessCommon`: will check common style of files.
  * `gradlew assessGradle`: will check code style of Gradle's.
  * `gradlew checkstyleMain`: will check code style of Java's source files.
  * `gradlew checkstyleTest`: will check code style of Java's test files.
  * `gradlew pmdMain`: will check code style of Java's source files.
  * `gradlew pmdTest`: will check code style of Java's test files.
  * `assemble` task depends on these six tasks.

* To test code: `gradlew test`
  * This task is finalized with a Jacoco Report.

* To get all the tasks for the project: `gradlew tasks --all`

### Folders structure

```
  /src
    /main
      /java
    /test
      /java
```

- `src/main/groovy`: Source code files.
  - [`SpyProjectFactory`](src/main/java/all/shared/gradle/testfixtures/SpyProjectFactory.java) is where all the magic happens.
- `src/test/groovy`: Test code files[1].

> [1] Tests are done with [JUnit](http://junit.org) and [Mockito](http://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html).

## Documentation

* [`CHANGELOG.md`](CHANGELOG.md): add information of notable changes for each version here, chronologically ordered [1].

> [1] [Keep a Changelog](http://keepachangelog.com)

## License

[MIT License](/LICENSE.txt)

## Additional words

Don't forget:

* **Love what you do**.
* **Learn everyday**.
* **Learn yourself**.
* **Share your knowledge**.
* **Learn from the past, dream on the future, live and enjoy the present to the max!**.

At life:

* Let's act, not complain.

At work:

* Let's give solutions, not questions.
