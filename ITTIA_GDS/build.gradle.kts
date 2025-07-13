plugins {
    application
}

repositories {
    mavenCentral()
}

val javaFxVersion = "21.0.1"
val platform = "linux" // your OS: "win", "mac", "linux"

dependencies {
    implementation("org.openjfx:javafx-base:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-controls:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-fxml:$javaFxVersion:$platform")
    implementation("org.openjfx:javafx-graphics:$javaFxVersion:$platform")

    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("com.opencsv:opencsv:5.9")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.1")
}

application {
    mainClass.set("com.ittia.gds.GDSittiaEntry")
}

// ✅ compileJava에도 JavaFX 모듈 경로 전달
tasks.named<JavaCompile>("compileJava") {
    options.compilerArgs = listOf(
        "--module-path", classpath.asPath,
        "--add-modules", "javafx.controls,javafx.fxml"
    )
}

// ✅ run 시에도 JVM에 JavaFX 모듈 전달
tasks.named<JavaExec>("run") {
    jvmArgs = listOf(
        "--module-path", classpath.asPath,
        "--add-modules", "javafx.controls,javafx.fxml"
    )
}
