import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.kaku.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
}

apollo {
    service("service") {
        packageName.set("com.kaku.graphql")
        generateKotlinModels.set(true)
        generateApolloMetadata.set(true)

        dependsOn(project(":network"))
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":network"))

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    ksp(libs.moshi.codegen) // for @JsonClass(generateAdapter = true)

    testImplementation(libs.junit)
}
