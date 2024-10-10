package dev.seeruk.plugin.renewables;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import dev.seeruk.plugin.renewables.config.Config;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

public final class RenewablesPlugin extends JavaPlugin {

    private RenewablesListener renewablesListener;

    @Override
    public void onEnable() {
        saveResource("config.dist.yml", true);

        var config = getConfigWithDefaults().orElseThrow();
        var server = getServer();

        this.renewablesListener = new RenewablesListener(config);

        // Register events...
        server.getPluginManager().registerEvents(this.renewablesListener, this);
    }

    @Override
    public void onDisable() {
        // Unregister events...
        HandlerList.unregisterAll(this.renewablesListener);
    }

    private Optional<Config> getConfigWithDefaults() {
        var mapper = new YAMLMapper();
        var configFile = new File(getDataFolder(), "config.yml");
        var defaultFile = Objects.requireNonNull(getResource("config.dist.yml"));

        var config = new Config();

        try {
            mapper.readerForUpdating(config).readValue(defaultFile);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "failed to read default configuration: " + e.getMessage());
            return Optional.empty();
        }

        try {
            mapper.readerForUpdating(config).readValue(configFile);
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "failed to read configuration: " + e.getMessage());
        }

        return Optional.of(config);
    }
}
