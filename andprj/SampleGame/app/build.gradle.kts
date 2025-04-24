plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "kr.ac.tukorea.ge.scgyong.spgp2025.samplegame"
    compileSdk = 35

    defaultConfig {
        applicationId = "kr.ac.tukorea.ge.scgyong.spgp2025.samplegame"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
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
}

dependencies {
    // a2dg 모듈을 app 모듈에서 사용하도록 설정
    // :는 루트(최상위) 프로젝트부터 시작해서 하위 모듈을 지정한다는 뜻
    implementation(project(":a2dg"))

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}