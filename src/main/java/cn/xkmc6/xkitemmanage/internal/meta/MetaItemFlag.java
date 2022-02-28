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
public class MetaItemFlag extends Meta{

    private final List<ItemFlag> flags = new ArrayList<>();

    public MetaItemFlag(ConfigurationSection root) {
        super(root);
        ConfigurationSection rootSection = root.getConfigurationSection("meta.itemflag");
        if (rootSection == null) return;
        rootSection.getKeys(false).forEach(key -> {
            if (key.asItemFlag() != null) flags.add(key.asItemFlag());
        });
    }

    @Override
    public ItemStack build(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        flags.forEach(meta::addItemFlags);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public ItemStack drop(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        meta.getItemFlags().forEach(meta::removeItemFlags);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
