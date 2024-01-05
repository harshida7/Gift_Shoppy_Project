plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.giftshoppy_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.giftshoppy_project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    implementation ("com.razorpay:checkout:1.6.4")
    implementation ("com.airbnb.android:lottie:3.4.0")
        implementation ("com.nineoldandroids:library:2.4.0")

    implementation ("com.daimajia.slider:library:1.1.5@aar")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //implementation("com.squareup.retrofit2:convertor-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    //implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.mikhaellopez:circularimageview:4.3.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.squareup.picasso:picasso:2.3.2")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation ("com.github.chrisbanes:PhotoView:2.0.0")


    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}