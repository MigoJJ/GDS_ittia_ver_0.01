plugins {
    application
}

repositories {
    mavenCentral()
}

val javaFxVersion = "21.0.1"
val platform = "linux" // adjust for your OS

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

val javaFxModules = listOf("javafx.controls", "javafx.fxml")
val javafxLibs = configurations.runtimeClasspath.get()
    .filter { it.name.contains("javafx") }
    .joinToString(File.pathSeparator) { it.absolutePath }

tasks.named<JavaCompile>("compileJava") {
    options.compilerArgs = listOf(
        "--module-path", javafxLibs,
        "--add-modules", javaFxModules.joinToString(",")
    )
}

tasks.named<JavaExec>("run") {
    jvmArgs = listOf(
        "--module-path", javafxLibs,
        "--add-modules", javaFxModules.joinToString(",")
    )
}
