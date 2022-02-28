package cn.xkmc6.xkitemmanage.libs.extensions.java.lang.String;

import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.internal.Display;
import cn.xkmc6.xkitemmanage.internal.meta.MetaAttribute;
import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import javafx.util.Pair;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.lang.String;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Extension
public class StringExtension {
    public static void print(@This String key) {
        ItemManage.getInstance().getLogger().info(key);
    }

    public static XMaterial asXMaterial(@This String key) {
        Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(key);
        return xMaterial.orElse(XMaterial.STONE);
    }

    public static Material asMaterial(@This String key) {
        Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(key);
        return xMaterial.orElse(XMaterial.STONE).parseMaterial();
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

    public static PotionEffectType asPotionEffectType(@This String key) {
        return PotionEffectType.getByName(key);
    }

    public static int asInt(@This String key) {
        try {
            return Integer.parseInt(key);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static Display asDisplay(@This String key){
        Map<String,Display> map = ItemManage.getInstance().getConfigManage().getDisplayConfig().getDisplayMap();
        return map.getOrDefault(key, null);
    }

    public static List<String> asNameKey(@This String key){
        List<String> list = new ArrayList<>();
        Pattern r = Pattern.compile("<(.*?)>");
        Matcher m = r.matcher(key);
        while(m.find()) {
            list.add(m.group().replace("<","").replace(">","").replace("...",""));
        }
        return list;
    }

    public static List<Pair<String,Boolean>> asLoreKey(@This String key){
        List<Pair<String,Boolean>> list = new ArrayList<>();
        Pattern r = Pattern.compile("<(.*?)>");
        Matcher m = r.matcher(key);
        while(m.find()) {
            if (m.group().contains("...>")){
                list.add(new Pair<>(m.group().replace("<","").replace("...>",""),true));
            }else {
                list.add(new Pair<>(m.group().replace("<","").replace(">",""),false));
            }
        }
        return list;
    }
}