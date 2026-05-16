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
            sender.sendMessage(ColorFixer.addColors(config.prefix + "&cNie posiadasz permisji do tej komendy! &8(&7linuqboxpvp.admin&8)"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorFixer.addColors(config.prefix + "&7Użycie: &f/" + label + " reload, odlamek, set"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            config.load();
            sender.sendMessage(ColorFixer.addColors(config.prefix + "&aPomyślnie przeładowałeś config.yml"));
            return true;
        }

        if (args[0].equalsIgnoreCase("odlamek")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ColorFixer.addColors(config.prefix + "&cNie możesz użyć tej komendy z poziomu konsoli!"));
                return true;
            }

            ItemStack fragment = new ItemStack(config.materialOdlamek);
            ItemMeta fragmentMeta = fragment.getItemMeta();

            if (fragmentMeta != null) {
                fragmentMeta.setDisplayName(ColorFixer.addColors(config.nameOdlamek));
                fragmentMeta.setLore(ColorFixer.addColors(config.loreOdlamek));
                fragmentMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                fragmentMeta.addEnchant(Enchantment.DURABILITY, 10, true);
                fragment.setItemMeta(fragmentMeta);
            }

            player.getInventory().addItem(fragment);
            sender.sendMessage(ColorFixer.addColors(config.prefix + "&aPomyślnie nadałeś odłamek"));
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ColorFixer.addColors(config.prefix + "&cNie możesz użyć tej komendy z poziomu konsoli!"));
                return true;
            }

            ItemStack item = player.getInventory().getItemInMainHand();

            if (item.getType().isAir()) {
                sender.sendMessage(ColorFixer.addColors(config.prefix + "&cMusisz trzymać item w ręce!"));
                return true;
            }

            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                sender.sendMessage(ColorFixer.addColors(config.prefix + "&cTen item nie ma meta!"));
                return true;
            }

            config.materialOdlamek = item.getType();
            config.nameOdlamek = meta.hasDisplayName() ? meta.getDisplayName() : item.getType().name();
            config.loreOdlamek = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

            config.save();
            sender.sendMessage(ColorFixer.addColors(config.prefix + "&aPomyślnie ustawiono odłamek!"));
            return true;
        }

        sender.sendMessage(ColorFixer.addColors(config.prefix + "&c?"));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && sender.hasPermission("linuqboxpvp.admin")) {
            List<String> chat = new ArrayList<>();
            chat.add("reload");
            chat.add("odlamek");
            chat.add("set");
            return chat;
        }
        return null;
    }
}