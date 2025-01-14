description = "Http4k XML support using GSON as an underlying engine"

dependencies {
    api(project(":http4k-format-core"))
    api(project(":http4k-format-gson"))
    api("org.json:json:_")
    testImplementation(project(":http4k-core"))
    testImplementation(project(path = ":http4k-core", configuration ="testArtifacts"))
    testImplementation(project(path = ":http4k-format-core", configuration ="testArtifacts"))
}
