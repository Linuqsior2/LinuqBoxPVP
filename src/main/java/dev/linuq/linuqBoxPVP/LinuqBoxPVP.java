package dev.linuq.linuqBoxPVP;

import dev.linuq.LinuqBoxPVP.cmd.LinuqBoxPVPCMD;
import dev.linuq.LinuqBoxPVP.config.Config;
import dev.linuq.LinuqBoxPVP.listener.*;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public final class LinuqBoxPVP extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        try {
            this.config = ConfigManager.create(Config.class, (it) -> {
                it.withConfigurer(new YamlBukkitConfigurer());
                it.withBindFile(new File(this.getDataFolder(), "config.yml"));
                it.saveDefaults();
                it.load(true);
            });
        } catch (Exception exception) {
            this.getLogger().log(Level.SEVERE, "Error loading config.yml", exception);
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        getCommand("linuqboxpvp").setExecutor(new LinuqBoxPVPCMD(config));
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(config),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
