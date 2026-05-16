package dev.linuq.linuqBoxPVP.listener;

import dev.linuq.linuqBoxPVP.config.Config;
import dev.linuq.linuqBoxPVP.config.helpers.MaterialBreakBlocks;
import dev.linuq.linuqBoxPVP.utils.ColorFixer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockListener implements Listener {

    public Config config;
    private final Set<Location> placedBlocks = new HashSet<>();

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
            return;
        }

        placedBlocks.add(event.getBlockPlaced().getLocation());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        Location location = event.getBlock().getLocation();

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

        ItemStack tool = player.getInventory().getItemInMainHand();

        boolean placedByPlayer = placedBlocks.remove(location);

        Collection<ItemStack> drops = event.getBlock().getDrops(tool);

        for (ItemStack drop : drops) {
            ItemStack multipliedDrop = drop.clone();

            if (!placedByPlayer) {
                int baseAmount = drop.getAmount();
                int finalAmount = baseAmount;

                int fortuneLevel = 0;
                if (tool != null && tool.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    fortuneLevel = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                }

                if (fortuneLevel > 0) {
                    Random random = new Random();
                    int extra = 0;
                    for (int i = 0; i < fortuneLevel; i++) {
                        if (random.nextInt(100) < 33) {
                            extra++;
                        }
                    }
                    finalAmount += extra;
                }

                finalAmount = (int) Math.round(finalAmount * config.fortuneBlocks);
                multipliedDrop.setAmount(Math.max(finalAmount, 1));
            }

            if (config.dropToInventory) {
                var notStored = player.getInventory().addItem(multipliedDrop);
                if (!notStored.isEmpty()) {
                    for (ItemStack item : notStored.values()) {
                        player.getWorld().dropItemNaturally(location, item);
                    }
                }
            } else {
                player.getWorld().dropItemNaturally(location, multipliedDrop);
            }
        }

        event.getBlock().setType(Material.AIR);
    }
}