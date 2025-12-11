
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.kaku.mybase"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kaku.mybase"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    signingConfigs {
//        create("debug") {
//            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
//        }
//    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
        }
        release {
            signingConfig = signingConfigs.getByName("debug") // temp set to debug
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    lint {
        lintConfig = rootProject.file("config/lint/lint.xml")
        abortOnError = true
        checkReleaseBuilds = true
        warningsAsErrors = true
    }

    detekt {
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
    }

    ktlint {
        android = true // Enable Android-specific linting rules
        ignoreFailures = false // Fail the build if KtLint finds any issues
    }
}

dependencies {
    implementation(project(":ui"))
    implementation(project(":data")) // for di
    implementation(project(":network")) // for di

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.timber)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.compose)
    ktlintRuleset(libs.ktlint.compose)

    testImplementation(libs.junit)
}
