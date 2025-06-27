package dev.linuq.linuqBoxPVP.cmd;

import dev.linuq.linuqBoxPVP.config.Config;
import dev.linuq.linuqBoxPVP.utils.ColorFixer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LinuqBoxPVPCMD implements CommandExecutor, TabCompleter {

    private final Config config;

    public LinuqBoxPVPCMD(Config config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("linuqboxpvp.admin")) {
            sender.sendMessage(ColorFixer.addColors(config.prefix + "&cNie posiadasz permisji do tej komendy! &8" +
                    "(&7linuqboxpvp.admin&8)"));
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                config.load();
                sender.sendMessage(ColorFixer.addColors(config.prefix + "&aPomyślnie przeładowałeś config.yml"));
            }

            if (args[0].equalsIgnoreCase("odlamek")) {
                if (sender instanceof Player player) {
                    ItemStack fragment = new ItemStack(Material.GLOWSTONE_DUST);

                    ItemMeta fragmentMeta = fragment.getItemMeta();
                    if (fragmentMeta != null) {
                        fragmentMeta.setDisplayName(ColorFixer.addColors(config.nameOdlamek));
                        fragmentMeta.setLore(ColorFixer.addColors(config.loreOdlamek));

                        fragmentMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        fragmentMeta.addEnchant(Enchantment.DURABILITY, 10, true);

                        fragment.setItemMeta(fragmentMeta);
                    }
                    player.getInventory().addItem(fragment);
                    sender.sendMessage(ColorFixer.addColors(config.prefix + "Pomyślnie nadałeś odłamek"));
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> chat = new ArrayList<>();
            if (sender.hasPermission("linuqboxpvp.admin")) {
                chat.add("reload");
                chat.add("odlamek");
                return chat;
            }
        }
        return null;
    }
}
