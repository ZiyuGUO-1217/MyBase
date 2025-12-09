import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    detekt {
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
    }

    ktlint {
        android = true // Enable Android-specific linting rules
        ignoreFailures = false // Fail the build if KtLint finds any issues
        reporters {
            // Output KtLint results in plain text format
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.HTML) // Output KtLint results in HTML format
        }
    }

    dependencies {
        detektPlugins(rootProject.libs.detekt.formatting)
        detektPlugins(rootProject.libs.detekt.compose)
        ktlintRuleset(rootProject.libs.ktlint.compose)
    }

    pluginManager.withPlugin("com.android.library") {
        configure<com.android.build.api.dsl.LibraryExtension> {
            lint {
                lintConfig = rootProject.file("config/lint/lint.xml")
                abortOnError = true
                checkReleaseBuilds = true
                warningsAsErrors = true
                ignoreTestSources = true
            }
        }
    }
}
