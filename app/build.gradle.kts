plugins {
    id("com.android.application")
    kotlin("android")
}

baseConfig()

compose(true)

android {
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {

    androidBase()
    compose()
    retrofit()

    // Glide
   // implementation("com.github.bumptech.glide:glide:4.11.0")
    //kapt("com.github.bumptech.glide:compiler:4.11.0")

}