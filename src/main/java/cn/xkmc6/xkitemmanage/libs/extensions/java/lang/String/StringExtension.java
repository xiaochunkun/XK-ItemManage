package cn.xkmc6.xkitemmanage.libs.extensions.java.lang.String;

import cn.xkmc6.xkitemmanage.internal.meta.MetaAttribute;
import com.cryptomorin.xseries.XEnchantment;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.lang.String;
import java.util.Locale;

@Extension
public class StringExtension {
    public static void print(@This String key) {
        System.out.println(key);
    }

    public static MetaAttribute.Attributes asAttributes(@This String key) {
        return MetaAttribute.Attributes.asAttributes(key);
    }

    public static MetaAttribute.Slot asSlot(@This String key) {
        return MetaAttribute.Slot.asSlot(key);
    }

    public static Enchantment asEnchantment(@This String key) {
        return XEnchantment.matchXEnchantment(key).map(XEnchantment::getEnchant).orElse(null);
    }

    public static ItemFlag asItemFlag(@This String key) {
        try {
            return ItemFlag.valueOf(key);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static PotionData asPotionData(@This String key) {
        try {
            return new PotionData(PotionType.valueOf(key.toUpperCase(Locale.getDefault())));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static PotionEffectType asPotionEffectType(@This String key){
        return PotionEffectType.getByName(key);
    }

    public static int asInt(@This String key){
        try {
            return Integer.parseInt(key);
        }catch (NumberFormatException e){
            return -1;
        }
    }


}