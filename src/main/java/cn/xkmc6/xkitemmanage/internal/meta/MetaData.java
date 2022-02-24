package cn.xkmc6.xkitemmanage.internal.meta;

import de.tr7zw.changeme.nbtapi.NBTItem;
import javafx.util.Pair;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/23 23:28
 */
@MetaKey(key = "data")
public class MetaData extends Meta {
    private final List<Pair<String, Object>> data = new ArrayList<>();

    public MetaData(ConfigurationSection root) {
        super(root);
        ConfigurationSection rootSection = root.getConfigurationSection("data");
        if (rootSection == null) return;
        rootSection.getKeys(true).stream().filter(key -> rootSection.getConfigurationSection(key) == null).forEach(k -> data.add(new Pair<>(k, rootSection.get(k))));
    }

    @Override
    public ItemStack build(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        data.forEach(pair -> nbtItem.setObject("xkitem." + pair.getKey(), pair.getValue()));
        return nbtItem.getItem();
    }

    @Override
    public ItemStack drop(ItemStack itemStack){
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.removeKey("xkitem");
        return nbtItem.getItem();
    }
}
