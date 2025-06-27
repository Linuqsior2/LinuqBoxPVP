package dev.linuq.linuqBoxPVP.listener;

import dev.linuq.linuqBoxPVP.config.Config;
import dev.linuq.linuqBoxPVP.config.helpers.PermissionDropChance;
import dev.linuq.linuqBoxPVP.utils.ColorFixer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Random;

public class OnPlayerKill implements Listener {

    public final Config config;

    public OnPlayerKill(Config config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        if (killer != null) {
            Random rand = new Random();
            double chance = 0;

            for (Map.Entry<String, PermissionDropChance> entry : config.permissionDrops.entrySet()) {
                if (killer.hasPermission(entry.getKey())) {
                    chance = entry.getValue().getChance();
                    break;
                }
            }

            if (rand.nextInt(100) < chance) {
                ItemStack fragment = new ItemStack(config.materialOdlamek);

                ItemMeta fragmentMeta = fragment.getItemMeta();
                if (fragmentMeta != null) {
                    fragmentMeta.setDisplayName(ColorFixer.addColors(config.nameOdlamek));
                    fragmentMeta.setLore(ColorFixer.addColors(config.loreOdlamek));

                    fragmentMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    fragmentMeta.addEnchant(Enchantment.DURABILITY, 10, true);

                    fragment.setItemMeta(fragmentMeta);
                }


                if (config.eventDrop) {
                    if (rand.nextInt(100) < config.eventChanceDrop) {
                        killer.getInventory().addItem(fragment);
                        killer.getInventory().addItem(fragment);
                        killer.sendMessage(ColorFixer.addColors(String.valueOf(config.eventMessageDrop)));
                        return;
                    }
                }

                killer.getInventory().addItem(fragment);
                killer.sendMessage(ColorFixer.addColors(config.messageOdlamek));

            }
        }
    }
}
