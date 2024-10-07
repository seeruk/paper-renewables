package dev.seeruk.plugin.renewables;

import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.List;
import java.util.logging.Logger;

public class RenewablesListener implements Listener {
    private final Logger logger;

    public RenewablesListener(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock fallingBlock) {
            var anvils = List.of(Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL);

            if (anvils.contains(fallingBlock.getBlockData().getMaterial())) {
                this.handleFallingAnvil(event, fallingBlock);
                return;
            }
        }
    }

    private void handleFallingAnvil(EntityChangeBlockEvent event, FallingBlock fallingBlock) {
        if (event.getTo() != fallingBlock.getBlockData().getMaterial()) {
            // The anvil has not yet finished falling
            return;
        }

        var loc = event.getEntity().getLocation();
        var landedAt = loc.clone().add(0, -1, 0);

        if (landedAt.getBlock().getType() == Material.COBBLESTONE) {
            landedAt.getBlock().setType(Material.SAND);
        }

        logger.warning("tracking falling " + fallingBlock.getBlockData().getMaterial().name() + " at "
            + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " (landed)");
    }
}
