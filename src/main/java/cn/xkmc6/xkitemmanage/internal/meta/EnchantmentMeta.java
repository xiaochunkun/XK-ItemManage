package cn.xkmc6.xkitemmanage.internal.meta;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 小坤
 * @date 2022/02/22 13:16
 */
@MetaKey(key = "enchantment")
public class EnchantmentMeta extends Meta {

    private final Map<Enchantment, Integer> enchantmentMap = new HashMap<>();

    public EnchantmentMeta(ConfigurationSection root) {
        super(root);
        enchantmentMap.clear();
        root.getKeys(false).forEach(key -> {
            if (key.asEnchantment() == null) return;
            int value = root.getInt(key);
            if (value <= 0) return;
            enchantmentMap.put(key.asEnchantment(), value);
        });
    }

    public ItemStack build(ItemStack itemStack) {
        enchantmentMap.forEach(itemStack::addUnsafeEnchantment);
        return itemStack;
    }

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
