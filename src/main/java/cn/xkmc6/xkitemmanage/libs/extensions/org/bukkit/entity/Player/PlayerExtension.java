package cn.xkmc6.xkitemmanage.libs.extensions.org.bukkit.entity.Player;

import cn.xkmc6.xkitemmanage.util.PageableInventory;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Extension
public class PlayerExtension {
    public static void openInventory(@This Player player, PageableInventory inventory) {
        player.closeInventory();
        player.openInventory(inventory.getInventory());
    }

    public static void addItem(@This Player player, ItemStack itemStack) {
        if (itemStack != null && !itemStack.getType().equals(Material.AIR)){
            ItemStack item = itemStack.clone();
            item.setAmount(item.getMaxStackSize());
            player.getInventory().addItem(item);
        }
    }
}