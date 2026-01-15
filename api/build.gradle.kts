// API module - Public interfaces for other mods to depend on
// Contains no implementation details

dependencies {
    // Hytale Server API (provided by server at runtime)
    compileOnly(files("../libs/hytale-server.jar"))
}
