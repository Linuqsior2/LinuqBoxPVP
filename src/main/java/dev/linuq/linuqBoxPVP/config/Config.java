package dev.linuq.linuqBoxPVP.config;

import dev.linuq.linuqBoxPVP.config.helpers.MaterialBreakBlocks;
import dev.linuq.linuqBoxPVP.config.helpers.PermissionDropChance;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;

import java.util.*;

public class Config extends OkaeriConfig {
    @Comment("Prefix pluginu")
    public String prefix = "&#526ECA&lF&#5471C8&la&#5574C6&lz&#5776C5&le&#5979C3&lB&#5A7CC1&lo&#5C7FBF&lx&#5E81BE&lP" +
            "&#5F84BC&lV&#6187BA&lP &8» &7";

    @Comment("Odłamki")
    @Comment
    @Comment("Eventowy drop x2 odłamków")
    public boolean eventDrop = false;

    @Comment("Permisje i procenty do dropienia odłamków")
    public Map<String, PermissionDropChance> permissionDrops = new LinkedHashMap<>();
    {
        permissionDrops.put("linuqboxpvpaddon.default", new PermissionDropChance(25.3));
        permissionDrops.put("linuqboxpvpaddon.vip", new PermissionDropChance(30.5));
    }

    @Comment("Jaki ma być procent na drop x2 odłamków")
    public int eventChanceDrop = 40;

    @Comment("Wiadomość gdy gracz dropnie odłamek")
    public String messageOdlamek = "&aGratulacje! Zabijając gracza zdobyłeś odłamek szamana!";

    @Comment("Wiadomość gdy gracz wydropi odłamek x2")
    public List<String> eventMessageDrop = new ArrayList<>();
    {
        eventMessageDrop.add("");
        eventMessageDrop.add("&#DB27D6&lW&#D927D6&la&#D728D7&ll&#D528D7&le&#D328D8&ln&#D028D8&lt&#CE29D8&ly&#CC29D9&ln&#CA29D9&lk&#C82ADA&lo&#C62ADA&lw&#C42ADB&ly &#BF2BDB&ld&#BD2BDC&lr&#BB2BDC&lo&#B92CDD&lp&#B72CDD&l!");
        eventMessageDrop.add("&aZabijając gracza zdobyłeś odłamek szamana &#DB27D6x2!");
        eventMessageDrop.add("");
    }

    @Comment("Nazwa odłamka")
    public String nameOdlamek = "&#FFEE00&lM&#FDDE11&la&#FCCE22&lg&#FABE33&li&#F9AE45&lc&#F79E56&lz&#F58E67&ln&#F47E78&ly &#EE49B1&lP&#EC37C5&lr&#EB25D8&lo&#E912EC&lc&#E700FF&lh";

    @Comment("Lore odłamka")
    public List<String> loreOdlamek = new ArrayList<>();
    {
        loreOdlamek.add("");
        loreOdlamek.add("&7Ten item wypada po zabiciu gracza");
        loreOdlamek.add("&7Możesz go użyć pod komendą &d/szaman");
        loreOdlamek.add("");
    }

    @Comment("Material odłamka")
    public Material materialOdlamek = Material.GLOWSTONE_DUST;

    @Comment("SEKCJA BLOKÓW")
    @Comment
    @Comment("Jakie mają być zablokowane itemy/bloki do zniszczenia")
    public List<Material> blockedBreakBlocks = List.of(Material.IRON_BLOCK, Material.GOLD_BLOCK);

    @Comment("Jakie mają być zablokowane itemy/bloki do postawienia")
    public List<Material> blockedPlaceBlocks = List.of(Material.IRON_BLOCK, Material.GOLD_BLOCK);

    @Comment("Jaki blok można zniszczyć jakim itemem")
    public Map<Material, MaterialBreakBlocks> breakableBlocks = new LinkedHashMap<>();
    {
        breakableBlocks.put(Material.SPONGE, new MaterialBreakBlocks(Arrays.asList(Material.WOODEN_HOE, Material.DIAMOND_HOE)));

    }

    @Comment("Jaki ma być mnożnik bloków")
    public double fortuneBlocks = 1.5;

    @Comment("Czy ma być włączone dropowanie skopanych bloków do inventory")
    public boolean dropToInventory = true;

    @Comment("Title i subtitle kiedy gracz niszczy blok niedozwolonym itemem")
    public String titleBreakBlocks = "&#FF0000&lBŁĄD!";
    public String subTitleBreakBlocks = "&cNie możesz niszczyć tym itemem tego bloku";

    @Comment("Wiadomość gdy gracz próbuje postawić niedozwolony blok")
    public String blockedPlaceBlocksMessage = "&cNie możesz postawić tego bloku";

    @Comment("Wiadomość gdy gracz próbuje zniszczyć niedozwolony blok")
    public String blockedBreakBlocksMessage = "&cNie możesz zniszczyć tego bloku";

}
