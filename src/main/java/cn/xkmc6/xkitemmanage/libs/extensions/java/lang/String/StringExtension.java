package cn.xkmc6.xkitemmanage.libs.extensions.java.lang.String;

import cn.xkmc6.xkitemmanage.internal.meta.AttributeMeta;
import com.cryptomorin.xseries.XEnchantment;
import lombok.val;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.lang.String;
import java.util.Objects;
import java.util.Optional;

@Extension
public class StringExtension {
    public static void print(@This String key) {
        System.out.println(key);
    }

    public static AttributeMeta.Attributes asAttributes(@This String key) {
        return AttributeMeta.Attributes.asAttributes(key);
    }

    public static AttributeMeta.Slot asSlot(@This String key) {
        return AttributeMeta.Slot.asSlot(key);
    }

    public static Enchantment asEnchantment(@This String key) {
        return XEnchantment.matchXEnchantment(key).map(XEnchantment::getEnchant).orElse(null);
    }

    public static ItemFlag asItemFlag(@This String key){
        try {
            return ItemFlag.valueOf(key);
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}