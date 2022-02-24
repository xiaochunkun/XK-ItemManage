package cn.xkmc6.xkitemmanage.internal.meta;

import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;

/**
 * @author 小坤
 * @date 2022/02/23 20:15
 */
@MetaKey(key = "color")
public class MetaColor extends Meta {
    private final Color color;

    public MetaColor(ConfigurationSection root) {
        super(root);
        String s = root.getString("meta.color");
        if (s == null || s.split("-").length < 3) {
            color = null;
        } else {
            color = Color.fromBGR(s.split("-")[0].asInt(), s.split("-")[1].asInt(), s.split("-")[2].asInt());
        }
    }

    @Override
    public ItemStack build(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        if (meta instanceof PotionMeta){
            ((PotionMeta) meta).setColor(color);
        } else if (meta instanceof LeatherArmorMeta){
            ((LeatherArmorMeta) meta).setColor(color);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public ItemStack drop(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        if (meta instanceof PotionMeta){
            ((PotionMeta) meta).setColor(null);
        } else if (meta instanceof LeatherArmorMeta){
            ((LeatherArmorMeta) meta).setColor(null);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
