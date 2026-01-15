package dev.ewwegg.capital.core;

import com.hypixel.hytale.plugin.JavaPlugin;
import com.hypixel.hytale.plugin.JavaPluginInit;
import java.util.logging.Logger;

/**
 * Main entry point for the Capital plugin.
 * Manages land claiming and protection for Hytale servers.
 */
public class Capital extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger(Capital.class.getName());

    public Capital(JavaPluginInit init) {
        super(init);
    }

    @Override
    public void setup() {
        LOGGER.info("Capital plugin loading...");

        // Register commands (placeholder for future implementation)
        registerCommands();

        // Register event listeners (placeholder for future implementation)
        registerEvents();

        LOGGER.info("Capital plugin loaded successfully");
    }

    @Override
    public void shutdown() {
        LOGGER.info("Capital plugin shutting down...");

        // Cleanup will be added when managers are implemented

        LOGGER.info("Capital plugin shutdown complete");
    }

    private void registerCommands() {
        // Placeholder - commands will be registered in future issues
    }

    private void registerEvents() {
        // Placeholder - event listeners will be registered in future issues
    }
}
