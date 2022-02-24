package cn.xkmc6.xkitemmanage.internal.meta;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author 小坤
 * @date 2022/02/23 20:10
 */
@MetaKey(key = "shiny")
public class MetaShiny extends Meta {
    private final boolean shiny;

    public MetaShiny(ConfigurationSection root) {
        super(root);
        shiny = root.getBoolean("shiny", false);
    }

    @Override
    public ItemStack build(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        if (shiny){
            meta.addEnchant(Enchantment.ARROW_DAMAGE,1,true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public ItemStack drop(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        meta.removeEnchant(Enchantment.ARROW_DAMAGE);
        meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
