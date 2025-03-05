plugins {
    base //allows IDE clean to trigger clean on this module too
}

configurations.maybeCreate("default")
artifacts.add("default", file("outline-sdk-1.0.0.aar"))

group = "kg.ram.outline-sdk"
version = "1.0.0"