package cn.xkmc6.xkitemmanage.internal.meta;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小坤
 * @date 2022/02/22 13:16
 */
@MetaKey(key = "enchantment")
public class MetaEnchantment extends Meta {

    private final Map<Enchantment, Integer> enchantmentMap = new HashMap<>();

    public MetaEnchantment(ConfigurationSection root) {
        super(root);
        enchantmentMap.clear();
        ConfigurationSection rootSection = root.getConfigurationSection("meta.enchantment");
        if (rootSection == null) return;
        rootSection.getKeys(false).forEach(key -> {
            if (key.asEnchantment() == null) return;
            int value = root.getInt(key);
            if (value <= 0) return;
            enchantmentMap.put(key.asEnchantment(), value);
        });
    }

    @Override
    public ItemStack build(ItemStack itemStack) {
        enchantmentMap.forEach(itemStack::addUnsafeEnchantment);
        return itemStack;
    }

    @Override
    public ItemStack drop(ItemStack itemStack) {
        itemStack.getEnchantments().forEach((k, v) -> itemStack.removeEnchantment(k));
        return itemStack;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("EnchantmentMeta{");
        for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : enchantmentMap.entrySet()) {
            result.append("enchantment=");
            result.append(enchantmentIntegerEntry.getKey());
            result.append(",level=");
            result.append(enchantmentIntegerEntry.getValue());
            result.append(";");
        }
        result.append("}");
        return result.toString();
    }
}
