// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle-api:8.8.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:<kotlin-version>")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.1")
    }
}