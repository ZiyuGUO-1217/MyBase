import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.kaku.network"
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
        generateKotlinModels.set(true)
        generateApolloMetadata.set(true)
        packageName.set("com.kaku.graphql")

        introspection {
            endpointUrl.set("https://swapi-graphql.netlify.app/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

dependencies {
    implementation(libs.retrofit)
    api(libs.retrofit.converter.moshi)

    api(libs.apollo.runtime)
    implementation(libs.apollo.normalized.cache)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
}
