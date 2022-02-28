package cn.xkmc6.xkitemmanage.internal.meta;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小坤
 * @date 2022/02/06 13:14
 */
public abstract class Meta {
    @Getter
    private final ConfigurationSection root;

    public Meta(ConfigurationSection root) {
        this.root = root;
    }

    public ItemStack build(ItemStack itemStack) {
        return itemStack;
    }

    public ItemStack drop(ItemStack itemStack) {
        return itemStack;
    }
}
