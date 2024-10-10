package dev.seeruk.plugin.renewables;

import dev.seeruk.plugin.renewables.config.Config;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.List;
import java.util.Optional;

public class RenewablesListener implements Listener {
    private final Config config;

    public RenewablesListener(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock fallingBlock) {
            var anvils = List.of(Material.ANVIL, Material.CHIPPED_ANVIL, Material.DAMAGED_ANVIL);

            if (anvils.contains(fallingBlock.getBlockData().getMaterial())) {
                this.handleFallingAnvil(event, fallingBlock);
                return; // NOTE: Required if we're using this event for more thing in the future
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

        var fromName = landedAt.getBlock().getType().name();

        // Swap the material of the block, if it is configured to do so
        Optional.ofNullable(this.config.anvilTransitions.materials.get(fromName))
            .map(Material::getMaterial)
            .ifPresent(toMaterial -> landedAt.getBlock().setType(toMaterial));
    }
}
