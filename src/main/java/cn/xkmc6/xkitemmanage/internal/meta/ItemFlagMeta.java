package cn.xkmc6.xkitemmanage.internal.meta;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/23 13:13
 */
@MetaKey(key = "itemflag")
public class ItemFlagMeta extends Meta{

    private final List<ItemFlag> flags = new ArrayList<>();

    public ItemFlagMeta(ConfigurationSection root) {
        super(root);
        flags.clear();
        root.getKeys(false).forEach(key -> {
            if (key.asItemFlag() != null) flags.add(key.asItemFlag());
        });
    }

    public ItemStack build(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        flags.forEach(meta::addItemFlags);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack drop(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        meta.getItemFlags().forEach(meta::removeItemFlags);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
