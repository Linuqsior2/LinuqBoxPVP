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
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;

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

        if (config.blockedBreakBlocks.contains(blockType)) {
            event.setCancelled(true);
            player.sendMessage(ColorFixer.addColors(config.prefix + config.blockedBreakBlocksMessage));
            return;
        }

        if (config.breakableBlocks.get(blockType) != null) {
            Material itemInHand = player.getInventory().getItemInMainHand().getType();
            if (!config.breakableBlocks.get(blockType).getMaterial().contains(itemInHand)) {
                event.setCancelled(true);
                player.sendMessage(ColorFixer.addColors(config.prefix + "&cNie możesz zniszczyć tego bloku tym przedmiotem!"));
                player.sendTitle(ColorFixer.addColors(config.titleBreakBlocks), ColorFixer.addColors(config.subTitleBreakBlocks));
                return;
            }
        }

        event.setDropItems(false);
        Collection<ItemStack> drops = event.getBlock().getDrops(player.getInventory().getItemInMainHand());
        for (ItemStack drop : drops) {
            int amount = (int) Math.round(drop.getAmount() * config.fortuneBlocks);
            if (amount > 0) {
                ItemStack multipliedDrop = drop.clone();
                multipliedDrop.setAmount(amount);
                if (config.dropToInventory) {
                    HashMap<Integer, ItemStack> notStored = player.getInventory().addItem(multipliedDrop);
                    if (!notStored.isEmpty()) {
                        for (ItemStack item : notStored.values()) {
                            player.getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
                        }
                    }
                } else {
                    player.getWorld().dropItemNaturally(event.getBlock().getLocation(), multipliedDrop);
                }
            }
        }
        event.getBlock().setType(Material.AIR);
    }

}