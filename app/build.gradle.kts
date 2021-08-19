plugins {
    id("com.android.application")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "com.boycoder.kotlinjetpackinaction"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.appCompat)
    implementation(Libs.constraintlayout)
    testImplementation(Libs.junit)

    implementation(Libs.volley)
    implementation(Libs.gson)

    implementation(Libs.glide)
    annotationProcessor(Libs.glideCompiler)
}