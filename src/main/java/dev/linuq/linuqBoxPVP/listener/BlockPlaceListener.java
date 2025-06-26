package dev.linuq.linuqBoxPVP.listener;

import dev.linuq.LinuqBoxPVP.config.Config;
import dev.linuq.LinuqBoxPVP.utils.ColorFixer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.Player;

public class BlockPlaceListener implements Listener {
    public Config config;

    public BlockPlaceListener(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material placed = event.getBlockPlaced().getType();

        if (config.blockedItems.contains(placed)) {
            event.setCancelled(true);
            player.sendMessage(ColorFixer.addColors(config.prefix + config.blockedItemsMessage));
        }
    }
}
