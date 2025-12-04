plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.grupo_04_tarea_12_ejercicio_01"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.grupo_04_tarea_12_ejercicio_01"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    //room:2.6.1
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    annotationProcessor(libs.androidx.room.compiler) {
        exclude(group = "org.jetbrains", module = "annotations")
    }

    //hilt de dagger:2.51.1 com.dagger.hilt
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)

    // Testing
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}