package com.hypixel.hytale.plugin;

/**
 * Stub class representing the Hytale JavaPlugin base class.
 * This will be provided by the actual Hytale Server at runtime.
 */
public abstract class JavaPlugin {

    protected JavaPlugin(JavaPluginInit init) {
        // Initialization handled by Hytale runtime
    }

    /**
     * Called during plugin setup phase.
     * Register commands, events, and assets here.
     */
    public void setup() {
        // Override in subclass
    }

    /**
     * Called when the plugin is shutting down.
     * Clean up resources and save data here.
     */
    public void shutdown() {
        // Override in subclass
    }
}
