package dev.linuq.linuqBoxPVP.listener;

import dev.linuq.linuqBoxPVP.config.Config;
import dev.linuq.linuqBoxPVP.config.helpers.MaterialBreakBlocks;
import dev.linuq.linuqBoxPVP.utils.ColorFixer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.Player;

public class BlockListener implements Listener {
    public Config config;

    public BlockListener(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material placed = event.getBlockPlaced().getType();

        if (config.blockedPlaceBlocks.contains(placed)) {
            event.setCancelled(true);
            player.sendMessage(ColorFixer.addColors(config.prefix + config.blockedPlaceBlocksMessage));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        Material itemInHand = player.getInventory().getItemInMainHand().getType();

        MaterialBreakBlocks mbb = config.breakableBlocks.get(blockType);

        if (config.blockedBreakBlocks.contains(blockType)) {
            event.setCancelled(true);
            player.sendMessage(ColorFixer.addColors(config.prefix + config.blockedBreakBlocksMessage));
        }

        if (mbb != null) {
            if (!mbb.getMaterial().contains(itemInHand)) {
                event.setCancelled(true);
                player.sendMessage(ColorFixer.addColors(config.prefix + "&cNie możesz zniszczyć tego bloku tym przedmiotem!"));
                player.sendTitle(ColorFixer.addColors(config.titleBreakBlocks), ColorFixer.addColors(config.subTitleBreakBlocks));
            }
        }
    }
}