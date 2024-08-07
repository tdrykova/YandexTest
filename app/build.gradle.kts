plugins {
    id("kotlin-kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id("com.google.devtools.ksp")
//    kotlin("multiplatform")
//    id 'org.jetbrains.kotlin.plugin.serialization' version 'YOUR_KOTLIN_VERSION'

    id("org.jetbrains.kotlin.plugin.serialization")
}


kotlin {
//    androidTarget()
//    js().browser()
//    macosX64()
//    iosX64()
//    iosArm64()

//    sourceSets {
//        val commonMain by getting {
//            dependencies {
//                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
//                implementation("com.juul.kable:core:1.0.0")
//            }
//        }
//
//        val androidMain by getting {
//            dependencies {
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
//            }
//        }
//    }
}


//kotlin {
//    jvmToolchain(8)
//}

android {
    namespace = "com.tatry.yandextest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tatry.yandextest"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        buildConfigField 'String', 'KEY', KEY
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
//        sourceCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_17

    }
    kotlinOptions {
//        jvmTarget = "1.8"
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Navigation Jetpack
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Moshi
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
//    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

//    ksp("androidx.room:room-compiler:2.6.1")
    // Room
//    implementation("android.arch.persistence.room:runtime:1.1.1")
//    kapt("android.arch.persistence.room:compiler:1.1.1")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // viewpager2
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")


    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    // Snackbar
    implementation("com.github.Musfick:Snackify:0.1.2")

    //Flex
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    // Dagger
    implementation("com.google.dagger:dagger:2.51.1")
    kapt("com.google.dagger:dagger-compiler:2.51.1")

}