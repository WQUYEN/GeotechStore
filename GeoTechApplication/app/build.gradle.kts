plugins {
    id("com.android.application")
}

android {
    namespace = "quyenvvph20946.fpl.geoteachapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "quyenvvph20946.fpl.geoteachapplication"
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.squareup.picasso:picasso:-2.71828")
    implementation ("com.google.code.gson:gson:-2.10.1")


    implementation("com.google.android.gms:play-services-auth:16.7.0")
    implementation("androidx.activity:activity:1.8.0")
    implementation(fileTree(mapOf("dir" to "D:\\zalopay", "include" to listOf("*.aar", "*.jar"), "exclude" to listOf<String>())))
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation ("com.google.code.gson:gson:2.10.1")

    //lottie

    implementation ("com.airbnb.android:lottie:5.2.0")


    implementation ("io.github.chaosleung:pinview:1.4.4")

    // navigation mew
    implementation ("com.etebarian:meow-bottom-navigation:1.2.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.61")

    // jwt
    implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation ("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.11.2")

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")


    implementation("com.google.android.gms:play-services-auth:20.7.0")

    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.0.2")

    // socket
    implementation ("io.socket:socket.io-client:2.0.0") {

    }

    implementation ("com.github.dhaval2404:imagepicker:2.1")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    // add the dependency for the Google AI client SDK for Android
    implementation("com.google.ai.client.generativeai:generativeai:0.3.0")

    // Required for one-shot operations (to use `ListenableFuture` from Guava Android)
    implementation("com.google.guava:guava:31.0.1-android")

    // Required for streaming operations (to use `Publisher` from Reactive Streams)
    implementation("org.reactivestreams:reactive-streams:1.0.4")

    // momo
//    implementation("com.github.momo-wallet:mobile-sdk:1.0.7")

    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

// define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    //zalo
    implementation("commons-codec:commons-codec:1.14")


}