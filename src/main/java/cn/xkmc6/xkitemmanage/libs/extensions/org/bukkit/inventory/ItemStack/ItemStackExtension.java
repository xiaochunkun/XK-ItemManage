package cn.xkmc6.xkitemmanage.libs.extensions.org.bukkit.inventory.ItemStack;

import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.internal.ItemData;
import de.tr7zw.changeme.nbtapi.NBTItem;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;

/**
 * @author 小坤
 * @date 2022/03/03 10:28
 */
@Extension
public class ItemStackExtension {
    public static ItemData getItemData(@This ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("xk_item_manage_key")) return null;
        String key = nbtItem.getString("xk_item_manage_key");
        return ItemManage.getInstance().getConfigManage().getItemConfig().getItemMap().getOrDefault(key, null);
    }

    public static String getItemDataKey(@This ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        return nbtItem.getString("xk_item_manage_key");
    }

    public static void showItemStack(@This ItemStack itemStack, Player player) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (nbtItem.getKeys().size() > 0) {
            player.sendMessage("该物品的NBT为：");
            nbtItem.getKeys().forEach(key -> {
                player.sendMessage(MessageFormat.format("§a键：§8[§e{0}§8]§a 值：§8[§e{1}§8]", key, nbtItem.getString(key)));
            });
        } else {
            player.sendMessage("§4该物品无NBT");
        }
    }
}
