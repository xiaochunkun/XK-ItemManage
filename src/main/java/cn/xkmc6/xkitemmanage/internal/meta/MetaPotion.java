package cn.xkmc6.xkitemmanage.internal.meta;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/23 19:29
 */
@MetaKey(key = "potion")
public class MetaPotion extends Meta {

    private PotionData basePotion = null;

    private final List<PotionEffect> potionEffects = new ArrayList<>();

    public MetaPotion(ConfigurationSection root) {
        super(root);
        ConfigurationSection rootSection = root.getConfigurationSection("meta.potion");
        if (rootSection == null) return;
        rootSection.getKeys(false).forEach(key -> {
            if (key.equalsIgnoreCase("base")) {
                if (key.asPotionData() == null) return;
                basePotion = key.asPotionData();
            } else {
                if (key.asPotionEffectType() == null) return;
                String info = root.getString(key);
                if (info == null || info.split("-").length < 2) return;
                int time = info.split("-")[0].asInt();
                int level = info.split("-")[1].asInt();
                potionEffects.add(key.asPotionEffectType().createEffect(time, level));
            }
        });
    }

    @Override
    public ItemStack build(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        if (meta instanceof PotionMeta) {
            if (basePotion != null) {
                ((PotionMeta) meta).setBasePotionData(basePotion);
            }
            potionEffects.forEach(potionEffect -> ((PotionMeta) meta).addCustomEffect(potionEffect, true));
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public ItemStack drop(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        if (meta instanceof PotionMeta) {
            ((PotionMeta) meta).setBasePotionData(new PotionData(PotionType.WATER));
            ((PotionMeta) meta).clearCustomEffects();
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
