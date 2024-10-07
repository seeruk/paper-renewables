package dev.seeruk.plugin.renewables;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class RenewablesPlugin extends JavaPlugin {

    private RenewablesListener renewablesListener;

    @Override
    public void onEnable() {
        var logger = getLogger();

        this.renewablesListener = new RenewablesListener(logger);

        // Register events...
        getServer().getPluginManager().registerEvents(this.renewablesListener, this);
    }

    @Override
    public void onDisable() {
        // Unregister events...
        HandlerList.unregisterAll(this.renewablesListener);
    }
}
