plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.proyectomoviles"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.proyectomoviles"
        minSdk = 35
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
// Material Design
    implementation("com.google.android.material:material:1.12.0")
// ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha13")

    implementation("androidx.gridlayout:gridlayout:1.0.0")

    //Implementaciones para crear el cliente (RetrofitCliente)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
// Material Design
    implementation("com.google.android.material:material:1.10.0")

    //Implementacion de glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.picasso:picasso:2.8")

    //Implementacion de volley
    //implementation("com.android.volley:volley:1.1.1")




}